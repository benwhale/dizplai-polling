package com.dizplai.polling.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO for Poll. Hides the internal structure of the Poll object, and provides validation for the fields.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollCreationDTO {

    @NotBlank(message = "Question is required")
    private String question;
    
    @Size(min = 2, max = 7, message = "Poll must have between 2 and 7 options")
    private List<OptionCreationDTO> options;



}
