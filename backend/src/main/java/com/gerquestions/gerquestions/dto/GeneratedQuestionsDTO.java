package com.gerquestions.gerquestions.dto;

import com.gerquestions.gerquestions.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedQuestionsDTO {
    private Long id;

    List<Question> questionList;

    private String original_text;

    private String appUserEmail;
}
