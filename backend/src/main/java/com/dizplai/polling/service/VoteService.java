package com.dizplai.polling.service;

import org.springframework.stereotype.Service;

import com.dizplai.polling.model.Vote;
import com.dizplai.polling.repository.VoteRepository;
import com.dizplai.polling.repository.OptionRepository;
import com.dizplai.polling.repository.PollRepository;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.transaction.Transactional;

import com.dizplai.polling.dto.VoteCreationDTO;
import com.dizplai.polling.model.Option;
import com.dizplai.polling.model.Poll; 



@Service
public class VoteService {


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
     * @param voteCreationDTO DTO for the vote to create
     */
    @Transactional
    public Vote vote(VoteCreationDTO voteCreationDTO) {

        // Get the active poll (error out early if there is no active poll)
        Poll activePoll = pollRepository.findByActiveTrue()
            .orElseThrow(() -> new NoSuchElementException("No active poll found"));

        //Validate that the option exists
        Option option = optionRepository.findById(voteCreationDTO.getOptionId())
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

        // Atomically increment the vote count for the option to avoid race conditions
        optionRepository.incrementVoteCount(option.getId());
        
        return savedVote;

    }
    
}
