package com.dizplai.polling.mapper;

import org.mapstruct.Mapper;
import com.dizplai.polling.dto.OptionResponseDTO;
import com.dizplai.polling.model.Option;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionMapper {

    OptionResponseDTO toOptionResponse(Option option);

    List<OptionResponseDTO> toOptionResponses(List<Option> options);
}