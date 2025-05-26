package com.dizplai.polling.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.dizplai.polling.dto.PollResponseDTO;
import com.dizplai.polling.dto.VoteCreationDTO;
import com.dizplai.polling.dto.VoteResponseDTO;
import com.dizplai.polling.service.VoteService;
import com.dizplai.polling.mapper.VoteMapper;
import com.dizplai.polling.model.Poll;
import com.dizplai.polling.mapper.PollMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller for managing votes
 */
@RestController
@RequestMapping("/votes")
@Tag(name = "Votes", description = "Vote management API")
public class VoteController {

    private final VoteService voteService;
    private final VoteMapper voteMapper;
    private final PollMapper pollMapper;

    public VoteController(VoteService voteService, VoteMapper voteMapper, PollMapper pollMapper) {
        this.voteService = voteService;
        this.voteMapper = voteMapper;
        this.pollMapper = pollMapper;
    }

    /**
     * Get all votes for all polls
     * 
     * @return List<VoteResponseDTO>
     */
    @Operation(summary = "Get all votes for all polls", description = "Returns a list of all votes for all polls in the database.")
    @ApiResponse(responseCode = "200", description = "List of votes returned successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VoteResponseDTO.class))))
    @GetMapping("/")
    public List<VoteResponseDTO> getAllVotes() {
        return voteMapper.toVoteResponses(voteService.getAllVotes());
    }

    /**
     * Get all votes for a poll
     * 
     * @param pollId ID of the poll to get votes for
     * @return List<VoteResponseDTO>
     */
    @Operation(summary = "Get all votes for a poll", description = "Returns a list of all votes for the poll with the given ID.")
    @ApiResponse(responseCode = "200", description = "List of votes returned successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VoteResponseDTO.class))))
    @GetMapping("/poll/{pollId}")
    public List<VoteResponseDTO> getAllVotesForPoll(@PathVariable Long pollId) {
        return voteMapper.toVoteResponses(voteService.getAllVotesForPoll(pollId));
    }

    /**
     * Get all votes for an option
     * 
     * @param optionId ID of the option to get votes for
     * @return List<VoteResponseDTO>
     */
    @Operation(summary = "Get all votes for an option", description = "Returns a list of all votes for the option with the given ID.")
    @ApiResponse(responseCode = "200", description = "List of votes returned successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VoteResponseDTO.class))))
    @GetMapping("/option/{optionId}")
    public List<VoteResponseDTO> getAllVotesForOption(@PathVariable Long optionId) {
        return voteMapper.toVoteResponses(voteService.getAllVotesForOption(optionId));
    }

    /**
     * Vote for the active poll
     * 
     * @param voteCreationDTO DTO for the vote to create
     * @return PollResponseDTO the updated poll
     */
    @Operation(summary = "Vote for the active poll", description = "Votes for the active poll.")
    @ApiResponse(responseCode = "200", description = "Vote created successfully", content = @Content(schema = @Schema(implementation = PollResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Poll not found", content = @Content)
    @PostMapping("/")
    public PollResponseDTO vote(@RequestBody @Valid VoteCreationDTO voteCreationDTO) {
        Poll updatedPoll = voteService.vote(voteCreationDTO);
        return pollMapper.toPollResponse(updatedPoll);
    }
}
