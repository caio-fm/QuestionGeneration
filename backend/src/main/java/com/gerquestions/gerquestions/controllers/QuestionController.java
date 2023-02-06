package com.gerquestions.gerquestions.controllers;

import com.gerquestions.gerquestions.dto.GeneratedQuestionsDTO;
import com.gerquestions.gerquestions.entities.Question;
import com.gerquestions.gerquestions.exceptions.UserException;
import com.gerquestions.gerquestions.services.LoginService;
import com.gerquestions.gerquestions.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private LoginService loginService;
    private QuestionService questionService;

    public QuestionController(QuestionService questionService, LoginService loginService) {
        super();
        this.questionService = questionService;
        this.loginService = loginService;
    }


    @PostMapping(value = "/genquestions", consumes = "application/json")
    public ResponseEntity<?> genQuestions(
            @RequestHeader("Authorization") String header,
            @Valid @RequestBody String text
    ) throws UserException {

        loginService.userExists(header);
        String email = loginService.getUserDoToken(header);

        return new ResponseEntity<>(questionService.genQuestions(text, email), HttpStatus.OK);
    }

    @PutMapping(value = "/savequestions", consumes = "application/json")
    public ResponseEntity<?> saveQuestions(
            @RequestHeader("Authorization") String header,
            @Valid @RequestBody List<Long> questions
    ) throws UserException {

        loginService.userExists(header);
        String email = loginService.getUserDoToken(header);

        return new ResponseEntity<>(questionService.saveQuestions(questions, email), HttpStatus.OK);
    }


    @GetMapping(value = "/questions", consumes = "application/json")
    public ResponseEntity<?> getQuestions() {

        List<GeneratedQuestionsDTO> questionsDTO = questionService.getQuestions();

        return new ResponseEntity<List<GeneratedQuestionsDTO>>(questionsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/userquestions", consumes = "application/json")
    public ResponseEntity<?> getUserQuestions(@RequestHeader("Authorization") String header) throws UserException {

        loginService.userExists(header);
        String email = loginService.getUserDoToken(header);
        List<GeneratedQuestionsDTO> questionsDTO = questionService.getUserQuestions(email);

        return new ResponseEntity<List<GeneratedQuestionsDTO>>(questionsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/editquestion/{id}", consumes = "application/json")
    public ResponseEntity<?> editQuestion(@RequestHeader("Authorization") String header,
                                          @PathVariable Long id,
                                          @Valid @RequestBody String newText) throws UserException {

        loginService.userExists(header);
        String email = loginService.getUserDoToken(header);
        Question questionsDTO = questionService.editQuestion(email, id, newText);

        return new ResponseEntity<Question>(questionsDTO, HttpStatus.OK);
    }

}
