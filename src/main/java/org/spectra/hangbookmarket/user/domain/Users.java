package org.spectra.hangbookmarket.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.spectra.hangbookmarket.rent.domain.Rent;
import org.spectra.hangbookmarket.user.api.dto.LoginRequest;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "ldap_id", nullable = false)
    private String ldapId;

    @OneToMany(mappedBy = "rentedUser")
    @Column(name = "rented_history")
    @Comment("대여기록")
    private final List<Rent> rentedHistory = new ArrayList<>();

    @Builder
    public Users(String name, String password, String ldapId)
    {
        this.name = name;
        this.password = password;
        this.ldapId = ldapId;
    }

    public static Users createdUser(LoginRequest request)
    {
        return Users.builder()
            .ldapId(request.getUserId())
            .name(request.getUserId())
            .password(request.getPasswd())
            .build();
    }

    public void addRentedHistory(Rent rent) {
        this.rentedHistory.add(rent);
    }
}
