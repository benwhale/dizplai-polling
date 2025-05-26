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

    /**
     * Map a PollCreationDTO to a Poll.
     * Minimal information is requred to create a poll, so many fields are ignored.
     * @param dto the PollCreationDTO to map
     * @return the Poll
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "options", source = "options")
    Poll toPoll(PollCreationDTO dto);

    /**
     * Map a String to an Option.
     * This is a helper method to map a String to an Option used by MapStruct.
     * @param name the String to map
     * @return the Option
     */
    default Option stringToOption(String name) {
        Option option = new Option();
        option.setName(name);
        option.setVoteCount(0L);
        return option;
    }

    /**
     * Map a List of Strings to a List of Options.
     * This is a helper method to map a List of Strings to a List of Options used by MapStruct.
     * @param names the List of Strings to map
     * @return the List of Options
     */
    default List<Option> stringsToOptions(List<String> names) {
        if (names == null)
            return Collections.emptyList();
        return names.stream()
                .map(this::stringToOption)
                .toList();
    }

    /**
     * Map a Poll to a PollResponseDTO.
     * @param poll the Poll to map
     * @return the PollResponseDTO
     */
    @Mapping(target = "id", source = "id") // explicitly set the id
    @Mapping(target = "options", source = "options")
    PollResponseDTO toPollResponse(Poll poll);

    /**
     * Map a List of Polls to a List of PollResponseDTOs.
     * @param polls the List of Polls to map
     * @return the List of PollResponseDTOs
     */
    List<PollResponseDTO> toPollResponses(List<Poll> polls);
}
