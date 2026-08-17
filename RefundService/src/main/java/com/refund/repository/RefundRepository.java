package com.refund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refund.entity.RefundEntity;

@Repository
public interface RefundRepository extends JpaRepository<RefundEntity, Integer>{

}
