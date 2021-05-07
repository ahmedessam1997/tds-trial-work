package com.example.tdstrialwork.services;

import com.example.tdstrialwork.data.entities.ESim;
import com.example.tdstrialwork.helpers.Constants;
import com.example.tdstrialwork.repository.DeviceRepository;
import com.example.tdstrialwork.repository.ESimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ESimRepository eSimRepository;

    @Transactional
    public void assignESimsToDevices(Long deviceId, List<ESim> eSims) {
        var device = this.deviceRepository.findById(deviceId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ITEM_NOT_FOUND_MESSAGE));

        eSims.forEach(device::addESim);
        this.eSimRepository.saveAll(eSims);
        this.deviceRepository.save(device);
    }

    @Transactional(readOnly = true)
    public Page<ESim> listDevicesEsims(Long deviceId, Pageable paging) {
        return this.eSimRepository.getESimsByDeviceId(deviceId, paging);
    }

    public void removeESimFromDevice(Long deviceId, Long eSimId) {
        var eSim = this.eSimRepository.findById(eSimId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ITEM_NOT_FOUND_MESSAGE));
        var device = this.deviceRepository.findById(deviceId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ITEM_NOT_FOUND_MESSAGE));
        device.removeESim(eSim);
        this.eSimRepository.delete(eSim);
    }
}
