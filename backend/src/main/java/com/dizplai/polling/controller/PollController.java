package com.dizplai.polling.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dizplai.polling.model.Poll;
import com.dizplai.polling.service.PollService;
import com.dizplai.polling.mapper.PollMapper;
import com.dizplai.polling.dto.PollCreationDTO;
import com.dizplai.polling.dto.PollResponseDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequestMapping("/polls")
@Tag(name = "Polls", description = "Poll management API")
public class PollController {

    private final PollService pollService;
    private final PollMapper pollMapper;

    public PollController(PollService pollService, PollMapper pollMapper) {
        this.pollService = pollService;
        this.pollMapper = pollMapper;
    }

    /**
     * Create a new poll
     * 
     * @param pollCreationDTO DTO for the poll to create
     * @return PollResponseDTO
     */
    @Operation(summary = "Create a new poll", description = "Creates a new poll with the provided question and options. The poll is initially inactive.")
    @ApiResponse(responseCode = "200", description = "Poll created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping("/")
    public PollResponseDTO createPoll(@RequestBody PollCreationDTO pollCreationDTO) {
        Poll pollToCreate = pollMapper.toPoll(pollCreationDTO);
        Poll createdPoll = pollService.createPoll(pollToCreate);
        return pollMapper.toPollResponse(createdPoll);
    }

    /**
     * Get all polls
     * 
     * @return List<PollResponseDTO>
     */
    @Operation(summary = "Get all polls", description = "Returns a list of all polls in the database.")
    @ApiResponse(responseCode = "200", description = "List of polls returned successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PollResponseDTO.class))))
    @GetMapping("/")
    public List<PollResponseDTO> getAllPolls() {
        return pollMapper.toPollResponses(pollService.getAllPolls());
    }

    /**
     * Get the active poll
     * 
     * @return PollResponseDTO
     */
    @Operation(summary = "Get the active poll", description = "Returns the active poll in the database.")
    @ApiResponse(responseCode = "200", description = "Active poll returned successfully", content = @Content(schema = @Schema(implementation = PollResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "No active poll found", content = @Content)
    @GetMapping("/active")
    public PollResponseDTO getActivePoll() {
        return pollMapper.toPollResponse(pollService.getActivePoll());
    }

    /**
     * Get a poll by ID
     * 
     * @param pollId ID of the poll to get
     * @return PollResponseDTO
     */
    @Operation(summary = "Get a poll by ID", description = "Returns the poll with the given ID.")
    @ApiResponse(responseCode = "200", description = "Poll returned successfully", content = @Content(schema = @Schema(implementation = PollResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Poll not found", content = @Content)
    @GetMapping("/{pollId}")
    public PollResponseDTO getPollById(@PathVariable Long pollId) {
        return pollMapper.toPollResponse(pollService.getPollById(pollId));
    }

    /**
     * Activate a poll
     * 
     * @param pollId ID of the poll to activate
     * @return PollResponseDTO
     */
    @Operation(summary = "Activate a poll", description = "Activates the poll with the given ID.")
    @ApiResponse(responseCode = "200", description = "Poll activated successfully", content = @Content(schema = @Schema(implementation = PollResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Poll not found", content = @Content)
    @PostMapping("/{pollId}/activate")
    public PollResponseDTO activatePoll(@PathVariable Long pollId) {
        return pollMapper.toPollResponse(pollService.activatePoll(pollId));
    }

    /**
     * Deactivate a specific poll
     * 
     * @param pollId ID of the poll to deactivate
     * @return PollResponseDTO
     */
    @Operation(summary = "Deactivate a poll", description = "Deactivates the poll with the given ID.")
    @ApiResponse(responseCode = "200", description = "Poll deactivated successfully", content = @Content(schema = @Schema(implementation = PollResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Poll not found", content = @Content)
    @PostMapping("/{pollId}/deactivate")
    public PollResponseDTO deactivatePoll(@PathVariable Long pollId) {
        // TODO: consider if I'm happy with a noop if the poll is not active
        return pollMapper.toPollResponse(pollService.deactivatePollById(pollId));
    }

}
