package org.spectra.hangbookmarket.book.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
import org.spectra.hangbookmarket.rent.domain.Rent;
import org.spectra.hangbookmarket.user.domain.Users;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id")
    private Rent rent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id")
    private Users createUser;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id")
    private Users updateUser;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Builder
    protected Book(Long id, String name, Users createUser, LocalDateTime createDate, Users updateUser, LocalDateTime updateDate, BookStatus status)
    {
        this.id = id;
        this.name = name;
        this.createUser = createUser;
        this.createDate = createDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
        this.status = status;
    }

    public static Book createBook(CreateBookRequest createBookRequest, Users createUser)
    {
        return Book.builder()
            .name(createBookRequest.getName())
            .createUser(createUser)
            .createDate(LocalDateTime.now())
            .updateUser(createUser)
            .updateDate(LocalDateTime.now())
            .status(BookStatus.AVAILABLE)
            .build();
    }

    public void updateBook(UpdateBookRequest request, Users updateUser)
    {
        this.name = request.getName();
        this.updateUser = updateUser;
        this.updateDate = LocalDateTime.now();
        this.status = request.getStatus();
    }
}
