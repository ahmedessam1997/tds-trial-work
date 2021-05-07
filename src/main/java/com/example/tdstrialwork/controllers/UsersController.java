package com.example.tdstrialwork.controllers;

import com.example.tdstrialwork.helpers.Constants;
import com.example.tdstrialwork.mappers.DeviceMapper;
import com.example.tdstrialwork.mappers.UserMapper;
import com.example.tdstrialwork.services.UserService;
import io.reflectoring.api.UsersApi;
import io.reflectoring.model.AbstractDeviceDetails;
import io.reflectoring.model.AbstractUserDetails;
import io.reflectoring.model.CreateUserDetails;
import io.reflectoring.model.UpsertUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController extends CommonController implements UsersApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public ResponseEntity<List<AbstractUserDetails>> getUsers(Integer offset, Integer limit,
                                                               String sort, String filter) {
        var page = this.userService.getUsers(paging(offset, limit, sortBy(sort)), specificationFrom(filter));
        return ResponseEntity
                .ok()
                .header(Constants.TOTAL_COUNT_HEADER, String.valueOf(page.getTotalElements()))
                .body(this.userMapper.map(page.getContent()));
    }

    @Override
    public ResponseEntity<AbstractUserDetails> getUser(Long userId) {
        var user = this.userService.getUser(userId);
        return ResponseEntity.ok(this.userMapper.map(user));
    }

    @Override
    public ResponseEntity<Void> createUser(CreateUserDetails createUserDetails) {
        this.userService.createUser(this.userMapper.map(createUserDetails));
        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> editUser(Long userId, UpsertUserDetails upsertUserDetails) {
        this.userService.editUser(userId, this.userMapper.map(upsertUserDetails));
        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> removeUser(Long userId) {
        this.userService.removeUser(userId);
        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> assignDevicesToUser(Long userId, List<AbstractDeviceDetails> abstractDeviceDetails) {
        this.userService.assignDevicesToUser(userId, this.deviceMapper.mapToDevice(abstractDeviceDetails));
        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<List<AbstractDeviceDetails>> listUsersDevices(Long userId, Integer offset, Integer limit) {
        var page = this.userService.listUsersDevices(userId, paging(offset, limit));
        return ResponseEntity
                .ok()
                .header(Constants.TOTAL_COUNT_HEADER, String.valueOf(page.getTotalElements()))
                .body(this.deviceMapper.map(page.getContent()));

    }

    @Override
    public ResponseEntity<Void> removeDeviceFromUser(Long userId, Long deviceId) {
        this.userService.removeDeviceFromUser(userId, deviceId);
        return ResponseEntity.ok()
                .build();
    }
}