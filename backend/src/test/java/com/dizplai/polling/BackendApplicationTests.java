package com.dizplai.polling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.dizplai.polling.model.Poll;
import com.dizplai.polling.model.Option;
import com.dizplai.polling.dto.VoteCreationDTO;
import com.dizplai.polling.service.PollService;
import com.dizplai.polling.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@Transactional
@AutoConfigureMockMvc
class BackendApplicationTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.5")
            .withDatabaseName("polling_db_test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PollService pollService;

    @Autowired
    private VoteService voteService;

    @Test
    void contextLoads() {
        Assertions.assertTrue(postgres.isRunning());
    }

    @Test
    void testCompleteVoteFlow() throws Exception {
    // Given there is an active poll with valid options
    // Create a poll with options
    Poll poll = new Poll();
    poll.setQuestion("Who is going to win the Premier League?");
    
    Option option1 = new Option();
    option1.setName("Manchester United");
    option1.setVoteCount(0L);
    Option option2 = new Option();
    option2.setName("Liverpool");
    option2.setVoteCount(0L);
    Option option3 = new Option();
    option3.setName("Chelsea");
    option3.setVoteCount(0L);
    
    poll.setOptions(new ArrayList<>(List.of(option1, option2, option3))); // Mutable list for Hibernate

    // Insert the poll into the database
    Poll createdPoll = pollService.createPoll(poll);

    // Activate the poll
    Poll activatedPoll = pollService.activatePoll(createdPoll.getId());
    
    Long optionId = activatedPoll.getOptions().get(0).getId();

    ResultActions resultActions = mockMvc.perform(post("/votes/")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(new VoteCreationDTO(optionId)))).andDo(print());

    // Assert the response status is 200
    resultActions.andExpect(status().isOk());

    // Assert the vote was created
    Assertions.assertEquals(1, voteService.getAllVotes().size());
    
    // Assert the option has one vote
    Poll activePoll = pollService.getActivePoll();
    // Handle if the options have been shuffled
    Option votedForOption = activePoll.getOptions().stream().filter(option -> option.getId() == optionId).findFirst().orElseThrow();
    Assertions.assertEquals(1, votedForOption.getVoteCount());
    }
    
}
