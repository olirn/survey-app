package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Answer {

    private String id;

    private String answerBody;

    // assumption - it will be possible to have not several answers to one question, but only one selected.
    private Boolean selected;
}
