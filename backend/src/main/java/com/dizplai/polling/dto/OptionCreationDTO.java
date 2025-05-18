package com.dizplai.polling.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;    

/**
 * DTO for Option. Hides the internal structure of the Option object, and provides validation for the fields.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionCreationDTO {
    
    @NotBlank(message = "Option text is required")
    private String name;

}
