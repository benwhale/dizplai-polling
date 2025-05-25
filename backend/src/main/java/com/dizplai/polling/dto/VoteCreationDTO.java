package com.dizplai.polling.dto;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for Vote. Hides the internal structure of the Vote object, and provides
 * validation for the fields.
 */
@Schema(description = "Data Transfer object for creating a new vote")
public record VoteCreationDTO(
        @NotNull(message = "Option ID is required") @Schema(description = "The ID of the option to vote for", example = "1") Long optionId) {
}
