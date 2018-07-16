package com.zero.demo.controller;

import static com.zero.demo.constants.BaseConstants.RESPONSE_TYPE;
import static com.zero.demo.constants.I18NConstants.ERR_FAIL;
import static com.zero.demo.constants.I18NConstants.MSG_SUCCESS;
import static com.zero.demo.util.I18nUtil.getMessage;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.zero.core.domain.PageVO;
import com.zero.core.domain.ResponseVO;
import com.zero.core.tasks.AppCallable;
import com.zero.core.tasks.AppRunnable;
import com.zero.demo.ServiceException;
import com.zero.demo.constants.BaseConstants;
import com.zero.demo.service.DummyService;
import com.zero.demo.vo.DummyVO;
import com.zero.demo.vo.UserInfoBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "The Sample Controller", value = "Sample Controller Value", produces = "The Produces")
/**
 * DummyController
 * 
 * http://ljc.zero.com:8080/SpringBootDemo/dummy/sysInfo
 * 
 * @author Administrator
 * @version 2017-11-24
 */
@RestController
@RequestMapping("/dummy")
public class DummyController extends BaseController {
    private Logger log = LoggerFactory.getLogger(DummyController.class);

    //By default, get the property from application.yml or application.properties
    //If from app.yaml/app.properties, add @PropertySource("classpath:/com/acme/app.properties")
    @Value("${appConf.version}")
    private Long version;
    
    @Autowired
    private DummyService dummyService;

    @ApiOperation(value = "get Product Name", notes = "")
    /**
    * Get system information
    * @return
    */
    @RequestMapping(path = "/sysInfo", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO sysInfo() {
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                return dummyService.getSysInfo();
            }
        });                
    }

    @RequestMapping(path = "/getToken", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO getToken() {
        String statusCode = BaseConstants.STATUS_ERR;
        String statusMsg = "";
        Object obj = null;
        try {
            UserInfoBean ui = getUserInfoBean();
            if (ui != null) {
                String token = UUID.randomUUID().toString();
                setToken(ui.getUid(), token);

                obj = token;
                statusCode = BaseConstants.STATUS_OK;
                statusMsg = getMessage(MSG_SUCCESS);
            } else {
                statusCode = BaseConstants.STATUS_NOT_LOGIN;
            }
        } catch (Exception e) {
            log.error("{}", e);
            statusMsg = getMessage(ERR_FAIL);
        }

        return new ResponseVO(statusCode, statusMsg, obj);
    }

    /**
    * http://192.168.101.59:9002/appContext/dummy/list/1?userName=Alice
    * 
    * @param projectCode e.g. 1, 33333333, 66666666
    * @return statusCode=200, e.g. { statusCode: "200", list: [{name: "AAA"}, {name: "BBB"}]} 
    */
    @RequestMapping(path = "/list/{userId}", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO findDummyList(@PathVariable int userId, @RequestParam String userName) {
        log.info("userId: {}, userName: {}", userId, userName);
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                return dummyService.findDummyList(userId);
            }
        });
    }

    @ApiOperation(value = "分页查询Dummy列表", notes = "")
    @ApiImplicitParam(name = "param", value = "Dummy参数详情", required = true, dataType = "DummyVO")
    /**
    * Invoke sample:
    * POST http://192.168.101.59:9002/appContext/dummy/page/3/2
    * BODY { "projectCode": "1" }
    * Note: get pageSize, pageIndex from pageVO
    * 
    * @param scheme 
    * @param pageVO (pageSize, pageIndex)
    * @return PagedResultVO {}
    */
    @RequestMapping(path = "/page/{pageSize}/{pageIndex}", method = RequestMethod.POST, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO findDummyPage(@RequestBody DummyVO param, PageVO pageVO) {
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                return dummyService.findDummyPage(param, pageVO);
            }
        });
    }
    
    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO findDummyPage(@RequestBody DummyVO param) {
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                return dummyService.addDummy(param);
            }
        });
    }    

    @RequestMapping(path = "/log", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO downloadLog() {
        return process(new AppRunnable() {
            public void run() throws ServiceException {
                dummyService.downloadLog();
            }
        });
    }

    /**
    * 
    * Invoke sample:
    * POST http://192.168.101.59:9002/appContext/dummy/visit/3/2
    * 
    * @param pageVO (pageSize, pageIndex)
    * @return PagedResultVO {}
    */
    @RequestMapping(path = "/visit/{pageSize}/{pageIndex}", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO findAccessPage(PageVO pageVO) {
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                return dummyService.findAccessPage(pageVO);
            }
        });
    }

    @RequestMapping(path = "/uploadDoc", consumes = "multipart/form-data", method = RequestMethod.POST, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO uploadDoc(@RequestParam MultipartFile multiFile) {
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                return dummyService.uploadFile(multiFile);
            }
        });
    }
    
    /**
    * Process Fortify warning
    * 
    * @param binder
    * @param request
    */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.setDisallowedFields("createTime");
    }    
}
