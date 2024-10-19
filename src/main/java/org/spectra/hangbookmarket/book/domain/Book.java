package org.spectra.hangbookmarket.book.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spectra.hangbookmarket.book.api.BookCreateRequest;
import org.spectra.hangbookmarket.book.api.UpdateBookRequest;
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
    @JoinColumn(name = "user_id")
    private Users createUser;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Builder
    protected Book(Long id, String name, Users createUser, LocalDateTime createDate, BookStatus status)
    {
        this.id = id;
        this.name = name;
        this.createUser = createUser;
        this.createDate = createDate;
        this.status = status;
    }

    public static Book createBook(BookCreateRequest bookCreateRequest, Users user)
    {
        return Book.builder()
            .name(bookCreateRequest.getName())
            .createUser(user)
            .createDate(LocalDateTime.now())
            .status(BookStatus.AVAILABLE)
            .build();
    }

    public void updateBook(UpdateBookRequest request, Users user)
    {
        this.name = request.getName();
        this.createUser = user;
        this.createDate = request.getCreateDate();
        this.status = request.getStatus();
    }
}
