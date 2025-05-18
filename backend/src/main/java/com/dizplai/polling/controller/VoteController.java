package com.dizplai.polling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dizplai.polling.dto.VoteCreationDTO;
import com.dizplai.polling.dto.VoteResponseDTO;
import com.dizplai.polling.service.VoteService;
import com.dizplai.polling.mapper.VoteMapper;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;


/**
 * Controller for managing votes
 */
@RestController
@RequestMapping("/votes")
public class VoteController {


    private final VoteService voteService;
    private final VoteMapper voteMapper;

    public VoteController(VoteService voteService, VoteMapper voteMapper) {
        this.voteService = voteService;
        this.voteMapper = voteMapper;
    }

    /**
     * Get all votes for all polls
     * @return List<VoteResponseDTO>
     */
    @GetMapping("/")
    public List<VoteResponseDTO> getAllVotes() {
        return voteMapper.toVoteResponses(voteService.getAllVotes());
    }

    /**
     * Get all votes for a poll
     * @param pollId ID of the poll to get votes for
     * @return List<VoteResponseDTO>
     */
    @GetMapping("/{pollId}")
    public List<VoteResponseDTO> getAllVotesForPoll(@PathVariable Long pollId) {
        return voteMapper.toVoteResponses(voteService.getAllVotesForPoll(pollId));
    }

    /** 
     * Get all votes for an option
     * @param optionId ID of the option to get votes for
     * @return List<VoteResponseDTO>
     */
    @GetMapping("/{optionId}")
    public List<VoteResponseDTO> getAllVotesForOption(@PathVariable Long optionId) {
        return voteMapper.toVoteResponses(voteService.getAllVotesForOption(optionId));
    }


    /**
     * Vote for the active poll
     * @param voteCreationDTO DTO for the vote to create
     */
    @PostMapping("/")
    public VoteResponseDTO vote(@RequestBody VoteCreationDTO voteCreationDTO) {
        return voteMapper.toVoteResponse(voteService.vote(voteCreationDTO));
    }
}
