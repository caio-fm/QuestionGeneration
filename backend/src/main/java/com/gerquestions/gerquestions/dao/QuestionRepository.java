package com.gerquestions.gerquestions.dao;

import com.gerquestions.gerquestions.entities.GeneratedQuestions;
import com.gerquestions.gerquestions.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface QuestionRepository<T, ID extends Serializable> extends JpaRepository<Question, Long> {
}
