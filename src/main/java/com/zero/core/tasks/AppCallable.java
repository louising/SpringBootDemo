package com.zero.core.tasks;

import com.zero.demo.ServiceException;

public interface AppCallable extends AppTask {
    Object run() throws ServiceException;
}
