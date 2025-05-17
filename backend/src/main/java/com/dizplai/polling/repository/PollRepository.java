package com.dizplai.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dizplai.polling.model.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {

}

