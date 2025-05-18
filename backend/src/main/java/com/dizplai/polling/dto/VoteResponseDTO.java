package com.dizplai.polling.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponseDTO {
    
    private Long id;
    private String optionName;
    private LocalDateTime createdAt;

}