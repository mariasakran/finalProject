package com.example.demo.Surveys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "type", nullable=false)
    private String questionType; // e.g., "text", "multiple-choice", "rating"
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

     @Column(name = "questionResult")
     private int[] questionResult = new int[0];
     @Column(name = "questionStringResult")
    private List <String> questionStringResult = new ArrayList<>() ;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> answerOptions = new ArrayList<>();

    // Helper method to add answer option
    public void addAnswerOption(AnswerOption option) {
        answerOptions.add(option);
        option.setQuestion(this);
}

    public Question() {}

    public Question(String text, String questionType) {
        this.text = text;
        this.questionType = questionType;
    }


    public int[] getQuestionResult() {
        return questionResult;
    }

    public void setQuestionResult(int[] questionResult) {
        this.questionResult = questionResult;
    }

    public List<String> getQuestionStringResult() {
        return questionStringResult;
    }

    public void setQuestionStringResult(List<String> questionStringResult) {
        this.questionStringResult = questionStringResult;
    }
    public void addStringResult (String answer){
     questionStringResult.add(answer);

    }
    public void addSize (int size){
        questionResult=new int[size];

        }



    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey =survey;
}



    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
        if (answerOptions != null) {
            answerOptions.forEach(a -> a.setQuestion(this));
}
}
}


