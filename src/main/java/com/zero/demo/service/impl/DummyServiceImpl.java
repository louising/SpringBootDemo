package com.zero.demo.service.impl;

import static com.zero.demo.constants.BaseConstants.DIR_CURR;
import static com.zero.demo.constants.I18NConstants.ERR_FAIL;
import static com.zero.demo.util.BaseUtil.getTmpDir;
import static com.zero.demo.util.BaseUtil.writeFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zero.core.domain.AccessVO;
import com.zero.core.domain.PageResultVO;
import com.zero.core.domain.PageVO;
import com.zero.demo.ServiceException;
import com.zero.demo.conf.AppConf;
import com.zero.demo.conf.ConfigValueConf;
import com.zero.demo.dao.DummyDao;
import com.zero.demo.service.DummyService;
import com.zero.demo.util.BaseUtil;
import com.zero.demo.util.FileUtil;
import com.zero.demo.util.HttpUtil;
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
public class DummyServiceImpl extends BaseServiceImpl implements DummyService {
    Logger log = LoggerFactory.getLogger(DummyServiceImpl.class);

    final static String LOG_DIR = "/SpringBootDemo/logs/"; //refer to logback.xml
    final static String LOG_ZIP_FULL_FILE_NAME = LOG_DIR + "logs.zip";

    static final List<String> supportedDocTypes = new ArrayList<>();

    @Autowired
    private ConfigValueConf configVO;

    @Autowired
    private AppConf appConf;
    
    @Autowired
    private DummyDao dummyDao;
    
    static {
        supportedDocTypes.add("zip");
    }

    public Map<String, String> getSysInfo() {
        HttpUtil.getRequest().setAttribute("userBean", new DummyVO(100, "Louis"));
        
        Map<String, String> map = BaseUtil.getSysInfo();
        map.put("version", appConf.getVersion() + "");
        map.put("configPath", configVO.getConfigPath());
        return map;
    }

    public List<DummyVO> findDummyList(int userId) throws ServiceException {
        Object userBean = HttpUtil.getRequest().getAttribute("userBean");
        System.out.println("UserBean: " + userBean);
        log.info("userId: " + userId + " config path: " + configVO.getConfigPath());

        if (userId < 1) {
            throw new ServiceException("USER_ID_INVALID");
        }

        return dummyDao.findDummyList(userId);
    }

    public PageResultVO findDummyPage(DummyVO paramaterVO, PageVO pageVO) throws ServiceException {        
        return doPagedQuery(dummyDao, "findDummyPage", paramaterVO, pageVO);
    }
    
    public Integer addDummy(DummyVO dummyVO) throws ServiceException {
        log.info("add dummy: " + dummyVO);
        if (dummyVO.getUserName() == null)
            throw new ServiceException("ERR_USER_NAME_CAN_NOT_NULL");

        dummyDao.addDummy(dummyVO);

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

    @Override
    public void addAccessRecord(AccessVO vo) throws ServiceException {
        //dummyDao.addAccessRecord(vo);
    }

    public PageResultVO findAccessPage(PageVO pageVO) throws ServiceException {
        return doPagedQuery(dummyDao, "findAccessPage", null, pageVO);
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
}
