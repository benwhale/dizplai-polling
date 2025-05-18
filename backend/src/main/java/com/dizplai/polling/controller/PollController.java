package com.dizplai.polling.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dizplai.polling.model.Poll;
import com.dizplai.polling.service.PollService;
import com.dizplai.polling.mapper.PollMapper;
import com.dizplai.polling.dto.PollCreationDTO;
import com.dizplai.polling.dto.PollResponseDTO;


import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/polls")
public class PollController {
    
    private final PollService pollService;
    private final PollMapper pollMapper;


    public PollController(PollService pollService, PollMapper pollMapper) {
        this.pollService = pollService;
        this.pollMapper = pollMapper;
    }

    /**
     * Create a new poll
     * @param pollCreationDTO DTO for the poll to create
     * @return PollResponseDTO
     */
    @PostMapping("/")
    public PollResponseDTO createPoll(@RequestBody PollCreationDTO pollCreationDTO) {
        Poll pollToCreate = pollMapper.toPoll(pollCreationDTO);
        Poll createdPoll = pollService.createPoll(pollToCreate);
        return pollMapper.toPollResponse(createdPoll);   
    }

    /**
     * Get all polls
     * @return List<PollResponseDTO>
     */
    @GetMapping("/")
    public List<PollResponseDTO> getAllPolls() {
        return pollMapper.toPollResponses(pollService.getAllPolls());
    }

    /**
     * Get the active poll
     * @return PollResponseDTO
     */
    @GetMapping("/active")
    public PollResponseDTO getActivePoll() {
        return pollMapper.toPollResponse(pollService.getActivePoll());
    }

    /**
     * Get a poll by ID
     * @param pollId ID of the poll to get
     * @return PollResponseDTO
     */
    @GetMapping("/{pollId}")
    public PollResponseDTO getPollById(@PathVariable Long pollId) {
        return pollMapper.toPollResponse(pollService.getPollById(pollId));
    }

    /**
     * Activate a poll
     * @param pollId ID of the poll to activate
     * @return PollResponseDTO  
     */
    @PostMapping("/{pollId}/activate")
    public PollResponseDTO activatePoll(@PathVariable Long pollId) {
        return pollMapper.toPollResponse(pollService.activatePoll(pollId));
    }

    /**
     * Deactivate a poll
     * @param pollId ID of the poll to deactivate
     * @return PollResponseDTO
     */
    @PostMapping("/{pollId}/deactivate")
    public PollResponseDTO deactivatePoll(@PathVariable Long pollId) {
        // TODO: consider if I'm happy with a noop if the poll is not active
        return pollMapper.toPollResponse(pollService.deactivatePollById(pollId));
    }

}
