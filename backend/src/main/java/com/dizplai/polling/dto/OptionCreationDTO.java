package com.dizplai.polling.dto;

import jakarta.validation.constraints.NotBlank;


/**
 * DTO for Option. Hides the internal structure of the Option object, and provides validation for the fields.
 */

public record OptionCreationDTO(@NotBlank(message = "Option text is required") String name) {
}
