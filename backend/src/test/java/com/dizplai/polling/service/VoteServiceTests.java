package com.dizplai.polling.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dizplai.polling.dto.VoteCreationDTO;
import com.dizplai.polling.model.Option;
import com.dizplai.polling.model.Poll;
import com.dizplai.polling.model.Vote;
import com.dizplai.polling.repository.OptionRepository;
import com.dizplai.polling.repository.PollRepository;
import com.dizplai.polling.repository.VoteRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Assertions;

class VoteServiceTests {
    
    @Mock
    private VoteRepository voteRepository;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private PollRepository pollRepository;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVoteHappyPath() {
        // Given there is an active poll with valid options
        // Setup mock active poll
        Poll activePoll = new Poll();
        activePoll.setActive(true);
        activePoll.setId(1L);

        // Setup mock option
        Option mockOption = new Option();
        mockOption.setId(1L);
        mockOption.setPoll(activePoll);
        mockOption.setVoteCount(0L);
        mockOption.setName("Test Option 1");
        activePoll.setOptions(List.of(mockOption));

        // Setup mock vote
        Vote mockVote = new Vote();
        mockVote.setId(1L);
        mockVote.setOption(mockOption);
        mockVote.setPoll(activePoll);

        // Setup mock vote creation DTO for option 1
        VoteCreationDTO mockVoteCreationDTO = new VoteCreationDTO(1L);

        given(pollRepository.findByActiveTrue()).willReturn(Optional.of(activePoll));
        given(optionRepository.findById(1L)).willReturn(Optional.of(mockOption));
        given(voteRepository.save(any(Vote.class))).willReturn(mockVote);
        // Setup mock updated poll - hasn't incremented the vote count, but I'm testing the method logic
        given(pollRepository.findById(1L)).willReturn(Optional.of(activePoll));

        // When a vote is cast for option 1
        voteService.vote(mockVoteCreationDTO);

        // Then
        // Poll Repository should look up the active poll
        then(pollRepository).should().findByActiveTrue();
        // Option Repository should look up the option
        then(optionRepository).should().findById(1L);
        // Vote Repository should save the vote
        then(voteRepository).should().save(any(Vote.class));
        // Option Repository should increment the vote count
        then(optionRepository).should().incrementVoteCount(1L);
        // Poll Repository should look up the updated poll
        then(pollRepository).should().findById(1L);
    }

    @Test
    void testVoteWithNoActivePoll() {
        
        //Given there is no active poll
        given(pollRepository.findByActiveTrue()).willReturn(Optional.empty());

        //When a vote is cast
        VoteCreationDTO mockVoteCreationDTO = new VoteCreationDTO(1L);
        try {
            voteService.vote(mockVoteCreationDTO);
            Assertions.fail("Expected a NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            //Then a NoSuchElementException is thrown
            // Expected exception
            assertEquals("No active poll found", e.getMessage());
        }      

        // And the poll repository should have been called to find the active poll
        then(pollRepository).should().findByActiveTrue();

        // And the option repository should not have been called
        then(optionRepository).shouldHaveNoInteractions();

        // And the vote repository should not have been called
        then(voteRepository).shouldHaveNoInteractions();

        // And there should be no further interactions with the poll repository
        then(pollRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testVoteWithInvalidOption() {
        // Given there is an active poll with valid options
        // Setup mock active poll
        Poll activePoll = new Poll();
        activePoll.setActive(true);
        activePoll.setId(1L);

        // Setup mock option
        Option mockOption = new Option();
        mockOption.setId(1L);
        mockOption.setPoll(activePoll);
        mockOption.setVoteCount(0L);
        mockOption.setName("Test Option 1");
        activePoll.setOptions(List.of(mockOption));

        // Setup mock vote creation DTO for option 2
        VoteCreationDTO mockVoteCreationDTO = new VoteCreationDTO(2L);

        given(pollRepository.findByActiveTrue()).willReturn(Optional.of(activePoll));
        given(optionRepository.findById(2L)).willReturn(Optional.empty());

        // When a vote is cast for option 2 
        try {
            voteService.vote(mockVoteCreationDTO);
            Assertions.fail("Expected a NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Then a NoSuchElementException is thrown
            // Expected exception
            assertEquals("Option not found", e.getMessage());
        }
        
        // And the poll repository should have been called to find the active poll
        then(pollRepository).should().findByActiveTrue();

        // And the option repository should have been called to find the option
        then(optionRepository).should().findById(2L);

        // And the vote repository should not have been called
        then(voteRepository).shouldHaveNoInteractions();

        // And the option repository should not have been called to increment the vote count
        then(optionRepository).shouldHaveNoMoreInteractions();

        // And there should be no further interactions with the poll repository
        then(pollRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testVoteWithOptionNotBelongingToActivePoll() {
        // Given there is an active poll
        // Setup mock active poll
        Poll activePoll = new Poll();
        activePoll.setActive(true);
        activePoll.setId(1L);

        // Set up mock alternative poll (inactive)
        Poll inactivePoll = new Poll();
        inactivePoll.setActive(false);
        inactivePoll.setId(2L);

        // Setup mock option belonging to inactive poll 2
        Option mockOption = new Option();
        mockOption.setId(1L);
        mockOption.setPoll(inactivePoll);
        mockOption.setVoteCount(0L);
        mockOption.setName("Test Option Belonging to Inactive Poll");
        inactivePoll.setOptions(List.of(mockOption));

        // Setup mock vote creation DTO for option 2
        VoteCreationDTO mockVoteCreationDTO = new VoteCreationDTO(2L);

        given(pollRepository.findByActiveTrue()).willReturn(Optional.of(activePoll));
        given(optionRepository.findById(2L)).willReturn(Optional.of(mockOption));

        // When a vote is cast for an option belonging to an inactive poll
        try {
            voteService.vote(mockVoteCreationDTO);
            Assertions.fail("Expected a IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Then an IllegalArgumentException is thrown
            assertEquals("Option does not belong to the active poll", e.getMessage());
        }

        // And the poll repository should have been called to find the active poll
        then(pollRepository).should().findByActiveTrue();

        // And the option repository should have been called to find the option
        then(optionRepository).should().findById(2L);

        // And the vote repository should not have been called
        then(voteRepository).shouldHaveNoInteractions();

        // And there should be no further interactions with the poll repository
        then(pollRepository).shouldHaveNoMoreInteractions();

        // And the option repository should not have been called to increment the vote count
        then(optionRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testVoteWithActivePollDeleted() {
        // Given there is an active poll with valid options but the poll is deleted inbetween the vote and the pollRepository.findById call
        // Setup mock active poll
        Poll activePoll = new Poll();
        activePoll.setActive(true);
        activePoll.setId(1L);

        // Setup mock option
        Option mockOption = new Option();
        mockOption.setId(1L);
        mockOption.setPoll(activePoll);
        mockOption.setVoteCount(0L);
        mockOption.setName("Test Option 1");
        activePoll.setOptions(List.of(mockOption));

        // Setup mock vote
        Vote mockVote = new Vote();
        mockVote.setId(1L);
        mockVote.setOption(mockOption);
        mockVote.setPoll(activePoll);

        // Setup mock vote creation DTO for option 1
        VoteCreationDTO mockVoteCreationDTO = new VoteCreationDTO(1L);

        given(pollRepository.findByActiveTrue()).willReturn(Optional.of(activePoll));
        given(optionRepository.findById(1L)).willReturn(Optional.of(mockOption));
        given(voteRepository.save(any(Vote.class))).willReturn(mockVote);

        // Given the active poll no longer exists (perhaps it was deleted inbetween the vote and the pollRepository.findById call)
        given(pollRepository.findById(1L)).willReturn(Optional.empty());

        // When a vote is cast for option 1
        try {
            voteService.vote(mockVoteCreationDTO);
            Assertions.fail("Expected a NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Then a NoSuchElementException is thrown
            assertEquals("Poll not found", e.getMessage());
        }

        // Then
        // Poll Repository should look up the active poll
        then(pollRepository).should().findByActiveTrue();
        // Option Repository should look up the option
        then(optionRepository).should().findById(1L);
        // Vote Repository should save the vote
        then(voteRepository).should().save(any(Vote.class));
        // Option Repository should increment the vote count
        then(optionRepository).should().incrementVoteCount(1L);
        // Poll Repository should look up the updated poll
        then(pollRepository).should().findById(1L);

    }
}
