package com.dizplai.polling.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dizplai.polling.model.Poll;
import com.dizplai.polling.repository.PollRepository;
import com.dizplai.polling.service.PollService;
import com.dizplai.polling.mapper.PollMapper;
import com.dizplai.polling.dto.PollCreationDTO;
import com.dizplai.polling.dto.PollResponseDTO;

@RestController
@RequestMapping("/api")
public class PollController {
    
    private final PollRepository pollRepository;
    private final PollService pollService;
    private final PollMapper pollMapper;

    public PollController(PollRepository pollRepository, PollService pollService, PollMapper pollMapper) {
        this.pollRepository = pollRepository;
        this.pollService = pollService;
        this.pollMapper = pollMapper;
    }

    @GetMapping("/polls")
    public List<PollResponseDTO> getAllPolls() {
        List<Poll> polls = pollRepository.findAll();
        return pollMapper.toPollResponses(polls);
    }

    @PostMapping("/polls")
    public PollResponseDTO createPoll(@RequestBody PollCreationDTO pollCreationDTO) {
        Poll pollToCreate = pollMapper.toPoll(pollCreationDTO);
        Poll createdPoll = pollService.createPoll(pollToCreate);
        return pollMapper.toPollResponse(createdPoll);   
    }

}
