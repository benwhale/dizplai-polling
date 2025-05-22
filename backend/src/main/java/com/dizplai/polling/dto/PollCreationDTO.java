package com.dizplai.polling.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * DTO for Poll. Hides the internal structure of the Poll object, and provides validation for the fields.
 */
public record PollCreationDTO(
    @NotBlank(message = "Question is required") String question,
    @Size(min = 2, max = 7, message = "Poll must have between 2 and 7 options") List<OptionCreationDTO> options
    ) {}
