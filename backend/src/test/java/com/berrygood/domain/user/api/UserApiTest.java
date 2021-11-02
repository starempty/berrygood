package com.berrygood.domain.user.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.berrygood.BerrygoodApplicationTests;
import com.berrygood.domain.user.entity.User;

class UserApiTest extends BerrygoodApplicationTests {
    @Test
    void register() throws Exception {
        User user = new User();
        user.setName("윤동현");
        user.setEmail("hn01291@naver.com");
        user.setGender('M');
        user.setPhone("1234");
        user.setPassword("1234");
/*
        ResultActions resultActions = this.mockMvc.perform(post("/api/user/")
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .content(this.objectMapper.writeValueAsString(user)));
*/
        this.mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated());

//        resultActions.andExpect(status().isCreated())
//                .andDo(print());
    }

    @Test
    void delete(String s) throws Exception {
        String id = "hn01291@naver.com";
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("api/user"+id))
                .andExpect(status().isOk())
                .andDo(print());
    }
}