package com.dizplai.polling.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dizplai.polling.model.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {

    /**
     * Get the active poll if it exists
     * @return Optional<Poll>
     */
    Optional<Poll> findByActiveTrue();
}

