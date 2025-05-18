package com.dizplai.polling.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollResponseDTO {
    private Long id;
    private boolean active;
    private String question;
    private List<OptionResponseDTO> options;
    private LocalDateTime createdAt; 
}
