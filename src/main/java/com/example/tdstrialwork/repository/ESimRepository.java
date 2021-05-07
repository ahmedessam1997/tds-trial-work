package com.example.tdstrialwork.repository;

import com.example.tdstrialwork.data.entities.ESim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ESimRepository extends JpaRepository<ESim, Long>, JpaSpecificationExecutor {
}
