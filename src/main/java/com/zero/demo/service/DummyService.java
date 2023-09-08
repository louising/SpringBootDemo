package com.zero.demo.service;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zero.core.ServiceException;
import com.zero.core.domain.AccessVO;
import com.zero.core.domain.PageResultVO;
import com.zero.core.domain.PageVO;
import com.zero.demo.vo.DummyVO;

/**
 * DummyService
 * 
 * @author Louisling
 * @version 2018-07-10
 */
public interface DummyService {
    Map<String, String> getSysInfo();

    /**
     * Find dummy list where userId >= ${userId}
     * 
     * @param userId
     * @return
     * @throws ServiceException
     */
    List<DummyVO> findDummyList(int userId) throws ServiceException;
    
    List<DummyVO> findProjectList();

    PageResultVO findDummyPage(DummyVO scheme, PageVO pageVO) throws ServiceException;

    Object addDummy(DummyVO dummyVO) throws ServiceException;

    void downloadLog() throws ServiceException;

    void addAccessRecord(AccessVO vo) throws ServiceException;

    PageResultVO findAccessPage(PageVO pageVO) throws ServiceException;

    String uploadFile(MultipartFile multiFile) throws ServiceException;
    
    void uploadDoc(@RequestParam MultipartFile multiFile) throws ServiceException;
    
    String testParams(int userId, String userName) throws ServiceException;
}

