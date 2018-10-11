package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.PicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PicRepository   extends JpaRepository<PicEntity, String> {

    List<PicEntity> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);
}
