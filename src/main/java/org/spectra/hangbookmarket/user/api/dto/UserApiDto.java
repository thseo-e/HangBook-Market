package org.spectra.hangbookmarket.user.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.spectra.hangbookmarket.user.domain.Users;

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
    public UserApiDto(Users users)
    {
        this.id = users.getId();
        this.name = users.getName();
        this.password = users.getPassword();
        this.ldapId = users.getLdapId();
    }
}
