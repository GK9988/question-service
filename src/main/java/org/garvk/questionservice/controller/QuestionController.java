package org.garvk.questionservice.controller;

import jakarta.websocket.server.PathParam;
import org.garvk.questionservice.model.Question;
import org.garvk.questionservice.model.UserResponseDto;
import org.garvk.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuestions(){
        try{
            List<Question> aOutQuestions = questionService.getAllStudents();
            return new ResponseEntity<>(aOutQuestions, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{qCategory}")
    public ResponseEntity<?> getQuestionsByCategory(@PathVariable String qCategory){
        try{
            List<Question> aOutQuestions = questionService.getQuestionsByCategory(qCategory);
            return new ResponseEntity<>(aOutQuestions, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Question aInQuestion){
        try{
            Question aOutQuestion = questionService.addQuestion(aInQuestion);
            return new ResponseEntity<>(aOutQuestion, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuestion(@RequestBody Question aInQuestion){
        try{
            Question aOutQuestion = questionService.addQuestion(aInQuestion);
            return new ResponseEntity<>(aOutQuestion, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer id){
        try{
            questionService.deleteQuestion(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<?> getQuestionsForQuiz(@RequestParam("category") String aInCategory, @RequestParam("noOfQuestions") Integer aInNoOfQuestions){
        return questionService.getQuestionsForQuiz(aInCategory, aInNoOfQuestions);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<?> getQuestionsByIds(@RequestBody List<Integer> aInIds){
        return questionService.getQuestionsByIds(aInIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<?> getScore(@RequestBody List<UserResponseDto> aInUserResponses){
        return questionService.getScore(aInUserResponses);
    }

}
