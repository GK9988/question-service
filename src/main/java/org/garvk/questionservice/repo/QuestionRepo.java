package org.garvk.questionservice.repo;

import org.garvk.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findAllByCategory(String aInQCategory);

    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :aInNoOfQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsInLimit(String category, Integer aInNoOfQuestions);
}
