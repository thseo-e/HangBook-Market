package org.spectra.hangbookmarket.user.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.user.api.dto.UserApiDto;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userApiController
{
    private final UserService userService;

    @GetMapping("/my")
    public String getUserInfo(HttpSession session)
    {
        UserApiDto user = userService.getUserInfo((Long) session.getAttribute("userId"));

        return user.getName();
    }
}
