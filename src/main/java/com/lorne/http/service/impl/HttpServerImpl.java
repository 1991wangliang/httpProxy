package com.lorne.http.service.impl;

import com.lorne.http.service.HttpServer;
import com.lorne.http.service.UserService;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.ProxyAuthenticator;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * create by lorne on 2017/8/25
 */
@Service
public class HttpServerImpl implements HttpServer {

    @Value("${http.port}")
    private int httpPort;

    private  HttpProxyServer server = null;

    @Autowired
    private UserService userService;

    @Override
    public void start() {
       server = DefaultHttpProxyServer.bootstrap()
               .withProxyAuthenticator(new ProxyAuthenticator() {

                   @Override
                   public boolean authenticate(String userName, String password) {
                       return userService.authenticate(userName,password);
                   }

                   @Override
                   public String getRealm() {
                       return "admin";
                   }
               })
               .withAddress(new InetSocketAddress(httpPort))
               .start();

    }

    @Override
    public void close() {
        server.stop();
    }
}