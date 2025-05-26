package com.dizplai.polling.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer object for returning a vote
 */
@Schema(description = "Data Transfer object for returning a vote")
public record VoteResponseDTO(
        @Schema(description = "The id of the vote", example = "1") Long id,
        @Schema(description = "The name of the option voted for", example = "Manchester United") String optionName,
        @Schema(description = "The date and time the vote was created", example = "2025-01-01T00:00:00Z") LocalDateTime createdAt) {
}