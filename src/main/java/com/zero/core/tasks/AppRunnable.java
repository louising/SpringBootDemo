package com.zero.core.tasks;

import com.zero.demo.ServiceException;

public interface AppRunnable extends AppTask {
    void run() throws ServiceException;
}