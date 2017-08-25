package com.lorne.http.service.impl;

import com.lorne.http.service.HttpServer;
import com.lorne.http.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/8/25
 */
@Service
public class InitServiceImpl implements InitService {

    @Autowired
    private HttpServer httpServer;


    @Override
    public void start() {
        httpServer.start();
    }

    @Override
    public void close() {
        httpServer.close();
    }
}
