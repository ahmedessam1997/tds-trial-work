package com.example.tdstrialwork.repository;

import com.example.tdstrialwork.data.entities.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(
            value = "SELECT d.* FROM devices d where d.user_id = :userId",
            countQuery = "SELECT count(d.*) from devices d where d.user_id = :userId",
            nativeQuery = true
    )
    Page<Device> getDevicesByUserId(@Param("userId") long userId, Pageable pageable);
}
