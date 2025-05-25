package com.dizplai.polling.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for returning a poll option
 */
@Schema(description = "Data Transfer object for returning a poll option")
public record OptionResponseDTO(

        @Schema(description = "The ID of the option", example = "1") Long id,
        @Schema(description = "The text of the option", example = "Manchester United") String name,
        @Schema(description = "The number of votes for the option", example = "10") Long voteCount) {
}
