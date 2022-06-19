package com.example.uebung1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MoviesTests {

    @Autowired
    private MockMvc mockMvc; //not a problem, appearently
    //cool comment

    @Test
    void testCase1() throws Exception {
        this.mockMvc
                .perform(get("/movies"))
                .andExpect(status().isOk()) //status code is 200
                .andExpect(content().json("[]")) //empty list
        ;
    }

    @Test
    void testCase2() throws Exception {
        this.mockMvc
                .perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Top-Gun\" }"))
                .andExpect(status().isCreated()) //status code is 201
                .andExpect(jsonPath("$").doesNotExist()) //no response body
        ;
    }

    @Test
    void testCase3() throws Exception {
        this.mockMvc
                .perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Who am I\" }"))

        ;
        this.mockMvc
                .perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 0,\"name\": \"Who am I\"}]"))
        ;
    }

    @Test
    void testCase4() throws Exception {
        this.mockMvc
                .perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"King Kong\" }"))
        ;
        this.mockMvc
                .perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"300\" }"))
        ;
        this.mockMvc
                .perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":  0, \"name\":  \"King Kong\"},{\"id\": 1, \"name\":  \"300\"}]"))
        ;
    }

    @Test
    void testCase5() throws Exception {
        this.mockMvc
                .perform(post("http://localhost:8080/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"namos\": \"Die Unglaublichen\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
        ;
    }

    @Test
    void testCase6() throws Exception {
        this.mockMvc
                .perform(post("http://localhost:8080/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"namos\": \"Die Unglaublichen\"}"))
        ;
        this.mockMvc
                .perform(post("http://localhost:8080/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"San Andreas\"}"))
        ;
        this.mockMvc
                .perform(get("http://localhost:8080/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 0,\"name\": \"San Andreas\"}]"))
        ;
    }
}
