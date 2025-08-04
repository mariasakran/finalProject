package com.example.demo.Surveys;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String category;
    @Column(nullable = false)
    private String title;
    @Column
    private String description;
    @Column
    private boolean isAccepted;
    @Column
    private boolean isFinished;
    @Column
    private Long userId;
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();
    @Column
    private List<Long> voters=new ArrayList<>();
    public Survey() {}

    public Survey(String category, String title, String description, Long userId) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.isFinished=false;
        this.isAccepted=false;
    }

    // Helper method to add question
    public void addQuestion(Question question) {
        questions.add(question);
        question.setSurvey(this);
    }
    public void addVoter(Long voterId){
        voters.add(voterId);
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        if (questions != null) {
            questions.forEach(q -> q.setSurvey(this));
}
}

    public List<Long> getVoters() {
        return voters;
    }

    public void setVoters(List<Long> voters) {
        this.voters = voters;
    }
}