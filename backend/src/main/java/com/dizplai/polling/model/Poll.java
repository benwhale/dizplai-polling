package com.dizplai.polling.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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

    /**
     * The id of the poll, used as the primary key.
     */
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    /**
     * Whether the poll is currently active.
     */
    private boolean active = false;

    /**
     * The question of the poll.
     */
    private String question;

    /**
     * The options of the poll. 
     * This is a one-to-many relationship with the Option entity.
     * The 2-7 option constraint is enforced by the DTOs
     */
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Option> options;

}
