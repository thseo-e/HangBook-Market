package org.spectra.hangbookmarket.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spectra.hangbookmarket.user.api.LoginRequest;
import org.spectra.hangbookmarket.user.domain.User;
import org.spectra.hangbookmarket.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginServiceTest
{
    @Autowired
    UserRepository userRepository;

    @DisplayName("user 생성")
    @Test
    void createUser()
    {
        // given
        LoginRequest request = new LoginRequest();
        request.setUserId("thseo");
        request.setPasswd("123qwe");

        User user = User.createUser(request);

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getName()).isEqualTo(request.getUserId());
    }

    //TODO
    // login test
}