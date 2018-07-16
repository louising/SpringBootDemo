package com.zero.demo.dao;

import java.util.List;
import java.util.Map;

import com.zero.demo.vo.DummyVO;

public interface DummyDao {
    List<DummyVO> findDummyPage(Map<String, Object> parameters);
    int findDummyPageCount(DummyVO paramaterVO);

    List<DummyVO> findDummyList(int userId);

    void addDummy(DummyVO scheme);

    //void addAccessRecord(AccessVO vo);
    //List<AccessVO> findAccessPage(Map<String, Object> parameters);
    //int findAccessPageCount();
}
