package org.spectra.hangbookmarket.user.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.user.api.dto.UserApiDto;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController
{
    private final UserService userService;

    @Operation(summary = "내가 대여한 책 정보", description = "내가 대여한 책 정보를 조회합니다.")
    @GetMapping("/rented")
    public String myRentedInfo(HttpSession session)
    {
        //TODO : 세션에서 유저 정보를 가져와서 대여한 책 정보를 조회하는 로직을 구현해주세요.

        return "my rented info";
    }
}
