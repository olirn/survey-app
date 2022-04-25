package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@AllArgsConstructor
@Component
public class DataHolder {


    // structure of "inside" Map -
    // key - questionName (another candidate - question id)
    // value - Question

    // key - userId, value - "inside" Map
    private Map<String, Map<String, Question>> userQuestions;
}
