package com.dizplai.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dizplai.polling.model.Vote;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    /**
     * Find all votes for a specific poll
     * @param pollId the ID of the poll
     * @return List of votes for the poll
     */
    List<Vote> findByPollId(Long pollId);

    /**
     * Find all votes for a specific option
     * @param optionId the ID of the option
     * @return List of votes for the option
     */
    List<Vote> findByOptionId(Long optionId);

}
