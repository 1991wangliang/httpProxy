package com.lorne.http.service.impl;

import com.lorne.core.framework.utils.config.ConfigHelper;
import com.lorne.http.service.UserService;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/8/25
 */
@Service
public class UserServiceImpl implements UserService{


    private ConfigHelper helper = null;

    public UserServiceImpl() {
        helper = new ConfigHelper("users.properties");
    }

    @Override
    public boolean authenticate(String userName, String password) {
        return password.equals(helper.getStringValue(userName));
    }

}
