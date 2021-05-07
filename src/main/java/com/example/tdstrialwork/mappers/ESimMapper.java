package com.example.tdstrialwork.mappers;

import com.example.tdstrialwork.data.entities.ESim;
import io.reflectoring.model.ESimDetails;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ESimMapper {

    List<ESim> map(List<ESimDetails> eSimDetails);

    List<ESimDetails> mapToESimDetails(List<ESim> eSims);

    UUID map(String value);

    default String map(UUID value) {
        return value.toString();
    }
}
