package com.gustavo.microservices.multiplication.challenge;

import com.gustavo.microservices.multiplication.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeAttemptController.class)
class ChallengeAttemptControllerTest {

    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ChallengeAttemptDTO> jsonRequestAttempt;

    @Autowired
    private JacksonTester<ChallengeAttempt> jsonResultAttempt;

    @Autowired
    private JacksonTester<List<ChallengeAttempt>> jsonResultAttemptList;

    @Test
    public void postValidResult() throws Exception {
        //given
        User user = new User(1L, "John");
        long attemptId = 5L;
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 70, "john", 3500);
        ChallengeAttempt expectedResponse = new ChallengeAttempt(attemptId, user,
                50, 70, 3500, true);

        given(challengeService
                .verifyAttempt(eq(attemptDTO)))
                .willReturn(expectedResponse);
        //when
        MockHttpServletResponse response = mvc.perform(
                post("/attempts").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();
        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonResultAttempt.write(expectedResponse).getJson());
    }

    @Test
    public void postInvalidResult() throws Exception {
        //given
        User user = new User(1L, "John");
        long attemptId = 5L;
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(2000, -70, "john", 3000);
        //when
        MockHttpServletResponse response = mvc.perform(
                post("/attempts").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();
        //then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getUserTests() throws Exception {
        //given
        User user = new User("john_doe");
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 50, 70, 3500, true);
        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user, 20, 10, 210, false);
        List<ChallengeAttempt> attempts = List.of(attempt1, attempt2);
        given(challengeService.getStatsForUser(user.getAlias())).willReturn(attempts);

        //when
        MockHttpServletResponse response = mvc.perform(get("/attempts").param("alias", user.getAlias()))
                .andReturn()
                .getResponse();
        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonResultAttemptList.write(attempts).getJson()
        );
    }

}