package com.example.tdstrialwork.services;

import com.example.tdstrialwork.data.entities.Device;
import com.example.tdstrialwork.data.entities.ESim;
import com.example.tdstrialwork.repository.DeviceRepository;
import com.example.tdstrialwork.repository.ESimRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest extends CommonService {

    @Mock
    private ESimRepository eSimRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void canAssignESimsToDevices() {
        Device device = getDevice();
        ESim eSim = getESim();

        Mockito.when(this.deviceRepository.findById(anyLong())).thenReturn(java.util.Optional.of(device));

        this.deviceService.assignESimsToDevices(device.getId(), List.of(eSim));

        Mockito.verify(this.eSimRepository, Mockito.times(1)).saveAll(List.of(eSim));
        Mockito.verify(this.deviceRepository, Mockito.times(1)).save(device);
    }

    @Test
    void canListDevicesEsims() {
        ESim eSim = getESim();
        Pageable pageable = PageRequest.of(0, 8);
        Page<ESim> page = new PageImpl<>(List.of(eSim));

        Mockito.when(this.eSimRepository.getESimsByDeviceId(anyLong(), isA(Pageable.class))).thenReturn(page);
        var res = this.deviceService.listDevicesEsims(1L, pageable);

        Assertions.assertEquals(1, res.getTotalElements());
        Assertions.assertEquals(page.getContent().get(0).getIccid(), res.getContent().get(0).getIccid());
        Assertions.assertEquals(page.getContent().get(0).getImsi(), res.getContent().get(0).getImsi());
    }

    @Test
    void canRemoveESimFromDevice() {
        Device device = getDevice();
        ESim eSim = getESim();

        Mockito.when(this.eSimRepository.findById(anyLong())).thenReturn(java.util.Optional.of(eSim));
        Mockito.when(this.deviceRepository.findById(anyLong())).thenReturn(java.util.Optional.of(device));

        this.deviceService.removeESimFromDevice(device.getId(), eSim.getId());

        Mockito.verify(this.eSimRepository, Mockito.times(1)).delete(eSim);
    }
}