package com.dizplai.polling.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@AllArgsConstructor
@NoArgsConstructor // JPA requires a no-args constructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Vote extends BaseEntity {

    /**
     * The id of the vote, used as the primary key.
     */
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    /**
     * The poll that the vote belongs to.
     */
    @ManyToOne
    private Poll poll;

    /**
     * The option that the vote belongs to.
     */
    @ManyToOne
    private Option option;

    /*
     * TODO I'd love to put IP Address here as a basic way of monitoring where the
     * votes are coming from
     */

}
