package com.zero.springboot.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zero.demo.SpringBootDemoApplication;
import com.zero.demo.dao.DummyDao;

@SuppressWarnings("rawtypes")
/**
 * DummyServiceBTest
 * 
 * @author Louisling
 * @since 2021-12-10
 */
@SpringBootTest(classes = SpringBootDemoApplication.class) 
public class DummyServiceBTest {
    Logger log = LoggerFactory.getLogger(DummyServiceBTest.class);
    
    @Autowired
    private DummyDao dummyDao;
    
    @Test
    public void testFindSingleDummy() {
        List<Map> list = dummyDao.users();
        log.info("Dummy List: " + list);
        assertEquals(true, list != null && list.size() > 0);
        assertEquals(true, list.size() > 0);
    }
    
    @Test
    public void testFindMultipleDummy() {
        List<Map> list = dummyDao.users();
        log.info("Dummy List: " + list);
        assertEquals(true, list != null && list.size() == 0);
    }
}
