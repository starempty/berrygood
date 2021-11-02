package com.berrygood;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.berrygood.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class BerrygoodApplicationTests {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserRepository repository;
}
