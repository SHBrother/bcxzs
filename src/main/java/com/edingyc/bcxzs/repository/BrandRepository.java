package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository    extends JpaRepository<BrandEntity, String> {

    List<BrandEntity> findByBrand(String brand);

}
