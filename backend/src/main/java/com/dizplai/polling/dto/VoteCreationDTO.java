package com.dizplai.polling.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for Vote. Hides the internal structure of the Vote object, and provides validation for the fields.
 */
public record VoteCreationDTO(@NotNull Long optionId) {}
