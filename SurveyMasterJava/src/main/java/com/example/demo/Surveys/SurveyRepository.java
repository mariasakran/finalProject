package com.example.demo.Surveys;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByIsAcceptedTrueAndIsFinishedFalse();
    List<Survey> findByIsAcceptedFalseAndIsFinishedFalse();
    List<Survey> findByUserId(Long userId);

}

