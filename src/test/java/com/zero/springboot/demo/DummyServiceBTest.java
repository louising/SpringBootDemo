package com.zero.springboot.demo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zero.demo.DemoApplication;
import com.zero.demo.dao.DummyDao;
import com.zero.demo.vo.DummyVO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class) 
public class DummyServiceBTest {
    Logger log = LoggerFactory.getLogger(DummyServiceBTest.class);
    
    @Autowired
    private DummyDao dummyDao;
    
    @Test
    public void testFindSingleDummy() {
        List<DummyVO> list = dummyDao.findDummyList(3);
        log.info("Dummy List: " + list);
        Assert.assertTrue(list.size() > 0);
    }
    
    @Test
    public void testFindMultipleDummy() {
        List<DummyVO> list = dummyDao.findDummyList(1000);
        log.info("Dummy List: " + list);
        Assert.assertTrue(list.size() == 0);
    }
}
