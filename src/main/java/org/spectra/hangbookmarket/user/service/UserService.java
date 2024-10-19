package org.spectra.hangbookmarket.user.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.user.api.dto.UserApiDto;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    public UserApiDto getUserInfo(Long userId)
    {
        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return UserApiDto.builder()
            .users(user)
            .build();
    }

    public Users findUser(Long userId)
    {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }
}
