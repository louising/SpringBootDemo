package com.zero.demo.controller;

import static com.zero.demo.constants.BaseConstants.STATUS_ERR;
import static com.zero.demo.constants.BaseConstants.STATUS_NOT_LOGIN;
import static com.zero.demo.constants.BaseConstants.STATUS_OK;
import static com.zero.demo.constants.I18NConstants.ERR_FAIL;
import static com.zero.demo.constants.I18NConstants.MSG_SUCCESS;
import static com.zero.demo.util.I18nUtil.getMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.zero.core.BaseController;
import com.zero.core.ServiceException;
import com.zero.core.domain.PageVO;
import com.zero.core.domain.ResponseVO;
import com.zero.demo.service.impl.DummyServiceImpl;
import com.zero.demo.util.HttpUtil;
import com.zero.demo.vo.DummyVO;
import com.zero.demo.vo.UserInfoBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "The Sample Controller", value = "Sample Controller Value", produces = "The Produces")
/**
 * http://localhost:8080/SpringBootDemo/swagger-ui.html
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
 *    GET http://localhost:8080/SpringBootDemo/dummy/list2?sessionCode=123
 *    
 * @author Louis
 * @version 2017-11-24
 */
@RestController
@RequestMapping("/dummy")
public class DummyController extends BaseController {
    private Logger log = LoggerFactory.getLogger(DummyController.class);

    //By default, read property from application.yml or application.properties
    //If read from app.yaml/app.properties, add @PropertySource("classpath:/com/acme/app.properties")
    @Value("${appconf.version}")
    private Long version;

    @Autowired
    private DummyServiceImpl dummyService;
    
    static String APP_ID = "app001";
    static String APP_SECRET = "secret001";
    static String URL_WX_SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + APP_SECRET + "&grant_type=authorization_code";
       
    //param: {code, userName,tel,inviteCode}
    //After register, return a jsession,roleCode
    //Or inviteCode is invalid or has been used.
    @PostMapping("/register")
    public ResponseVO register(@RequestBody UserInfoBean u) {
        log.info("Register: " + u);
        // TODO Check userName, tel, code
        String code = u.getCode();
        String userName = u.getUserName();
        String tel = u.getTel(); //130-1234-5678
        String inviteCode = u.getInviteCode();
        //Check inviteCode is valid
        
        //1) To get openId by wx(code, appId,appSecret)
        String url = URL_WX_SESSION + "&js_code=" + code;
        String res = HttpUtil.sendGet(url, null);
        log.info("" + res);
        JSONObject obj = JSONObject.parseObject(res);
        String openId = obj.getString("openid"); // session_key
        log.info("code {} openId {}", code, openId);
        //TODO exception handler. if openId = null
        
        String sessionCode = getSessionCode();
        
        //2) DB: Add (openId, userName, tel)
        //3) DB: Update the code has been used
        //4) Generate SessionCode, save to Map<sessionCode, openId>
        
        //5) Return sessioncode,roleCode,roleName        
        ResponseVO result = new ResponseVO();
        Map<String, String> map = new HashMap<>();
        map.put("originalCode: ", code);
        map.put("sessionCode", sessionCode);
        map.put("roleCode", "002");
        map.put("roleName", "AreaAdmin");
        result.setData(map);
        return result;
    }
    
    String getSessionCode() {
        String token = UUID.randomUUID().toString();
        return token.replace("-", ""); //32-bits
    }
        
    /**
     * @return {sessioncode,roleCode,roleName}
     */
    @GetMapping("/login")
    public ResponseVO login(@RequestParam String code) {
        //1) Get openId by (code, appId,appSecret)
        String url = URL_WX_SESSION + "&js_code=" + code;
        String res = HttpUtil.sendGet(url, null);
        log.info(res);
        JSONObject obj = JSONObject.parseObject(res);
        String openId = obj.getString("openid"); // session_key
        log.info("code {} openId {}", code, openId);
        
        //2) If user exists, generate jsessioncode, return (jsession,roleCode,roleName)
        //TODO found user in DB. "select * from t_user where openId = ?"
        String sessionCode = getSessionCode();
        
        //TODO if not found, new ResponseVO("501", "User doesnot exist, Please get inviteCode to join")
        ResponseVO result = new ResponseVO();
        Map<String, String> map = new HashMap<>();
        map.put("sessionCode", sessionCode);
        map.put("roleCode", "002");
        map.put("roleName", "AreaAdmin");
        result.setData(map);
        
        return result;
    }

    //Use org.springframework.http.converter.json.Jackson2ObjectMapperBuilder to convert JSON to Object
    //@RequestMapping(path = "/add", method = RequestMethod.POST)
    //public @ResponseBody ResponseVO add(@RequestBody DummyVO param) {
    @PostMapping("/add")
    public ResponseVO add(@RequestBody DummyVO param) {
        return process(() -> dummyService.addDummy(param));
    }

    //If add @RequestBody, must provide body
    //Will auto fill paramVO with request parameters
    /**
     * DELETE http://localhost:8080/SpringBootDemo/dummy/del?dummyName=Alice001 BODY { "userId": "1", "userName": "Alice" }
     * DELETE http://localhost:8080/SpringBootDemo/dummy/del?dummyName=Alice01&userId=102&userName=Alice02 BODY { "userId": "103", "userName": "Alice03" }
     */
    //@RequestMapping(path = "/del", method = RequestMethod.DELETE)
    @DeleteMapping("/del")
    public ResponseVO del(@RequestParam("dummyName") String userName, DummyVO paramVO, @RequestBody DummyVO body) {
        return process(() -> log.info("userName: {}, param: {}, body {}", userName, paramVO, body));
    }

    //update is a idempotent operation which does not create new resource
    //URL parameters will be filled to method parameter
    //if has @RequestBody, must provide body
    /**
     * PUT http://localhost:8080/SpringBootDemo/dummy/upd?userId=102&userName=Alice02 BODY { "userId": "103", "userName": "Alice03" }
     */
    //@RequestMapping(path = "/upd", method = RequestMethod.PUT)
    @PutMapping("/upd")
    public ResponseVO upd(DummyVO requestParam, @RequestBody DummyVO body) {
        return process(() -> log.info("Param: {}, Body: {}", requestParam, body));
    }

    /**
     * GET http://localhost:8080/SpringBootDemo/dummy/list
     */
    //@RequestMapping(path = "/list", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    @GetMapping("/list")
    public ResponseVO list() {
        return process(() -> {
            return dummyService.users();
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
    //@RequestMapping(path = "/page/{pageSize}/{pageIndex}", method = RequestMethod.POST, produces = RESPONSE_TYPE)
    @PostMapping("/page/{pageSize}/{pageIndex}")
    public ResponseVO findDummyPage(@RequestBody DummyVO param, PageVO pageVO) {
        return process(() -> dummyService.findDummyPage(param, pageVO));        
    }

    //@RequestMapping(path = "/downloadLog", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    @GetMapping("/downloadLog")
    public ResponseVO downloadLog() {
        return process(()-> dummyService.downloadLog());        
    }

    //@RequestMapping(path = "/uploadDoc", consumes = "multipart/form-data", method = RequestMethod.POST, produces = RESPONSE_TYPE)
    @PostMapping(path = "/uploadDoc", consumes = "multipart/form-data")
    public ResponseVO uploadDoc(@RequestParam MultipartFile multiFile) {
        return process(()-> dummyService.uploadDoc(multiFile));        
    }

    /**
     * Test PathVariable, RequestParam
     * GET http://localhost:8080/SpringBootDemo/dummy/testPathAndParams/1?userName=Alice
     */
    //@RequestMapping(path = "/testPathAndParams/{userId}", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    @GetMapping("/testPathAndParams/{userId}")
    public ResponseVO testParams(@PathVariable int userId, @RequestParam String userName) {
        return process(()-> dummyService.testParams(userId, userName));
    }

    @ApiOperation(value = "get system information", notes = "")
    /**
    * Get system information
    */
    //@RequestMapping(path = "/sysInfo", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    @GetMapping("/sysInfo")
    public ResponseVO sysInfo() {
        HttpUtil.getRequest().getSession().setAttribute("USER_INFO_BEAN", "Louis");
        return process(() -> dummyService.getSysInfo());        
    }

    //@RequestMapping(path = "/fails", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    @GetMapping("/fails")
    public ResponseVO fails() {
        throw new ServiceException("Assumed Error");
    }

    protected void addCookie() {
        Cookie k = new Cookie("USER_NAME", "Louis");
        k.setMaxAge(-1); //0: delete -1: delete when browser exit
        k.setDomain("zero.com");
        k.setPath("/");
        HttpUtil.getResponse().addCookie(k);
    }

    //@RequestMapping(path = "/getToken", method = RequestMethod.GET, produces = RESPONSE_TYPE)
    @GetMapping("/getToken")
    public ResponseVO getToken() {
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


    
    @GetMapping("/projects")
    public ResponseVO projects(String projectName, PageVO pageVO) {
        log.info("Project: {} PageVO {}", projectName, pageVO);
        
        Map<String, String> map = new HashMap<String,String>();
        map.put("project_name", projectName);
        return process(() -> dummyService.findProjectPage(map, pageVO));
    }
}
