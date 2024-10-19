package org.spectra.hangbookmarket.user.api;

import java.util.Base64;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.user.api.dto.LoginRequest;
import org.spectra.hangbookmarket.user.api.dto.UserApiDto;
import org.spectra.hangbookmarket.user.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApiController
{
    private final LoginService loginService;


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpSession session)
    {
        UserApiDto dto = loginService.login(loginRequest);

        if(dto == null)
        {
            return "Login Failed";
        }
        else
        {
            session.setAttribute("userName", dto.getName());
            session.setAttribute("userId", dto.getId());

            return "Login Success";
        }
    }

    @PostMapping("/login/ldap")
    public String loginLdap(@RequestBody LoginRequest loginRequest, HttpSession session)
    {
        //LDAP 로그인
        boolean loginResult = loginService.loginLdap(
            loginRequest.getUserId(), new String(Base64.getDecoder().decode(loginRequest.getPasswd()))
        );

        if (loginResult)
        {
            session.setAttribute("userId", loginRequest.getUserId());
            return "Login Successful";
        }
        else
        {
            return "Login Failed";
        }

    }
}
