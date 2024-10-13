package org.spectra.hangbookmarket.user.application;

import java.util.Base64;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.user.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController
{
    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginParam param)
    {
        return loginService.login(
            param.getId(), new String(Base64.getDecoder().decode(param.getPasswd()))
        );
    }
}
