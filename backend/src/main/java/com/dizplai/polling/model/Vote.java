package com.dizplai.polling.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor // JPA requires a no-args constructor
@Data
public class Vote {
    
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    
    @ManyToOne
    private Poll poll;
    
    @ManyToOne
    private Option option;

    @CreationTimestamp
    private Date createdAt; // TODO split into abstract class
    
    @UpdateTimestamp
    private Date updatedAt;

    /* TODO I'd love to put IP Address here as a basic way of monitoring where the votes are coming from */

}
