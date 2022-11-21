package com.example.springsecurityazure.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {
    private Long id;
    private String name;
    private Instant joinedAt;
    private Long pollCount;
    private Long voteCount;
}
