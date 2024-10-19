package org.spectra.hangbookmarket.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spectra.hangbookmarket.user.api.dto.LoginRequest;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginServiceTest
{
    @Autowired
    UserRepository userRepository;

    LoginRequest request = new LoginRequest();

    @BeforeEach
    public void createLoginRequest()
    {
        request.setUserId("thseo");
        request.setPasswd("123qwe");
    }

    @DisplayName("user 생성")
    @Test
    void createUser()
    {
        Users users = Users.createUser(request);

        // when
        Users savedUsers = userRepository.save(users);

        // then
        assertThat(savedUsers.getName()).isEqualTo(request.getUserId());
    }

    @DisplayName("Login 실패")
    @Test
    void loginFail()
    {
        // given
        LoginRequest requestFail = new LoginRequest();
        requestFail.setUserId("Test");
        requestFail.setPasswd("test");

        // when
        Optional<Users> user = userRepository.findByNameAndPassword(requestFail.getUserId(), requestFail.getPasswd());

        // then
        assertThat(user.isPresent()).isFalse();
    }

    @DisplayName("login 성공")
    @Test
    void testMethodNameHere()
    {
        // given
        Users createUser = Users.createUser(request);
        userRepository.save(createUser);

        // when
        Optional<Users> user = userRepository.findByNameAndPassword(request.getUserId(), request.getPasswd());

        // then
        assertThat(user.isPresent()).isTrue();
    }
}