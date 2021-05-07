package com.example.tdstrialwork.mappers;

import com.example.tdstrialwork.data.entities.Device;
import io.reflectoring.model.AbstractDeviceDetails;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    List<Device> mapToDevice(List<AbstractDeviceDetails> abstractDeviceDetailsList);

    List<AbstractDeviceDetails> map(List<Device> devices);

    UUID map(String value);

    default String map(UUID value) {
        return value.toString();
    }
}