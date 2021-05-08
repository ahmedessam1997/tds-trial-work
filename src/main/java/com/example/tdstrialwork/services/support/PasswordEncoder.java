package com.example.tdstrialwork.services.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String hash(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
