package com.example.springsecurityazure.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ChoiceVoteCount {
    private Long choiceId;
    private Long voteCount;
}
