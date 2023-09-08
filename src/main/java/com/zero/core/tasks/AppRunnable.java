package com.zero.core.tasks;

import com.zero.core.ServiceException;

/**
 * AppRunnable
 * 
 * @author Louisling
 * @since 2018-07-01
 */
public interface AppRunnable extends AppTask {
    void run() throws ServiceException;
}