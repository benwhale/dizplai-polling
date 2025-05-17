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

@AllArgsConstructor
@Data
@Entity
public class Poll {
    
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
    
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Option> options; 
    /* TODO: Validate 2-7 options */

    private String question;
    
    @CreationTimestamp
    private Date createdAt;
    
    @UpdateTimestamp
    private Date updatedAt;
    
}
