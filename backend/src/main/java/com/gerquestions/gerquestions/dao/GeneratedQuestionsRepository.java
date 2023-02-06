package com.gerquestions.gerquestions.dao;

import com.gerquestions.gerquestions.entities.GeneratedQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface GeneratedQuestionsRepository<T, ID extends Serializable> extends JpaRepository<GeneratedQuestions, Long> {
}
