package com.example.tdstrialwork.services;

import com.example.tdstrialwork.data.entities.Device;
import com.example.tdstrialwork.data.entities.User;
import com.example.tdstrialwork.repository.DeviceRepository;
import com.example.tdstrialwork.repository.UserRepository;
import com.example.tdstrialwork.services.support.PasswordEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends CommonService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void canGetUsers() {
        var users = constructUsers();
        Page<User> page = new PageImpl<>(users);
        Pageable pageable = PageRequest.of(0, 8);

        Mockito.when(this.userRepository.findAll(any(Specification.class), isA(Pageable.class))).thenReturn(page);

        var res = this.userService.getUsers(pageable, Specification.where(null));

        Assertions.assertEquals(2, res.getTotalElements());
        Assertions.assertEquals("user1", res.getContent().get(0).getName());
        Assertions.assertEquals("user1@gmail.com", res.getContent().get(0).getEmail());
    }

    @Test
    void canGetUser() {
        var user = constructUser();
        Mockito.when(this.userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        var res = this.userService.getUser(user.getId());
        Assertions.assertNotNull(res);
        Assertions.assertEquals(user.getId(), res.getId());
        Assertions.assertEquals(user.getEmail(), res.getEmail());
    }

    @Test
    void canCreateUser() {
        var user = constructUser();
        Mockito.when(this.userRepository.save(any(User.class))).thenReturn(user);
        this.userService.createUser(user);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void canAssignDevicesToUser() {
        var device = getDevice();
        var user = constructUser();
        user.setDevices(new HashSet<>());
        Mockito.when(this.userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        this.userService.assignDevicesToUser(user.getId(), List.of(device));

        Mockito.verify(this.deviceRepository, Mockito.times(1)).saveAll(List.of(device));
        Mockito.verify(this.userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void listUsersDevices() {
        var device = getDevice();
        Pageable pageable = PageRequest.of(0, 8);
        Page<Device> page = new PageImpl<>(List.of(device));

        Mockito.when(this.deviceRepository.getDevicesByUserId(anyLong(), isA(Pageable.class))).thenReturn(page);
        var res = this.userService.listUsersDevices(1L, pageable);

        Assertions.assertEquals(1, res.getTotalElements());
        Assertions.assertEquals(page.getContent().get(0).getDeviceUniqueId(), res.getContent().get(0).getDeviceUniqueId());
        Assertions.assertEquals(page.getContent().get(0).getMetaTag(), res.getContent().get(0).getMetaTag());
        Assertions.assertEquals(page.getContent().get(0).getOs(), res.getContent().get(0).getOs());
    }
}