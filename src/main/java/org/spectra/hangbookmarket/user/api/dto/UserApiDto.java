package org.spectra.hangbookmarket.user.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.spectra.hangbookmarket.user.domain.User;

@Getter
@Setter
@RequiredArgsConstructor
public class UserApiDto
{
    private Long id;

    private String name;

    private String password;

    private String ldapId;

    @Builder
    public UserApiDto(User user)
    {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.ldapId = user.getLdapId();
    }
}
