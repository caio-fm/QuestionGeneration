package com.gerquestions.gerquestions.services;

import com.gerquestions.gerquestions.dao.AppUserRepository;
import com.gerquestions.gerquestions.dao.GeneratedQuestionsRepository;
import com.gerquestions.gerquestions.dao.QuestionRepository;
import com.gerquestions.gerquestions.dto.GeneratedQuestionsDTO;
import com.gerquestions.gerquestions.entities.AppUser;
import com.gerquestions.gerquestions.entities.GeneratedQuestions;
import com.gerquestions.gerquestions.entities.Question;
import com.gerquestions.gerquestions.misc.RandomText;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {

    private GeneratedQuestionsRepository<GeneratedQuestions, Long> generatedQuestionsRepository;

    private QuestionRepository<Question, Long> questionRepository;
    private AppUserRepository<AppUser, String> appUserRepository;

    public QuestionService(
            GeneratedQuestionsRepository<GeneratedQuestions, Long> generatedQuestionsRepository,
            QuestionRepository<Question, Long> questionRepository,
            AppUserRepository<AppUser, String> appUserRepository
    ) {
        super();
        this.generatedQuestionsRepository = generatedQuestionsRepository;
        this.questionRepository = questionRepository;
        this.appUserRepository = appUserRepository;
    }

    public GeneratedQuestionsDTO genQuestions(String text, String email) {
        AppUser user = appUserRepository.findById(email).get();
        RandomText lorem = new RandomText();
        int numberQuestGenerated = 3;
        List<Question> questions = new ArrayList<>();
        GeneratedQuestions generatedQuestions = new GeneratedQuestions();

        for (int i = 0; i < numberQuestGenerated; i++) {
            Question question = new Question();
            question.setText(lorem.getText(10)); //MODELO
            question.setEditedText(question.getText());
            questionRepository.save(question);
            questions.add(question);

        }


        generatedQuestions.setQuestionList(questions);
        generatedQuestions.setOriginal_text(text);
        generatedQuestions.setAppUser(user);
        generatedQuestionsRepository.save(generatedQuestions);

        GeneratedQuestionsDTO generatedQuestionsDTO = new GeneratedQuestionsDTO();
        generatedQuestionsDTO.setOriginal_text(generatedQuestions.getOriginal_text());
        generatedQuestionsDTO.setId(generatedQuestions.getId());
        generatedQuestionsDTO.setAppUserEmail(generatedQuestions.getAppUser().getEmail());
        generatedQuestionsDTO.setQuestionList(generatedQuestions.getQuestionList());

        return generatedQuestionsDTO;

    }


    public GeneratedQuestionsDTO saveQuestions(List<Long> questions, String email) {
        GeneratedQuestions generatedQuestions = generatedQuestionsRepository.findById(questions.get(0)).get();
        GeneratedQuestionsDTO generatedQuestionsDTO = new GeneratedQuestionsDTO();
        generatedQuestionsDTO.setOriginal_text(generatedQuestions.getOriginal_text());
        generatedQuestionsDTO.setId(generatedQuestions.getId());
        generatedQuestionsDTO.setAppUserEmail(generatedQuestions.getAppUser().getEmail());

        questions.remove(0);
        AppUser user = appUserRepository.findById(email).get();
        Set<Long> questionIds = generatedQuestions.getQuestionsIds();
        questionIds.retainAll(questions);

        for (Question q : generatedQuestions.getQuestionList()) {
            if (questionIds.contains(q.getId())) q.setSaved(true);
            else q.setSaved(false);
            questionRepository.save(q);
        }

        generatedQuestionsDTO.setQuestionList(generatedQuestions.getQuestionList());

        return generatedQuestionsDTO;
    }

    public List<GeneratedQuestionsDTO> getQuestions() {
        List<GeneratedQuestions> generatedQuestions = generatedQuestionsRepository.findAll();
        List<GeneratedQuestionsDTO> questionsDTO = new ArrayList<>();
        for (int i = 0; i < generatedQuestions.size(); i++) {
            GeneratedQuestions generatedQuestion = generatedQuestions.get(i);
            GeneratedQuestionsDTO generatedQuestionsDTO = new GeneratedQuestionsDTO(generatedQuestion.getId(), generatedQuestion.getQuestionList(), generatedQuestion.getOriginal_text(), generatedQuestion.getAppUser().getEmail());
            questionsDTO.add(generatedQuestionsDTO);
        }


        return questionsDTO;
    }


    public List<GeneratedQuestionsDTO> getUserQuestions(String email) {
        AppUser user = appUserRepository.findById(email).get();
        List<GeneratedQuestions> generatedQuestions = user.getGeneratedQuestions();
        List<GeneratedQuestionsDTO> questionsDTO = new ArrayList<>();
        for (GeneratedQuestions gq : generatedQuestions) {
            GeneratedQuestions generatedQuestion = gq;
            List<Question> savedQuestionsOnly = new ArrayList<>();
            for (Question q : generatedQuestion.getQuestionList()) {
                if (q.getSaved() == null || q.getSaved()) savedQuestionsOnly.add(q);
            }
            GeneratedQuestionsDTO generatedQuestionsDTO = new GeneratedQuestionsDTO(generatedQuestion.getId(), savedQuestionsOnly, generatedQuestion.getOriginal_text(), generatedQuestion.getAppUser().getEmail());
            questionsDTO.add(generatedQuestionsDTO);
        }


        return questionsDTO;
    }

    public Question editQuestion(String email, Long id, String newText) {
        AppUser user = appUserRepository.findById(email).get();
        Question q = questionRepository.findById(id).get();
        q.setEditedText(newText);
        questionRepository.save(q);
        return q;
    }
}
