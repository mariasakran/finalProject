package com.example.demo.Surveys;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByIsAcceptedTrueAndIsFinishedFalse();
    List<Survey> findByIsAcceptedTrueAndIsFinishedTrue();
    List<Survey> findByCategory(String category);
    List<Survey> findByIsAcceptedFalseAndIsFinishedFalse();
    List<Survey> findByUserId(Long userId);
    default boolean hasVoter(Long surveyId, Long voterId) {
        return findById(surveyId)
                .map(survey -> survey.getVoters().contains(voterId))
                .orElse(false);
    }

}

