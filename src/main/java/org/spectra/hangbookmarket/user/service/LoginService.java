package org.spectra.hangbookmarket.user.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.user.api.dto.LoginRequest;
import org.spectra.hangbookmarket.user.api.dto.UserApiDto;
import org.spectra.hangbookmarket.user.domain.User;
import org.spectra.hangbookmarket.user.repository.UserRepository;
import org.spectra.hangbookmarket.user.thirdparty.LdapService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService
{
    private final LdapService ldap;
    private final UserRepository userRepository;

    public UserApiDto login(LoginRequest loginRequest)
    {
        Optional<User> user = userRepository.findByNameAndPassword(loginRequest.getUserId(), loginRequest.getPasswd());

        if (user.isPresent())
        {
            return UserApiDto.builder()
                .user(user.get())
                .build();
        }

        User newUser = userRepository.save(User.createUser(loginRequest));

        return UserApiDto.builder()
            .user(newUser)
            .build();
    }

    public boolean loginLdap(String id, String decodedPasswd)
    {
        try
        {
            String userName = id + "@spectra.co.kr";
            ldap.connect("dc1.spectra.co.kr", "389", "p=Spectra", userName, decodedPasswd);

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
