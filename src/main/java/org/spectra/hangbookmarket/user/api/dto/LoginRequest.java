package org.spectra.hangbookmarket.user.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest
{
    private String userId;

    private String passwd;
}
