package com.gerquestions.gerquestions.dao;

import com.gerquestions.gerquestions.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface AppUserRepository<T, ID extends Serializable> extends JpaRepository<AppUser, String> {
}
