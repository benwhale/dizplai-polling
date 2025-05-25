package com.dizplai.polling.service;

import com.dizplai.polling.model.Poll;
import com.dizplai.polling.repository.PollRepository;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class PollService {

    private static final Logger logger = LoggerFactory.getLogger(PollService.class);

    private final PollRepository pollRepository;    

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    /**
     * Create a new poll
     * @param poll The poll to create
     * @return The created poll
     */
    public Poll createPoll(Poll poll) {
        logger.debug("Creating poll using object: {}", poll);        
        // Set the poll for each option
        if (poll.getOptions() != null) {
            poll.getOptions().forEach(option -> option.setPoll(poll));
        }
        logger.info("Creating poll: {}", poll);
        
        return pollRepository.save(poll);
        
    }

    /**
     * Get all polls
     * @return A list of all polls
     */
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    /**
     * Get a poll by ID
     * @param id The ID of the poll to get
     * @return The poll with the given ID
     * @throws NoSuchElementException if the poll is not found
     */
    public Poll getPollById(Long id) {
        return pollRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Poll not found"));
    }

    /**
     * Activate a poll
     * @param id The ID of the poll to activate
     * @return The activated poll
     * @throws NoSuchElementException if the poll is not found
     */
    public Poll activatePoll(Long id) {
        // Only one poll can be active at a time, so we need to deactivate the current active poll
        Poll currentActivePoll = getActivePoll();
        if (currentActivePoll != null) {
            deactivatePoll(currentActivePoll);
        }
        Poll poll = getPollById(id);
        poll.setActive(true);
        return pollRepository.save(poll);
    }

    /**
     * Deactivate a poll
     * @param id The ID of the poll to deactivate
     * @return The deactivated poll
     * @throws NoSuchElementException if the poll is not found
     */
    public Poll deactivatePollById(Long id) {
        Poll poll = getPollById(id);
        return deactivatePoll(poll);
    }

    /**
     * Deactivate a poll
     * @param poll The poll to deactivate
     * @return The deactivated poll
     */
    private Poll deactivatePoll(Poll poll) {
        poll.setActive(false);
        return pollRepository.save(poll);
    }

    /**
     * Get the active poll
     * @return The active poll
     * @throws NoSuchElementException if the poll is not found
     */
    public Poll getActivePoll() {
        return pollRepository.findByActiveTrue().orElseThrow(() -> new NoSuchElementException("No active poll found"));
    }
    
}
