package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Question {

    private String name;

    private String questionBody;

    private Set<Answer> answers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return name.equals(question.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
