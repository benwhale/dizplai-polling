package com.dizplai.polling.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.dizplai.polling.dto.VoteCreationDTO;
import com.dizplai.polling.dto.VoteResponseDTO;
import com.dizplai.polling.model.Vote;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    /**
     * Map a VoteCreationDTO to a Vote.
     * Minimal information is requred to create a vote, so many fields are ignored.
     * @param request the VoteCreationDTO to map
     * @return the Vote
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "poll", ignore = true)
    @Mapping(target = "option", ignore = true)
    Vote toVote(VoteCreationDTO request);

    /**
     * Map a Vote to a VoteResponseDTO.
     * @param vote the Vote to map
     * @return the VoteResponseDTO
     */
    @Mapping(target = "optionName", source = "option.name")
    VoteResponseDTO toVoteResponse(Vote vote);

    /**
     * Map a List of Votes to a List of VoteResponseDTOs.
     * @param votes the List of Votes to map
     * @return the List of VoteResponseDTOs
     */
    List<VoteResponseDTO> toVoteResponses(List<Vote> votes);
}
