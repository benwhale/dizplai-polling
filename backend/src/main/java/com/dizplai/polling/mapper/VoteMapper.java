package com.dizplai.polling.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.dizplai.polling.dto.VoteCreationDTO;
import com.dizplai.polling.dto.VoteResponseDTO;
import com.dizplai.polling.model.Vote;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "poll", ignore = true)
    @Mapping(target = "option", ignore = true)
    Vote toVote(VoteCreationDTO request);

    @Mapping(target = "optionName", source = "option.name")
    VoteResponseDTO toVoteResponse(Vote vote);

    List<VoteResponseDTO> toVoteResponses(List<Vote> votes);
}
