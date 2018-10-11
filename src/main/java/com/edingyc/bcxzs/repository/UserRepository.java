package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByOpenid(String openId);

}
