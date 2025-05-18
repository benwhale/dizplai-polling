package com.dizplai.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dizplai.polling.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
    
}
