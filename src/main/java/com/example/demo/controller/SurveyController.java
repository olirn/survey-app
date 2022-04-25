package com.example.demo.controller;

import com.example.demo.dto.Distribution;
import com.example.demo.dto.Question;
import com.example.demo.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Collection;

@RestController
@RequestMapping("/survey/")
@RequiredArgsConstructor
public class SurveyController {

    private final QuestionService questionService;

    @GetMapping("questions-all/{userId}")
//    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Returns All Questions.")
    public Collection<Question> getAllQuestions(@PathVariable @NotEmpty String userId) {

        return questionService.getAllQuestions(userId);
    }

    @GetMapping("question/{userId}/{questionName}")
//    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Get one Question by name.")
    public Question getQuestion(@PathVariable @NotEmpty String userId, @PathVariable @NotEmpty String questionName) {

        return questionService.getQuestion(userId, questionName);
    }

    @PutMapping("updateAttributes/{userId}/{questionName}/{answerId}")
    @ApiOperation("Mark Answer For Question.")
    public void markAnswerForQuestion(@PathVariable @NotEmpty String userId,
                                      @PathVariable String questionName, @PathVariable String answerId) {

        questionService.markAnswerForQuestion(userId, questionName, answerId);
    }

    @PostMapping("add-question/{userId}/{questionName}/{questionBody}")
    @ApiOperation("Add Question.")
    public void addQuestion(@PathVariable @NotEmpty String userId, @PathVariable String questionName,
                            @PathVariable String questionBody) {

        questionService.addQuestion(userId, questionName, questionBody);
    }

    @PostMapping("add-answer/{userId}/{questionName}/{answerId}/{answerBody}")
    @ApiOperation("Add Answer to existed Question.")
    public void addAnswer(@PathVariable @NotEmpty String userId, @PathVariable String questionName,
                          @PathVariable String answerId,
                          @PathVariable String answerBody) {

        questionService.addAnswer(userId, questionName, answerId, answerBody); // String questionName, String answerId, String answerBody
    }

    @GetMapping("relativeDistributionByQuestion/{questionName}")
    @ApiOperation("Get Relative Distribution By Question Name.")
    public Collection<Distribution> getRelativeDistributionByQuestion(@PathVariable @NotEmpty String questionName) {

        return questionService.getRelativeDistributionByQuestion(questionName);
    }

}
