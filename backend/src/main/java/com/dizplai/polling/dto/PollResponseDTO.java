package com.dizplai.polling.dto;

import java.time.LocalDateTime;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer object for returning a poll
 */
@Schema(description = "Data Transfer object for returning a poll")
public record PollResponseDTO(
    @Schema(description = "The ID of the poll", example = "1") Long id,

    @Schema(description = "Whether the poll is active", example = "true") boolean active,

    @Schema(description = "The question of the poll", example = "Who will win the Premier League?") String question,

    @Schema(description = "The options of the poll", example = """
        [
          {"id": 1, "name": "Manchester United", "voteCount": 10},
          {"id": 2, "name": "Liverpool", "voteCount": 15},
          {"id": 3, "name": "Chelsea", "voteCount": 8},
          {"id": 4, "name": "Arsenal", "voteCount": 12}
        ]
        """) List<OptionResponseDTO> options,

    @Schema(description = "The date and time the poll was created", example = "2021-01-01T00:00:00Z") LocalDateTime createdAt) {
}
