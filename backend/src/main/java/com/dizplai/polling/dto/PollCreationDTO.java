package com.dizplai.polling.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for Poll. Hides the internal structure of the Poll object, and provides
 * validation for the fields.
 */
@Schema(description = "Data Transfer object for creating a new poll")
public record PollCreationDTO(

        @NotBlank(message = "Question is required") @Schema(description = "The question of the poll", example = "Who will win the Premier League?") String question,

        @Size(min = 2, max = 7, message = "Poll must have between 2 and 7 options") @Schema(description = "The poll options", example = """
                ["Manchester United", "Liverpool", "Chelsea", "Arsenal"]
                """) List<String> options) {
}
