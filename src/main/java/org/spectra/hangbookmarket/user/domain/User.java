package org.spectra.hangbookmarket.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spectra.hangbookmarket.user.api.LoginRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "ldap_id", nullable = false)
    private String ldapId;

    @Builder
    public User(String name, String password, String ldapId)
    {
        this.name = name;
        this.password = password;
        this.ldapId = ldapId;
    }

    public static User createUser(LoginRequest request)
    {
        return User.builder()
            .ldapId(request.getUserId())
            .name(request.getUserId())
            .password(request.getPasswd())
            .build();
    }
}
