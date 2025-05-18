package com.dizplai.polling.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor // JPA requires a no-args constructor
@Data
@Entity
public class Poll {
    
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private boolean active = false;
    
    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Option> options; 
    /* TODO: Validate 2-7 options */
    
    @CreationTimestamp
    private Date createdAt;
    
    @UpdateTimestamp
    private Date updatedAt;
    
}
