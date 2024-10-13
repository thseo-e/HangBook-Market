package org.spectra.hangbookmarket.user.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginParam
{
    private String id;

    private String passwd;
}
