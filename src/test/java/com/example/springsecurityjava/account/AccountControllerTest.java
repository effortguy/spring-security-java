package com.example.springsecurityjava.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Test
    void index_anonymous() throws Exception {
        mockMvc.perform(get("/").with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void index_anonymous_annotation() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void index_user() throws Exception {
        // 로그인을 한 상태에서 테스트
        mockMvc.perform(get("/").with(user("keesun").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "keesun", roles = "USER")
    void index_user_annotation() throws Exception {
        // 로그인을 한 상태에서 테스트
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void admin_user() throws Exception {
        // 로그인을 한 상태에서 테스트
        mockMvc.perform(get("/admin").with(user("keesun").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_admin() throws Exception {
        // 로그인을 한 상태에서 테스트
        mockMvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAdmin
    void admin_admin_annotation() throws Exception {
        // 로그인을 한 상태에서 테스트
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    public void login_성공() throws Exception {
        Account user = new Account("keesun", "123", "USER");

        accountService.createNew(user);

        mockMvc.perform(formLogin().user("keesun").password("123"))
                .andExpect(authenticated());
    }

    @Transactional
    @Test
    public void login_실패() throws Exception {
        Account user = new Account("keesun", "123", "USER");

        accountService.createNew(user);

        mockMvc.perform(formLogin().user("keesun").password("1234"))
                .andExpect(unauthenticated());
    }
}