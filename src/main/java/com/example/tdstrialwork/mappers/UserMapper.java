package com.example.tdstrialwork.mappers;

import com.example.tdstrialwork.data.entities.User;
import io.reflectoring.model.AbstractUserDetails;
import io.reflectoring.model.CreateUserDetails;
import io.reflectoring.model.UpsertUserDetails;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    List<AbstractUserDetails> map(List<User> user);

    AbstractUserDetails map(User user);

    User map(CreateUserDetails createUserDetails);

    User map(UpsertUserDetails upsertUserDetails);

    UUID map(String value);
}
