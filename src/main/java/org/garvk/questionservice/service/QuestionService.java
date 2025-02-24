package org.garvk.questionservice.service;

import org.garvk.questionservice.model.Question;
import org.garvk.questionservice.model.QuestionWrapperDto;
import org.garvk.questionservice.model.UserResponseDto;
import org.garvk.questionservice.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public QuestionWrapperDto questionToQuestionWrapperDto(Question aInQuestion){
        QuestionWrapperDto aOutDto = new QuestionWrapperDto();
        aOutDto.setId(aInQuestion.getId());
        aOutDto.setQuestionTitle(aInQuestion.getQuestionTitle());
        aOutDto.setOption1(aInQuestion.getOption1());
        aOutDto.setOption2(aInQuestion.getOption2());
        aOutDto.setOption3(aInQuestion.getOption3());
        aOutDto.setOption4(aInQuestion.getOption4());
        return aOutDto;
    }

    public List<Question> getAllStudents(){
        return questionRepo.findAll();
    }

    public List<Question> getQuestionsByCategory(String aInQCategory) {
        return questionRepo.findAllByCategory(aInQCategory);
    }

    public Question addQuestion(Question aInQuestion) {
        return questionRepo.save(aInQuestion);
    }

    public void deleteQuestion(Integer id) {
        Question lQuestion = questionRepo.findById(id).orElse(null);
        if(null == lQuestion){
            throw new IllegalArgumentException("No Question with id " + id);
        }
        questionRepo.delete(lQuestion);
    }

    public ResponseEntity<?> getQuestionsForQuiz(String aInCategory, Integer aInNoOfQuestions) {
        try{
            List<Integer> aOutQuestions = questionRepo.findRandomQuestionsInLimit(aInCategory, aInNoOfQuestions);
            return new ResponseEntity<>(aOutQuestions, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getQuestionsByIds(List<Integer> aInIds) {
        List<QuestionWrapperDto> aOutQuestionWrapperDto = new ArrayList<>();
        try{
            List<Question> lQuestions = questionRepo.findAllById(aInIds);

            aOutQuestionWrapperDto = lQuestions.stream()
                    .map(this::questionToQuestionWrapperDto)
                    .toList();

            return new ResponseEntity<>(aOutQuestionWrapperDto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getScore(List<UserResponseDto> aInUserResponses) {
        int total = 0;

        try{
            for(UserResponseDto resp: aInUserResponses){
                Question lQuestion = questionRepo.findById(resp.getqId()).orElse(null);

                if(null == lQuestion){
                    throw new IllegalArgumentException("No Question with Id " + resp.getqId());
                }

                if(resp.getqAnswer().equals(lQuestion.getRightAnswer())){
                    total++;
                }

            }
            return new ResponseEntity<>(total, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
