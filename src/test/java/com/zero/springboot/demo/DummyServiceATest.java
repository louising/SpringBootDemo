package com.zero.springboot.demo;

/*import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;*/

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
 * DummyServiceATest
 * 
 * @author Louisling
 * @since 2021-12-10
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class) 
public class DummyServiceATest {
    Logger log = LoggerFactory.getLogger(DummyServiceATest.class);

    @Autowired
    private DummyDao dummyDao;


    @Test
    public void testFindDummyList() {
        List<Map> list = dummyDao.users();
        log.info("Dummy List: " + list);
        //Assert.assertTrue(list.size() > 0);
        
        assertEquals(true, list != null && list.size() > 0);
        assertEquals(true, list.size() > 0);
    }

    @Test
    public void testFindDummySingle() {
        List<Map> list = dummyDao.users();
        log.info("Dummy List: " + list);
        assertEquals(true, list != null && list.size() > 0);
    }
}
