package com.dizplai.polling.mapper;

import org.mapstruct.*;
import com.dizplai.polling.model.*;
import com.dizplai.polling.dto.PollCreationDTO;
import com.dizplai.polling.dto.PollResponseDTO;
import java.util.List;


@Mapper(componentModel = "spring", uses = {OptionMapper.class})
public interface PollMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Poll toPoll(PollCreationDTO request);
    
    @Mapping(target = "id", source = "id") // explicitly set the id
    @Mapping(target = "options", source = "options")
    PollResponseDTO toPollResponse(Poll poll);
    
    // For collections
    List<PollResponseDTO> toPollResponses(List<Poll> polls);
}
