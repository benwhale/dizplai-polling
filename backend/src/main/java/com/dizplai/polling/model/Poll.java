package com.dizplai.polling.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor // JPA requires a no-args constructor
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Poll extends BaseEntity {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private boolean active = false;

    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Option> options;

}
