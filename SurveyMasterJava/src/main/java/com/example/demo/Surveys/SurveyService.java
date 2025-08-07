package com.example.demo.Surveys;

import com.example.demo.EmailSender.EmailService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository,
                         QuestionRepository questionRepository,
                         AnswerOptionRepository answerOptionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
    }
    public List<Survey>getUnAcceptedSurveys(){
        return surveyRepository.findByIsAcceptedFalseAndIsFinishedFalse();

    }
    public List<Survey>getSurveysByCategory(String Category){
        return surveyRepository.findByCategory(Category);
    }
    public Survey markIsAccepted(Long surveyId){
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(()->new RuntimeException("survey not found"));

        survey.setAccepted(true);
        return surveyRepository.save(survey);
    }
    public Survey markIsFinished(Long surveyId){
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(()->new RuntimeException("survey not found"));

        survey.setFinished(true);
        return surveyRepository.save(survey);
    }


    public Optional<Survey> getSurvey(Long id) {
        return surveyRepository.findById(id);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findByIsAcceptedTrueAndIsFinishedFalse();
    }
     public List<Survey> getAllSurveysByUserId(Long userId){
        return  surveyRepository.findByUserId( userId);
     }
     public List<Survey>getAllFinishedSurveys(){
        return surveyRepository.findByIsAcceptedTrueAndIsFinishedTrue();
     }



    public Survey createCompleteSurvey(SurveyRequestDTO surveyRequest) {
        Survey survey = new Survey();
        survey.setTitle(surveyRequest.getTitle());
        survey.setDescription(surveyRequest.getDescription());
        survey.setAccepted(false);
        survey.setCategory(surveyRequest.getCategory());
        survey.setUserId(surveyRequest.getUserid());
        survey.setFinished(false);
        surveyRequest.getQuestions().forEach(qDto -> {
            Question question = new Question();
            question.setText(qDto.getText());
            question.setQuestionType(qDto.getQuestionType());

            if (qDto.getAnswerOptions() != null) {
                qDto.getAnswerOptions().forEach(aDto -> {
                    AnswerOption option = new AnswerOption(aDto.getText());
                    question.addAnswerOption(option);
                });
            }
            List <AnswerOption>options=question.getAnswerOptions();
            int size=options.size();
            question.addSize(size);
            survey.addQuestion(question);
        });

        return surveyRepository.save(survey);
    }
public void deleteSurvey(Long surveyId){
        if(!surveyRepository.existsById(surveyId))
            throw new RuntimeException(("survey not found with id " + surveyId));
    surveyRepository.deleteById(surveyId);
}
public void addVoter(Long surveyId,Long userId){
    Survey survey=surveyRepository.findById(surveyId)
            .orElseThrow(()-> new RuntimeException("survey not found"));
    if(surveyRepository.hasVoter(surveyId,userId)) {
        throw new RuntimeException("you already voted");
    }
    survey.addVoter(userId);
    surveyRepository.save(survey);

}
public Question setResult(Long questionId , int index,Long surveyId,Long userId){
    if(surveyRepository.hasVoter(surveyId,userId)) {
        throw new RuntimeException("you already voted");
    }

    Question question = questionRepository.findById(questionId)
            .orElseThrow(()-> new RuntimeException("question not found"));
    int [] updateResult= question.getQuestionResult();
    updateResult[index]++;
    question.setQuestionResult(updateResult);
     return questionRepository.save(question);
}
public Question setStringResult(Long questionId ,String result,Long surveyId,Long userId){
    if(surveyRepository.hasVoter(surveyId,userId)) {
        throw new RuntimeException("you already voted");
    }

Question question = questionRepository.findById(questionId)
            .orElseThrow(()-> new RuntimeException("question not found"));
question.addStringResult(result);
return questionRepository.save(question);
}
public Question setCheckBoxResult (Long questionId ,int[] index,Long surveyId,Long userId){
    if(surveyRepository.hasVoter(surveyId,userId)) {
        throw new RuntimeException("you already voted");
    }
Question question = questionRepository.findById(questionId)
            .orElseThrow(()-> new RuntimeException("question not found"));
    int [] updateResult= question.getQuestionResult();
    for (int i=0;i<index.length;i++){
        updateResult[index[i]]++;
    }
  question.setQuestionResult(updateResult);
    return questionRepository.save(question);

}
}
