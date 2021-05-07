package com.example.tdstrialwork.controllers;

import com.example.tdstrialwork.helpers.Constants;
import com.example.tdstrialwork.mappers.DeviceMapper;
import com.example.tdstrialwork.mappers.ESimMapper;
import com.example.tdstrialwork.services.DeviceService;
import io.reflectoring.api.DevicesApi;
import io.reflectoring.model.ESimDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class DeviceController extends CommonController implements DevicesApi {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ESimMapper eSimMapper;

    @Override
    public ResponseEntity<Void> assignESimsToDevices(Long deviceId, List<ESimDetails> esimDetails) {
        this.deviceService.assignESimsToDevices(deviceId, this.eSimMapper.map(esimDetails));
        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<List<ESimDetails>> listDevicesEsims(Long deviceId, Integer offset,
                                                              Integer limit) {
        var page = this.deviceService.listDevicesEsims(deviceId, paging(offset, limit));
        return ResponseEntity
                .ok()
                .header(Constants.TOTAL_COUNT_HEADER, String.valueOf(page.getTotalElements()))
                .body(this.eSimMapper.mapToESimDetails(page.getContent()));
    }

    @Override
    public ResponseEntity<Void> removeESimFromDevice(Long deviceId, Long eSimId) {
        this.deviceService.removeESimFromDevice(deviceId, eSimId);
        return ResponseEntity.ok()
                .build();
    }
}
