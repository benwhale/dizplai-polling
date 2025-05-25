package com.dizplai.polling.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.dizplai.polling.model.Poll;
import com.dizplai.polling.model.Option;
import com.dizplai.polling.dto.PollCreationDTO;
import com.dizplai.polling.dto.PollResponseDTO;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PollMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "options", source = "options")
    Poll toPoll(PollCreationDTO dto);

    default Option stringToOption(String name) {
        Option option = new Option();
        option.setName(name);
        option.setVoteCount(0L);
        return option;
    }

    default List<Option> stringsToOptions(List<String> names) {
        if (names == null)
            return Collections.emptyList();
        return names.stream()
                .map(this::stringToOption)
                .toList();
    }

    @Mapping(target = "id", source = "id") // explicitly set the id
    @Mapping(target = "options", source = "options")
    PollResponseDTO toPollResponse(Poll poll);

    // For collections
    List<PollResponseDTO> toPollResponses(List<Poll> polls);
}
