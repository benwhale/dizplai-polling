package com.dizplai.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dizplai.polling.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

    /**
     * Atomically increment the vote count for an option.
     * 
     * @param id The id of the option to increment the vote count for.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Option o SET o.voteCount = o.voteCount + 1 WHERE o.id = :id")
    void incrementVoteCount(@Param("id") Long id);

}
