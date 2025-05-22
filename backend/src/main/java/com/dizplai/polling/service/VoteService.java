package com.dizplai.polling.service;

import org.springframework.stereotype.Service;

import com.dizplai.polling.model.Vote;
import com.dizplai.polling.repository.VoteRepository;
import com.dizplai.polling.repository.OptionRepository;
import com.dizplai.polling.repository.PollRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;

import com.dizplai.polling.dto.VoteCreationDTO;
import com.dizplai.polling.model.Option;
import com.dizplai.polling.model.Poll; 



@Service
public class VoteService {

    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;
    private final OptionRepository optionRepository;
    private final PollRepository pollRepository;

    public VoteService(VoteRepository voteRepository, OptionRepository optionRepository, PollRepository pollRepository) {
        this.voteRepository = voteRepository;
        this.optionRepository = optionRepository;
        this.pollRepository = pollRepository;
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public List<Vote> getAllVotesForPoll(Long pollId) {
        return voteRepository.findByPollId(pollId);
    }

    public List<Vote> getAllVotesForOption(Long optionId) {
        return voteRepository.findByOptionId(optionId);
    }

    /**
     * Vote for the active poll.
     * This relies on the assumption that there is only one active poll at a time.
     * Otherwise we would need to validate that the vote is for the active poll.
     * It wouldn't be difficult to add a pollId field to the DTO and validate that the vote is for the active poll.
     * @param voteCreationDTO DTO for the vote to create
     */
    @Transactional
    public Poll vote(VoteCreationDTO voteCreationDTO) {

        // Get the active poll (error out early if there is no active poll)
        Poll activePoll = pollRepository.findByActiveTrue()
            .orElseThrow(() -> new NoSuchElementException("No active poll found"));

        //Validate that the option exists
        Option option = optionRepository.findById(voteCreationDTO.optionId())
            .orElseThrow(() -> new NoSuchElementException("Option not found"));

        //Validate that the option belongs to the poll
        if (!option.getPoll().getId().equals(activePoll.getId())) {
            throw new IllegalArgumentException("Option does not belong to the active poll");
        }

        // Create and Save the vote
        // Opportunity for validation here.
        // Have they already voted?
        Vote vote = new Vote();
        vote.setOption(option);
        vote.setPoll(activePoll);

        // Save the vote
        Vote savedVote = voteRepository.save(vote);
        logger.info("Vote saved: {}", savedVote);

        // Atomically increment the vote count for the option to avoid race conditions
        optionRepository.incrementVoteCount(option.getId());


        // Get the updated poll
        // The other option is to return the saved vote, and then let the client fetch the updated poll.
        // I'm erring on the side of simplicity here and returning the updated poll to save the client from an extra request.
        // A light redis layer could provide the vote count for the option without the need to fetch the entire poll after each vote.
        return pollRepository.findById(activePoll.getId()).orElseThrow(() -> new NoSuchElementException("Poll not found"));

    }
    
}
