About
    It demonstrates these features:    
        1) Expose RESTful web service by Spring Boot through annotation.
        2) DAO by MyBatis(CRUD, paging query, transaction)
        3) Exception handler
        4) I18N
        5) XRSF defense(the simplest way:)
        6) CORS(Cross-origin resource sharing)
        7) jUnit test
        8) Swagger //http://localhost:8080/SpringBootDemo/swagger-ui.html
        9) Slf4j + Logback
        A) Schedule
        B) Encryption/Decryption
        C) Session management(just for simple demo, in product environment should use SSL and encrypted cookie, or SSO)
    
    This demo application is arranged in 3 lays:
        1) Dao        //Access DB
        2) Service    //Business logic
        3) Controller //Expose service as Restful web service, permisson control, XRSF check
        
Usage
    1) Configuration
        A) application.yml 
           Config context, port, datasource, configuration directory
           
        B) logback.xml
           Output log(console, file, DB...)
           
    2) Dao 
       e.g. DummyDao.java, DummyDao.xml
       
    3) Service
       e.g. DummyServiceImpl.java 
       
    4) Controller
       e.g. DummyController
    
    5) Run
        A) Start DB (H2 DB)
           c:\>java -jar "H:/lib/java/h2-1.4.197.jar"
           
           URL: jdbc:h2:tcp://localhost/~/H2DB-SpringRestDemo
           user: sa
           password: sa
           
        B) DemoApplication.java ==> Run as Java Application
           ...
           Started DemoApplication in 9.972 seconds (JVM running for 10.838) 
       
    6) Access exposed RESTful web services in browser, JS, PostMan or other client sides.
    
       API DOC: http://localhost:8080/SpringBootDemo/swagger-ui.html
       
       GET http://localhost:8080/SpringBootDemo/dummy/sysInfo
       GET http://localhost:8080/SpringBootDemo/dummy/list/2?userName=Alice

       //Paging query: /dummy/page/{pageSize}/{pageIndex}
       POST http://localhost:8080/SpringBootDemo/dummy/page/3/1
           BODY: { "userId": 2, "userName": "Alice"}
                   
Note        
    1. DAO
       1) Config datasource in application.yml/spring/datasource
       2) DataSourceConf.java/@MapperScan("com.zero.demo.dao")
       3) Write com.zero.demo.dao/xxxDao.java,xxxDao.xml
       4) Call Dao in DummyServiceImpl.java
            @Autowired
            private DummyDao dummyDao;              
    
    2. Expose RESTful web service
       1) class DummyController extends BaseController
       2) Add @RestController, @RequestMapping
       3) Call BaseController.process() that passed in invocation to DummyService
    
    3. Exception handler in BaseController.java
       1) DummyServiceImpl.java/addDummy()
          ... 
          throw new ServiceException("USER_ID_INVALID"); //Extract constant          
       2) Define i18n message in messages_*.properties for message_code of "USER_ID_INVALID"
       3) The controller will process the exception and return error message
       
    4. I18N
       1) Set request header or url parameter: "languageToken=en-US" //or zh-CN
       2) i18n/messages_en_US.properties
              COUNTRY=The country is China
              ADDRESS=The address is province {0} city {1} Area {2}
       3) TestServiceImpl.java
              String country = ResourceUtil.getMessage("COUNTRY"); 
              String address = ResourceUtil.getMessage("ADDRESS", "Guangdong", "ShenZhen", "South Hill");
                  
    5. XRSF
       1) After login, invoke "GET /dummy/getToken" to get token
       2) Each time request except login/logout,  add the header of "token"   

    6. CORS
       FilterConf.java/corsFilter()

    7. jUnit test
       Single Unit Test: DummyServiceATest, DummyServiceBTest
       Run all unit tests : AllTest.java
       
    8. Swagger
       Swagger2Conf.java
       Access at: http://localhost:8080/SpringBootDemo/swagger-ui.html
       
    9. Slf4j + Logback
       logback.xml
       
    A. Schedule
       CountJobConf.java
        
    B. Encryption/Decryption
       SecurityUtil.java
        
    C. Session management(just for simple demo, in product environment should use SSL and encrypted cookie, or SSO)
       SessionManager.java
              