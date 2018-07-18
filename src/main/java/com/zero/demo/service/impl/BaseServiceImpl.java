package com.zero.demo.service.impl;

import static com.zero.demo.constants.I18NConstants.ERR_FAIL;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.core.domain.BaseVO;
import com.zero.core.domain.PageResultVO;
import com.zero.core.domain.PageVO;
import com.zero.demo.ServiceException;

/**
 * BaseServiceImpl
 * 
 * @author Louisling
 * @version 2018-07-10
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl {
    protected Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
    
    protected String getCurrentUserId() {
        //After login: HttpUtil.getRequest().setAttribute(SESSION_USER_KEY, new User(100, "Louis"));
        return "user1001"; //Get from session
    }

    protected PageResultVO doPagedQuery(Object dao, String daoMethodName, Object parameterVO, PageVO pageVO) throws ServiceException {
        PageResultVO pagedResult = null;
        try {
            //1) Get recordCount 
            Method countMethod = null;
            Integer recordCount = 0;

            if (parameterVO != null) {
                countMethod = dao.getClass().getDeclaredMethod(daoMethodName + "Count", parameterVO.getClass());
                recordCount = (Integer) countMethod.invoke(dao, parameterVO);
            } else {
                countMethod = dao.getClass().getDeclaredMethod(daoMethodName + "Count");
                recordCount = (Integer) countMethod.invoke(dao);
            }

            //2) Fill PageVO

            //int startIndex = pageVO.getPageSize() * (pageVO.getPageIndex() - 1) + 1; for Oracle
            int startIndex = pageVO.getPageSize() * (pageVO.getPageIndex() - 1); //for MySQL, H2
            pageVO.setStartIndex(startIndex);
            pageVO.setEndIndex(Math.min((startIndex + pageVO.getPageSize() - 1), recordCount)); //for Oracle

            pageVO.setRecordCount(recordCount);

            //3) Fill page query parameters(Oracle: startIndex,endIndex;  MySQL/H2: startIndex,pageSize)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("startIndex", pageVO.getStartIndex());
            parameters.put("endIndex", pageVO.getEndIndex());
            parameters.put("pageSize", pageVO.getPageSize());

            // query parameters
            if (parameterVO != null) {
                Field[] fields = parameterVO.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    parameters.put(field.getName(), field.get(parameterVO));
                }
                if (parameterVO.getClass().getSuperclass() == BaseVO.class) {
                    fields = parameterVO.getClass().getSuperclass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        parameters.put(field.getName(), field.get(parameterVO));
                    }
                }
            }

            //4) Do query
            Method queryMethod = dao.getClass().getDeclaredMethod(daoMethodName, Map.class);
            List<T> records = (List<T>) queryMethod.invoke(dao, parameters);

            pagedResult = new PageResultVO(pageVO, records);
        } catch (Exception e) {
            log.error("{}", e);
            throw new ServiceException(ERR_FAIL);
        }

        return pagedResult;
    }

}
