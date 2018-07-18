package com.zero.springboot.demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.zero.demo.DemoApplication;

/**
 * RestTemplate lies in spring-web.jar
 * By default the RestTemplate relies on standard JDK facilities to establish HTTP connections.
 * If switch to other HTTP library, such as httpclient(Netty, OkHttp), it should be 4.5.5
 * There maybe indirect dependency, such as minio which depends on httpclient 4.1 that will missing HTTPClients.
 * 
 * @author Louisling
 * @version 2018-07-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DummyControllerTest {
    //@Value("${local.server.port}")
    @LocalServerPort
    private int port;

    @Test
    public void testGet() {
        System.out.println("\nPort1: " + port);
        
        RestTemplate restTemplate = new RestTemplate();        
        String url = "http://localhost:" + port + "/SpringBootDemo/dummy/sysInfo";
        String result = restTemplate.getForObject(url, String.class);        
        System.out.println("GET: " + result);
    }
    
    @Test
    public void testGetWithParameters() {
        System.out.println("\nPort2: " + port);
        Map<String, String> params = new HashMap<>();
        params.put("country", "USA");
        params.put("city", "New York");
        
        RestTemplate restTemplate = new RestTemplate();        
        String url = "http://localhost:" + port + "/SpringBootDemo/dummy/sysInfo?country={country}&city={city}";        
        String result = restTemplate.getForObject(url, String.class, params);
        System.out.println("GET: " + result);
    }
    
    @Test
    public void testGetWithHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("key1", "Alice");
        requestHeaders.add("key2", "Bob");
        
        RestTemplate restTemplate = new RestTemplate(); 
        String url = "http://localhost:8080/SpringBootDemo/dummy/sysInfo?country=USA";
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String str = response.getBody();
        System.out.println("Result: " + str);
    }
    
    @Test
    public void testPost() {
        RestTemplate restTemplate = new RestTemplate();        
        System.out.println("\nPort3: " + port);       

        String url = "http://localhost:" + port + "/SpringBootDemo/dummy/page/3/1";
        Map<String, Object> body = new HashMap<>();
        body.put("userId", 2);
        body.put("userName", "Alice");
        
        String result = restTemplate.postForObject(url, body, String.class);
        System.out.println("POST: " + result);
    }
    
    @Test
    public void testPostWithHeaderAndBody() {                
        System.out.println("\nPort4: " + port);       

        //Body
        String url = "http://localhost:" + port + "/SpringBootDemo/dummy/page/3/2";
        Map<String, Object> body = new HashMap<>();
        body.put("userId", 2);
        body.put("userName", "Alice");
        
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("languageToken", "Zero");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);        
        
        //POST (header+body)
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(url, entity, String.class);
        System.out.println("POST: " + result);
    }    
}
