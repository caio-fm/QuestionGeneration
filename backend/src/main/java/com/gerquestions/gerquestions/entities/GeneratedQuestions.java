package com.gerquestions.gerquestions.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GeneratedQuestions {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "generatedQuestions_questions", joinColumns = {
            @JoinColumn(name = "generatedQuestions_id")}, inverseJoinColumns = {
            @JoinColumn(name = "question_id")})
    private List<Question> questionList = new ArrayList<>();

    @Column(columnDefinition = "LONGTEXT")
    private String original_text;

    @ManyToOne
    @JoinColumn(name = "email")
    private AppUser appUser;

    public Set<Long> getQuestionsIds() {
        Set<Long> questionsIds = new HashSet<Long>();
        for (Question q : questionList) {
            questionsIds.add(q.getId());
        }
        return questionsIds;
    }

}
