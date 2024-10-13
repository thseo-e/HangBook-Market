package org.spectra.hangbookmarket.user.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.user.thirdparty.LdapService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService
{
    private LdapService ldap;

    public String login(String id, String decodedPasswd)
    {
        try
        {
            String userName = id + "@spectra.co.kr";
            ldap.connect("dc1.spectra.co.kr", "389", "p=Spectra", userName, decodedPasswd);

            return "success";
        } catch (Exception e)
        {
            //e.printStackTrace();
            return "fail";
        }
    }
}
