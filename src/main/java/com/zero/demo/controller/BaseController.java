package com.zero.demo.controller;

import static com.zero.demo.constants.BaseConstants.HEADER_TOKEN;
import static com.zero.demo.constants.BaseConstants.STATUS_ERR;
import static com.zero.demo.constants.BaseConstants.STATUS_NOT_LOGIN;
import static com.zero.demo.constants.I18NConstants.ERR_NO_TOKEN;
import static com.zero.demo.util.I18nUtil.getMessage;
import static com.zero.demo.util.BaseUtil.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zero.core.domain.AccessVO;
import com.zero.core.domain.ResponseVO;
import com.zero.core.tasks.AppCallable;
import com.zero.core.tasks.AppRunnable;
import com.zero.core.tasks.AppTask;
import com.zero.demo.ServiceException;
import com.zero.demo.constants.BaseConstants;
import com.zero.demo.service.DummyService;
import com.zero.demo.util.HttpUtil;
import com.zero.demo.vo.UserInfoBean;

public abstract class BaseController {
    static final Logger log = LoggerFactory.getLogger(BaseController.class);

    static final Map<String, String> userTokenMap = new ConcurrentHashMap<>(); //Map(userID, Token)

    @Autowired
    private DummyService dummyService;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    protected void checkUserLogin() throws ServiceException {
        if (BaseConstants.LOGIN_ENABLE) {
            checkNotNull(getUserInfoBean(), STATUS_NOT_LOGIN);            
        }
    }
    
    protected void checkToken() throws ServiceException {
        if (BaseConstants.XSRF_ENABLE) {
            //1) Check token is present
            String mToken = HttpUtil.getHeader(HEADER_TOKEN);
            checkNotNull(mToken, ERR_NO_TOKEN);

            //2) Check token is correct
            UserInfoBean user = getUserInfoBean();
            String userId = user.getUid();
            String token = userTokenMap.get(userId);
            checkParameterTrue(token != null && token.equals(mToken), ERR_NO_TOKEN);
        }
    }

    protected ResponseVO process(AppTask task) {
        ResponseVO result = new ResponseVO();
        try {
            checkUserLogin();
            checkToken();
            logVisit();
            
            if (task instanceof AppCallable) {
                Object value = ((AppCallable) task).run();
                result.setData(value);
            } else {
                ((AppRunnable) task).run();
            }

        } catch (ServiceException e) {
            log.error(e.getErrCode());
            result.setStatusCode(e.getErrCode());
            result.setStatusMsg(getMessage(e.getErrCode(), e.getArgs()));
        } catch (Exception e) {
            log.error(e.toString());
            result.setStatusCode(STATUS_ERR);
            result.setStatusMsg(getMessage(STATUS_ERR));
        }

        return result;
    }
    
    private void logVisit() {
        StringBuilder sb = new StringBuilder("\n#### ");
        HttpServletRequest req = HttpUtil.getRequest();
        sb.append(req.getRemoteHost() + " visit " + req.getRequestURL()); //URL full url, URI just path without host:port
        System.out.println();
        log.info(sb.toString());
        HttpUtil.prtRequest();
    }

    protected UserInfoBean getUserInfoBean() {
        UserInfoBean uiBean = new UserInfoBean("default_user_01");
        //HttpUtil.getRequest().setAttribute(SESSION_USER_KEY, new DummyVO(100, "Louis"));

        HttpServletRequest request = HttpUtil.getRequest();
        if (request != null) {
            //HttpUtil.prtRequest(request);

            /*
            AccessVO accessVO = getAccessVO(request);
            HttpSession session = (request).getSession();
            uiBean = (UserInfoBean) session.getAttribute(SsoConstants.SESSION_USER_INFO_KEY);
            saveAccessRecord(accessVO);
            */
        }
        return uiBean;
    }

    void saveAccessRecord(AccessVO vo) {
        executorService.execute(new Runnable() {
            public void run() {
                try {
                    dummyService.addAccessRecord(vo);
                } catch (ServiceException e) {
                    log.error(e.getMessage());
                }
            }
        });
    }

    AccessVO getAccessVO(HttpServletRequest req) {
        AccessVO vo = new AccessVO();

        vo.setRequestURI(req.getRequestURI());
        vo.setServletPath(req.getServletPath());
        vo.setContextPath(req.getContextPath());

        vo.setMethod(req.getMethod());
        vo.setContentType(req.getContentType());

        vo.setLocalAddress(req.getLocalAddr());
        vo.setLocalName(req.getLocalName());
        vo.setLocalPort(req.getLocalPort());

        vo.setRemoteAddress(req.getRemoteAddr());
        vo.setRemoteHost(req.getRemoteHost());
        vo.setRemotePort(req.getRemotePort());

        return vo;
    }
}
