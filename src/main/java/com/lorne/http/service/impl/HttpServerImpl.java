package com.lorne.http.service.impl;

import com.lorne.http.service.HttpServer;
import com.lorne.http.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import net.lightbody.bmp.mitm.CertificateAndKeySource;
import net.lightbody.bmp.mitm.KeyStoreFileCertificateSource;
import net.lightbody.bmp.mitm.RootCertificateGenerator;
import net.lightbody.bmp.mitm.keys.ECKeyGenerator;
import net.lightbody.bmp.mitm.manager.ImpersonatingMitmManager;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.InetSocketAddress;

/**
 * create by lorne on 2017/8/25
 */
@Service
public class HttpServerImpl implements HttpServer {

    @Value("${http.port}")
    private int httpPort;

    private HttpProxyServer server = null;

    @Autowired
    private UserService userService;





    @Override
    public void start() {

        // create a dyamic CA root certificate generator using Elliptic Curve keys
        RootCertificateGenerator ecRootCertificateGenerator = RootCertificateGenerator.builder()
                .keyGenerator(new ECKeyGenerator())     // use EC keys, instead of the default RSA
                .build();

        // save the dynamically-generated CA root certificate for installation in a browser
        ecRootCertificateGenerator.saveRootCertificateAsPemFile(new File("d:/lcn/myca.cer"));

        // save the dynamically-generated CA private key for use in future LittleProxy executions
        // (see CustomCAPemFileExample.java for an example loading a previously-generated CA cert + key from a PEM file)
        ecRootCertificateGenerator.savePrivateKeyAsPemFile(new File("d:/lcn/mykey.pem"),
                "mypasswod");


        ImpersonatingMitmManager mitmManager = ImpersonatingMitmManager.builder()
                .rootCertificateSource(ecRootCertificateGenerator)
                .serverKeyGenerator(new ECKeyGenerator())
                .build();

        HttpProxyServerBootstrap bootstrap = DefaultHttpProxyServer.bootstrap()
                .withManInTheMiddle(mitmManager)
                .withAddress(new InetSocketAddress(httpPort));

        server = bootstrap.start();


    }

    @Override
    public void close() {
        server.stop();
    }
}
