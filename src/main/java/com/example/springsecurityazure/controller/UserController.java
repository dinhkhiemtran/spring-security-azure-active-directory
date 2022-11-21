package com.example.springsecurityazure.controller;

import com.example.springsecurityazure.exception.ResourceNotFoundException;
import com.example.springsecurityazure.model.User;
import com.example.springsecurityazure.payload.response.*;
import com.example.springsecurityazure.repo.PollRepository;
import com.example.springsecurityazure.repo.UserRepository;
import com.example.springsecurityazure.repo.VoteRepository;
import com.example.springsecurityazure.security.CurrentUser;
import com.example.springsecurityazure.security.UserPrincipal;
import com.example.springsecurityazure.service.PollService;
import com.example.springsecurityazure.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checknameAvailability")
    public UserIdentityAvailability checknameAvailability(@RequestParam(value = "name") String name) {
        Boolean isAvailable = !userRepository.existsByName(name);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{name}")
    public UserProfile getUserProfile(@PathVariable(value = "name") String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User", "name", name));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }

    @GetMapping("/users/{name}/polls")
    public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "name") String name,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(name, currentUser, page, size);
    }


    @GetMapping("/users/{name}/votes")
    public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "name") String name,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(name, currentUser, page, size);
    }
}

