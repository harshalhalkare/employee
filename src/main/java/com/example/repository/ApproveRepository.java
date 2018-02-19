package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.domain.ApproveBucket;

public interface ApproveRepository extends JpaRepository<ApproveBucket, Long>{

}
