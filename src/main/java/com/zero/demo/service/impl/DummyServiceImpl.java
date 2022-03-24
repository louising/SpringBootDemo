package com.zero.demo.service.impl;

import static com.zero.demo.constants.BaseConstants.DIR_CURR;
import static com.zero.demo.constants.I18NConstants.ERR_FAIL;
import static com.zero.demo.constants.I18NConstants.ERR_UPLOAD;
import static com.zero.demo.util.BaseUtil.getTmpDir;
import static com.zero.demo.util.BaseUtil.writeFile;
import static com.zero.demo.util.BaseUtil.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zero.core.domain.AccessVO;
import com.zero.core.domain.PageResultVO;
import com.zero.core.domain.PageVO;
import com.zero.demo.ServiceException;
import com.zero.demo.conf.AppConf;
import com.zero.demo.conf.ConfigValueConf;
import com.zero.demo.dao.DummyDao;
import com.zero.demo.util.BaseUtil;
import com.zero.demo.util.FileUtil;
import com.zero.demo.util.IoUtil;
import com.zero.demo.util.ZipUtils;
import com.zero.demo.vo.DummyVO;

/**
 * Dummy service implementation
 * 
 * @author Louisling
 * @version 2018-07-01
 */
@Component
public class DummyServiceImpl extends BaseServiceImpl {
    Logger log = LoggerFactory.getLogger(DummyServiceImpl.class);

    final static String LOG_DIR = "/SpringBootDemo/logs/"; //refer to logback.xml
    final static String LOG_ZIP_FULL_FILE_NAME = LOG_DIR + "logs.zip";

    static final List<String> supportedDocTypes = new ArrayList<>();

    @Autowired
    private ConfigValueConf configVO;

    @Autowired
    private AppConf appConf;
    
    @Autowired
    private DummyDao dao;
    
    static {
        supportedDocTypes.add("zip");
    }

    public Map<String, String> getSysInfo() {
        Map<String, String> map = BaseUtil.getSysInfo();
        map.put("version", appConf.getVersion() + "");
        map.put("configPath", configVO.getConfigPath());
        return map;
    }
    
    /* User */
    public List<Map> users() throws ServiceException {
        return dao.users();
    }
    public PageResultVO usersPage(PageVO pageVO) throws ServiceException {
        return doPagedQuery(dao, "usersPage", null, pageVO);
    }
    
    /* Project */
    public PageResultVO findProjectPage(Map<String, String> param, PageVO pageVO) throws ServiceException {
        PageResultVO vo = doPagedQuery(dao, "findProjectPage", param, pageVO);
        formateDateTime((List) vo.getRecords(), "create_time");
        return vo;
    }
    
    public void delProject(String projectId) {
        dao.delProject(projectId);
    }
    
    public void saveProject(Map<String, String> map) {
        //1
        String projectId = map.get("project_id");
        if (isEmpty(projectId)) {
            projectId = BaseUtil.getPkCode();
            map.put("project_id", projectId);
            dao.addProject(map);
        } else {
            dao.updProject(map);
        }
    }
    
    public Map<String, Object> getProject(String projectId) {
        return dao.getProject(projectId);
    }
    
    private void formateDateTime(List<Map<String, Object>> list, String... fieldNames) {
        for (Map map : list) {
            for (String fieldName : fieldNames) {
                map.put(fieldName, BaseUtil.formatDateTime((java.sql.Timestamp) map.get(fieldName)));
            }
        }
    }

    
    
    public PageResultVO findDummyPage(DummyVO param, PageVO pageVO) throws ServiceException {
        log.info("Param: {}, PageVO: {}", param, pageVO);
        return doPagedQuery(dao, "findDummyPage", param, pageVO);
    }
    
    @Transactional //rollbackFor = ServiceException.class
    public Integer addDummy(DummyVO dummyVO) throws ServiceException {
        log.info("add dummy: " + dummyVO);
        dao.addDummy(dummyVO);
        
        if (dummyVO.getUserName().equals("ERROR"))
            throw new ServiceException("ERR_USER_NAME");

        return dummyVO.getUserId();
    }    

    public void downloadLog() throws ServiceException {
        //1) Log
        File file = new File(LOG_DIR);
        File[] files = file.listFiles(); //if file not exist, file.listFiles() will return null, if file.exist(), will return [] 
        if (files == null || files.length == 0) {
            throw new ServiceException("No log files");
        }

        StringBuffer sb = new StringBuffer("logs\n");
        for (int i = 0; i < files.length; i++) {
            sb.append(i + ": " + files[i].getName() + "\n");
        }
        log.info(sb.toString());

        //2) Download
        File zipFile = new File(LOG_ZIP_FULL_FILE_NAME);
        try {
            ZipUtils.compressFiles(files, zipFile);
            IoUtil.writeFile2Response(zipFile);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            FileUtil.deleteFile(zipFile);
        }
    }

    public void addAccessRecord(AccessVO vo) throws ServiceException {
        //dummyDao.addAccessRecord(vo);
    }

    public PageResultVO findAccessPage(PageVO pageVO) throws ServiceException {
        return doPagedQuery(dao, "findAccessPage", null, pageVO);
    }

    public String uploadFile(MultipartFile multiFile) throws ServiceException {
        String extension = FilenameUtils.getExtension(multiFile.getOriginalFilename());
        String fileId = Long.toString(System.currentTimeMillis());
        String newFileName = FileUtil.buildFilePrefixName(getCurrentUserId(), fileId) + DIR_CURR + extension;
        String fullNewFileName = getTmpDir(configVO.getConfigPath()) + newFileName; // + multiFile.getOriginalFilename() 
        log.info("Upload doc to app server: {} ", fullNewFileName);

        try (InputStream in = multiFile.getInputStream()) {
            writeFile(in, fullNewFileName, supportedDocTypes);
            //Save to T_Attachment(newFileName, multiFile.getOriginalFilename())
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServiceException(ERR_FAIL);
        }

        return fileId;
    }
    
    public void uploadDoc(@RequestParam MultipartFile multiFile) throws ServiceException {
        try (InputStream in = multiFile.getInputStream()) {
            FileUtils.copyInputStreamToFile(in, new File("c:/" + multiFile.getOriginalFilename()));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServiceException(ERR_UPLOAD);
        }
    }
    
    public String testParams(int userId, String userName) throws ServiceException {
        log.info("userId: {}, userName: {}", userId, userName);
        return "OK";
    }
}
