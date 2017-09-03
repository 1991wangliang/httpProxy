package com.lorne.http.service.impl;

import com.lorne.http.service.HttpServer;
import com.lorne.http.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequest;
import org.littleshoot.proxy.*;
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
                .withFiltersSource(new HttpFiltersSourceAdapter(){
                    @Override
                    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                        return super.filterRequest(originalRequest, ctx);
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
