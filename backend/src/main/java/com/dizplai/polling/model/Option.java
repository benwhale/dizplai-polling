package com.dizplai.polling.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Option extends BaseEntity {
    /**
     * The id of the option, used as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long voteCount;

    /**
     * The poll that the option belongs to.
     */
    @ManyToOne
    @ToString.Exclude // avoid infinite recursion
    private Poll poll;

    /**
     * Return the id of the poll if set, otherwise return "null".
     */
    @ToString.Include(name = "poll")
    private String pollToString() {
        return poll == null || poll.getId() == null ? "null" : poll.getId().toString();
    }
}
