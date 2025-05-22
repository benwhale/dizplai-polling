package com.dizplai.polling.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.dizplai.polling.dto.OptionCreationDTO;
import com.dizplai.polling.dto.OptionResponseDTO;
import com.dizplai.polling.model.Option;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "voteCount", constant = "0L")
    @Mapping(target = "poll", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Option toOption(OptionCreationDTO request);
    
    OptionResponseDTO toOptionResponse(Option option);
    
    List<OptionResponseDTO> toOptionResponses(List<Option> options);
}