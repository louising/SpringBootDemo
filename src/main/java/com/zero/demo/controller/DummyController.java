package com.zero.demo.controller;

import static com.zero.demo.constants.BaseConstants.RESPONSE_TYPE;
import static com.zero.demo.constants.BaseConstants.STATUS_ERR;
import static com.zero.demo.constants.BaseConstants.STATUS_NOT_LOGIN;
import static com.zero.demo.constants.BaseConstants.STATUS_OK;
import static com.zero.demo.constants.I18NConstants.ERR_FAIL;
import static com.zero.demo.constants.I18NConstants.ERR_UPLOAD;
import static com.zero.demo.constants.I18NConstants.MSG_SUCCESS;
import static com.zero.demo.util.I18nUtil.getMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.io.FileUtils;
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
import com.zero.demo.service.DummyService;
import com.zero.demo.util.HttpUtil;
import com.zero.demo.vo.DummyVO;
import com.zero.demo.vo.UserInfoBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "The Sample Controller", value = "Sample Controller Value", produces = "The Produces")
/**
 * DummyController
 *    
 *   POST http://localhost:8080/SpringBootDemo/dummy/add body { userId: 101, userName: "Alice"}
 * DELETE http://localhost:8080/SpringBootDemo/dummy/del?dummyName=Alice001 BODY { "userId": 1, "userName": "Alice" }
 *    PUT http://localhost:8080/SpringBootDemo/dummy/upd?userId=102&userName=Alice02 BODY { "userId": 103, "userName": "Alice03" }
 *    GET http://localhost:8080/SpringBootDemo/dummy/list
 *   POST http://localhost:8080/SpringBootDemo/dummy/page/3/2 BODY { "userId": "1", "userName": "Alice" }
 *   
 *   POST http://localhost:8080/SpringBootDemo/dummy/uploadDoc BODY form-data
 *    GET http://localhost:8080/SpringBootDemo/dummy/downloadLog
 *    GET http://localhost:8080/SpringBootDemo/dummy/sysInfo
 * 
 * @author Administrator
 * @version 2017-11-24
 */
@RestController
@RequestMapping("/dummy")
public class DummyController extends BaseController {
    private Logger log = LoggerFactory.getLogger(DummyController.class);

    //By default, read property from application.yml or application.properties
    //If read from app.yaml/app.properties, add @PropertySource("classpath:/com/acme/app.properties")
    @Value("${appConf.version}")
    private Long version;

    @Autowired
    private DummyService dummyService;

    //Use org.springframework.http.converter.json.Jackson2ObjectMapperBuilder to convert JSON to Object
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseVO add(@RequestBody DummyVO param) {
        return process(new AppRunnable() {
            public void run() throws ServiceException {
                log.info("{}", param);
                dummyService.addDummy(param);
            }
        });
    }
    
    //If add @RequestBody, must provide body
    //Will auto fill paramVO with request parameters
    /**
     * DELETE http://localhost:8080/SpringBootDemo/dummy/del?dummyName=Alice001 BODY { "userId": "1", "userName": "Alice" }
     * DELETE http://localhost:8080/SpringBootDemo/dummy/del?dummyName=Alice01&userId=102&userName=Alice02 BODY { "userId": "103", "userName": "Alice03" }
     */
    @RequestMapping(path = "/del", method = RequestMethod.DELETE)
    public @ResponseBody ResponseVO del(@RequestParam("dummyName") String userName,  DummyVO paramVO, @RequestBody DummyVO body) {
        return process(new AppRunnable() {
            public void run() throws ServiceException {
                log.info("userName: {}, param: {}, body {}", userName, paramVO, body);
                //dummyService.del()
            }
        });
    }
    
    //update is a idempotent operation which does not create new resource
    //URL parameters will be filled to method parameter
    //if has @RequestBody, must provide body
    /**
     * PUT http://localhost:8080/SpringBootDemo/dummy/upd?userId=102&userName=Alice02 BODY { "userId": "103", "userName": "Alice03" }
     */
    @RequestMapping(path = "/upd", method = RequestMethod.PUT)
    public @ResponseBody ResponseVO upd(DummyVO requestParam, @RequestBody DummyVO body) {
        return process(new AppRunnable() {
            public void run() throws ServiceException {
                log.info("Param: {}, Body: {}", requestParam, body);
                //dummyService.upd()
            }
        });
    }      
    
    /**
     * GET http://localhost:8080/SpringBootDemo/dummy/list
     */
    @RequestMapping(path = "/list", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO findDummyList(DummyVO param) {
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                log.info("Param: {}", param);
                return dummyService.findDummyList(1);
            }
        });
    }

    @ApiOperation(value = "Paging query Dummy list", notes = "")
    @ApiImplicitParam(name = "param", value = "Dummy details", required = true, dataType = "DummyVO")
    /**
    * Invoke sample:
    * POST http://localhost:8080/SpringBootDemo/dummy/page/3/2 BODY { "userId": "1", "userName": "Alice" }
    * 
    * Note: get pageSize, pageIndex from pageVO
    * 
    * @param param 
    * @param pageVO (pageSize, pageIndex), pageIndex start from 1
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

    @RequestMapping(path = "/downloadLog", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO downloadLog() {
        return process(new AppRunnable() {
            public void run() throws ServiceException {
                dummyService.downloadLog();
            }
        });
    }

    @RequestMapping(path = "/uploadDoc", consumes = "multipart/form-data", method = RequestMethod.POST, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO uploadDoc(@RequestParam MultipartFile multiFile) {
        return process(new AppRunnable() {
            public void run() throws ServiceException {
                try (InputStream in = multiFile.getInputStream()) {
                     FileUtils.copyInputStreamToFile(in, new File("c:/" + multiFile.getOriginalFilename()));
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new ServiceException(ERR_UPLOAD);
                }                
            }
        });
    }

    /**
     * Test PathVariable, RequestParam
     * GET http://localhost:8080/SpringBootDemo/dummy/testPathAndParams/1?userName=Alice
     */
    @RequestMapping(path = "/testPathAndParams/{userId}", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO testParams(@PathVariable int userId, @RequestParam String userName) {
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                log.info("userId: {}, userName: {}", userId, userName);
                return "OK";
            }
        });
    }

    @ApiOperation(value = "get system information", notes = "")
    /**
    * Get system information
    */
    @RequestMapping(path = "/sysInfo", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO sysInfo() {
        HttpUtil.getRequest().getSession().setAttribute("USER_INFO_BEAN", "Louis");
        
        return process(new AppCallable() {
            public Object run() throws ServiceException {
                return dummyService.getSysInfo();
            }
        });
    }
    
    @RequestMapping(path = "/fails", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO fails() {
        throw new ServiceException("Assumed Error");
    }    

    protected void addCookie() {
        Cookie k = new Cookie("USER_NAME", "Louis");
        k.setMaxAge(-1); //0: delete -1: delete when browser exit
        k.setDomain("zero.com");
        k.setPath("/");
        HttpUtil.getResponse().addCookie(k);
    }
    
    @RequestMapping(path = "/getToken", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    public @ResponseBody ResponseVO getToken() {
        String statusCode = STATUS_ERR;
        String statusMsg = "";
        Object obj = null;
        try {
            UserInfoBean ui = getUserInfoBean();
            if (ui != null) {
                String token = UUID.randomUUID().toString();
                userTokenMap.put(ui.getUid(), token);

                obj = token;
                statusCode = STATUS_OK;
                statusMsg = getMessage(MSG_SUCCESS);
            } else {
                statusCode = STATUS_NOT_LOGIN;
            }
        } catch (Exception e) {
            log.error("{}", e);
            statusMsg = getMessage(ERR_FAIL);
        }

        return new ResponseVO(statusCode, statusMsg, obj);
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
