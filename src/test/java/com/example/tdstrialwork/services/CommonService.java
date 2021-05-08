package com.example.tdstrialwork.services;

import com.example.tdstrialwork.data.entities.Device;
import com.example.tdstrialwork.data.entities.ESim;
import com.example.tdstrialwork.data.entities.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommonService {

    protected List<User> constructUsers() {
        List<User> users = new ArrayList<>();

        var user1 = constructUser(1L, "1234", "user1@gmail.com", "user1");
        users.add(user1);

        var user2 = constructUser(2L, "1111", "user2@gmail.com", "user2");
        users.add(user2);

        return users;
    }

    protected User constructUser(long id, String password, String email, String name) {
        var user = new User();
        user.setId(id);
        user.setStatus(User.Status.INITIAL);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setDevices(new HashSet<>());
        return user;
    }

    protected User constructUser() {
        return constructUser(1L, "1234", "user@gmail.com", "user");
    }

    protected ESim getESim() {
        ESim eSim = new ESim();
        eSim.setIccid("iccid");
        eSim.setId(1L);
        eSim.setActivationCode("123");
        eSim.setImsi("imsi");
        return eSim;
    }

    protected Device getDevice() {
        Device device = new Device();
        device.setMetaTag("metaTag");
        device.setOs("android");
        device.setType("Samsung");
        device.setId(1L);
        return device;
    }
}
