package org.spectra.hangbookmarket.user.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.user.api.dto.UserApiDto;
import org.spectra.hangbookmarket.user.domain.User;
import org.spectra.hangbookmarket.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    public UserApiDto getUserInfo(Long userId)
    {
        Optional<User> user = userRepository.findById(userId);

        return UserApiDto.builder()
            .user(user.get())
            .build();
    }

}
