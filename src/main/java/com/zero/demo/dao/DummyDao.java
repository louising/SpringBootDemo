package com.zero.demo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zero.demo.vo.DummyVO;

@SuppressWarnings("rawtypes")
public interface DummyDao {
    List<Map> users();
    
    List<DummyVO> usersPage(Map param);
    int usersPageCount();
    
    //Project Begin
    int addProject(Map<String, String> map);
    int updProject(Map<String, String> map);
    int delProject(@Param("project_id") String projectId);
    Map<String, Object> getProject(String projectId);
    
    List<Map<String, Object>> findProjectPage(Map<String, Object> param);
    int findProjectPageCount(HashMap<String, Object> param);    
    //Project End
    
    
    
    List<DummyVO> findDummyPage(Map<String, Object> parameters);
    int findDummyPageCount(DummyVO paramaterVO);
    void addDummy(DummyVO scheme);
    //void addAccessRecord(AccessVO vo);
    //List<AccessVO> findAccessPage(Map<String, Object> parameters);
    //int findAccessPageCount();
}
