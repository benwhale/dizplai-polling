package com.dizplai.polling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionResponseDTO {
    
    private Long id;
    private String name;
    private Long voteCount;
} 
