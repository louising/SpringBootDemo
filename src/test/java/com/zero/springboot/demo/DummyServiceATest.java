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
public class DummyServiceATest {
    Logger log = LoggerFactory.getLogger(DummyServiceATest.class);

    @Autowired
    private DummyDao dummyDao;

    @Test
    public void testFindDummyList() {
        List<DummyVO> list = dummyDao.findDummyList(1);
        log.info("Dummy List: " + list);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testFindDummySingle() {
        List<DummyVO> list = dummyDao.findDummyList(100);
        log.info("Dummy List: " + list);
        Assert.assertNotNull(list);
    }
}
