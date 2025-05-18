package com.dizplai.polling.service;

import com.dizplai.polling.model.Poll;
import com.dizplai.polling.repository.PollRepository;
import java.util.List;
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

    public Poll createPoll(Poll poll) {
        logger.debug("Creating poll using object: {}", poll);        
        // Set the poll for each option
        if (poll.getOptions() != null) {
            poll.getOptions().forEach(option -> option.setPoll(poll));
        }
        logger.info("Creating poll: {}", poll);
        return pollRepository.save(poll);
        
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll getPollById(Long id) {
        return pollRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Poll not found"));
    }

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

    public Poll deactivatePollById(Long id) {
        Poll poll = getPollById(id);
        return deactivatePoll(poll);
    }

    private Poll deactivatePoll(Poll poll) {
        poll.setActive(false);
        return pollRepository.save(poll);
    }

    public Poll getActivePoll() {
        return pollRepository.findByActiveTrue().orElse(null);
    }
    
}
