package com.dizplai.polling.mapper;

import org.mapstruct.Mapper;
import com.dizplai.polling.dto.OptionResponseDTO;
import com.dizplai.polling.model.Option;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionMapper {

    /**
     * Map an Option to an OptionResponseDTO.
     * @param option the Option to map
     * @return the OptionResponseDTO
     */
    OptionResponseDTO toOptionResponse(Option option);

    /**
     * Map a List of Options to a List of OptionResponseDTOs.
     * @param options the List of Options to map
     * @return the List of OptionResponseDTOs
     */
    List<OptionResponseDTO> toOptionResponses(List<Option> options);
}