package com.example.tdstrialwork.services;

import com.example.tdstrialwork.data.entities.Device;
import com.example.tdstrialwork.data.entities.User;
import com.example.tdstrialwork.helpers.Constants;
import com.example.tdstrialwork.repository.DeviceRepository;
import com.example.tdstrialwork.repository.UserRepository;
import com.example.tdstrialwork.services.support.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable paging, Specification<User> specification) {
        return this.userRepository.findAll(specification, paging);
    }

    @Transactional(readOnly = true)
    public User getUser(long userId) {
        return findUser(userId);
    }

    @Transactional
    public void createUser(User user) {
        /* To create new user */
        user.setId(null);
        user.setPassword(this.passwordEncoder.hash(user.getPassword()));
        user.setStatus(User.Status.INITIAL);
        var savedUser = this.userRepository.save(user);
        setDevices(savedUser);
    }

    @Transactional
    public void editUser(long userId, User user) {
        var existedUser = findUser(userId);
        setPassword(existedUser, user);
        user.setDevices(existedUser.getDevices());
        user.setId(userId);
        this.userRepository.save(user);
    }

    @Transactional
    public void removeUser(long userId) {
        if (this.userRepository.existsById(userId)) {
            this.userRepository.deleteById(userId);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ITEM_NOT_FOUND_MESSAGE);
        }
    }

    @Transactional
    public void assignDevicesToUser(long userId, List<Device> devices) {
        var user = findUser(userId);
        validateUserStatus(user);
        devices.forEach(user::addDevice);
        this.deviceRepository.saveAll(devices);
        this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<Device> listUsersDevices(Long userId, Pageable pageable) {
        return this.deviceRepository.getDevicesByUserId(userId, pageable);
    }

    @Transactional
    public void removeDeviceFromUser(Long userId, Long deviceId) {
        var device = this.deviceRepository.findById(deviceId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ITEM_NOT_FOUND_MESSAGE));
        var user = findUser(userId);
        validateUserStatus(user);
        user.removeDevice(device);
        this.deviceRepository.delete(device);
    }

    private void validateUserStatus(User user) {
        if (!user.getStatus().getId().equals("active"))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not active!");
        }
    }

    private void setPassword(User existedUser, User user) {
        if (user.getPassword() == null || user.getPassword().isBlank())
        {
            user.setPassword(existedUser.getPassword());
        }
        else
        {
            user.setPassword(this.passwordEncoder.hash(user.getPassword()));
        }
    }

    private void setDevices(User user) {
        if (user.getDevices() != null && !user.getDevices().isEmpty())
        {
            user.getDevices().forEach(user::addDevice);
            this.deviceRepository.saveAll(user.getDevices());
        }
    }

    private User findUser(long id)
    {
        return this.userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ITEM_NOT_FOUND_MESSAGE));
    }
}