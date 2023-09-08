package com.zero.core.tasks;

import com.zero.core.ServiceException;

/**
 * AppCallable
 * 
 * @author Louisling
 * @since 2018-07-01
 */
public interface AppCallable extends AppTask {
    Object run() throws ServiceException;
}
