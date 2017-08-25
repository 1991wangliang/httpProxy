package com.lorne.http.service.impl;

import com.lorne.http.service.UserService;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/8/25
 */
@Service
public class UserServiceImpl implements UserService{


    private PropertiesConfiguration configuration = null;

    public UserServiceImpl() {
        try {
            configuration = new PropertiesConfiguration("users.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean authenticate(String userName, String password) {
        return password.equals(configuration.getString(userName));
    }

}
