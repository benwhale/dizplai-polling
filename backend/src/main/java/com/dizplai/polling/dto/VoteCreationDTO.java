package com.dizplai.polling.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Vote. Hides the internal structure of the Vote object, and provides validation for the fields.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteCreationDTO {

    @NotNull
    private Long optionId;
}
