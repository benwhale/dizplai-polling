package com.dizplai.polling.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int voteCount;
    
    @ManyToOne
    @ToString.Exclude // avoid infinite recursion
    private Poll poll;

    @ToString.Include(name = "poll")
    private String pollToString() {
        return poll == null || poll.getId() == null ? "null" : poll.getId().toString();
    }
}
