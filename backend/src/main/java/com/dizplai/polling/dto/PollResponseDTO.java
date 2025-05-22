package com.dizplai.polling.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PollResponseDTO(Long id, boolean active, String question, List<OptionResponseDTO> options, LocalDateTime createdAt) {}
