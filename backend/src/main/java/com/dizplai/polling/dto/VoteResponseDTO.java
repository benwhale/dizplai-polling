package com.dizplai.polling.dto;

import java.time.LocalDateTime;

public record VoteResponseDTO(Long id, String optionName, LocalDateTime createdAt) {}