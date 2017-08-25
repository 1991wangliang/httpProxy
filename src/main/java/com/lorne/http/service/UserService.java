package com.lorne.http.service;

/**
 * create by lorne on 2017/8/25
 */
public interface UserService {

    boolean authenticate(String userName, String password);
}
