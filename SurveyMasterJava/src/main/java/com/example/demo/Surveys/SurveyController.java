package com.example.demo.Surveys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurvey(@PathVariable Long id) {
        return surveyService.getSurvey(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Survey> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<List<Survey>>findByUserId(@PathVariable Long userId){
        List<Survey> surveys = surveyService.getAllSurveysByUserId(userId);
        return ResponseEntity.ok(surveys);
    }
    @GetMapping("/getUnAcceptedSurvey")
    public ResponseEntity<List<Survey>>getUnAcceptedSurvey(){
        List<Survey> surveys = surveyService.getUnAcceptedSurveys();
        return ResponseEntity.ok(surveys);
    }

    

    @PostMapping("/{surveyId}/questions")
    public ResponseEntity<Survey> addQuestionToSurvey(
            @PathVariable Long surveyId,
            @RequestBody Question question) {
        Survey updatedSurvey = surveyService.addQuestionToSurvey(surveyId, question);
        return ResponseEntity.ok(updatedSurvey);
    }


    @PostMapping
    public ResponseEntity<Survey> createCompleteSurvey(@RequestBody SurveyRequestDTO surveyRequest) {
        Survey createdSurvey = surveyService.createCompleteSurvey(surveyRequest);
        return ResponseEntity.ok(createdSurvey);
}

    @PutMapping("/isAccepted/{surveyId}")
    public ResponseEntity<Survey>markIsAccepted(@PathVariable Long surveyId){
    Survey survey = surveyService.markIsAccepted(surveyId);
    return ResponseEntity.ok(survey);

    }
    @PutMapping("/isFinished/{surveyId}")
    public ResponseEntity<Survey>markIsFinished(@PathVariable Long surveyId){
        Survey survey = surveyService.markIsFinished(surveyId);
        return ResponseEntity.ok(survey);


    }
    @DeleteMapping("/{surveyId}")
    public ResponseEntity<Survey> deleteSurvey(@PathVariable Long surveyId){
        surveyService.deleteSurvey(surveyId);
        return ResponseEntity.ok().build();
    }
  @PutMapping("/radioResult/{questionId}/{index}/{surveyId}/{userId}")
    public  ResponseEntity<Question>addRadioResult (@PathVariable Long questionId,
                                                    @PathVariable int index,
                                                    @PathVariable Long surveyId,
                                                    @PathVariable Long userId ){
        Question question = surveyService.setResult(questionId,index,surveyId,userId);
        return ResponseEntity.ok(question);
  }
  @PutMapping("/textResult/{questionId}/{surveyId}/{userId}")
    public ResponseEntity<Question>addStringResult(@PathVariable Long questionId,
                                                   @RequestBody String answer,
                                                   @PathVariable Long surveyId,
                                                   @PathVariable Long userId){
       Question question= surveyService.setStringResult(questionId,answer,surveyId,userId);
      return ResponseEntity.ok(question);
  }
  @PutMapping("/checkBoxResult/{questionId}/{surveyId}/{userId}")
    public ResponseEntity<Question>addChickBoxResult(@PathVariable Long questionId,
                                                     @RequestBody int[] indexes,
                                                     @PathVariable Long surveyId,
                                                     @PathVariable Long userId){

Question question = surveyService.setCheckBoxResult(questionId,indexes,surveyId,userId);
      return ResponseEntity.ok(question);

  }
  @PutMapping("/addVoter/{surveyId}/{userId}")
    public ResponseEntity<Survey>addVoter( @PathVariable Long surveyId,
                                           @PathVariable Long userId ){
        surveyService.addVoter(surveyId,userId);
        return ResponseEntity.ok().build();
  }

}