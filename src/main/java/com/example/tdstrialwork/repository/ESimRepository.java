package com.example.tdstrialwork.repository;

import com.example.tdstrialwork.data.entities.ESim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ESimRepository extends JpaRepository<ESim, Long> {
    @Query(
            value = "SELECT e.* FROM e_sims e where e.device_id = :deviceId",
            countQuery = "SELECT count(e.*) from e_sims e where e.device_id = :deviceId",
            nativeQuery = true
    )
    Page<ESim> getESimsByDeviceId(@Param("deviceId") long deviceId, Pageable pageable);
}
