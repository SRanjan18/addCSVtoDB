package com.example.demo.repository;
import com.example.demo.entity.SourceMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SourceMasterRepository extends JpaRepository<SourceMaster, String> {

}
