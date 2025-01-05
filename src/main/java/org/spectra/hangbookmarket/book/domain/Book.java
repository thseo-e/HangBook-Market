package org.spectra.hangbookmarket.book.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
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
    private Users createdUser;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id")
    private Users updatedUser;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Builder
    private Book(Long id, String name, Users createdUser, LocalDateTime createdDate, Users updatedUser, LocalDateTime updatedDate, BookStatus status)
    {
        this.id = id;
        this.name = name;
        this.createdUser = createdUser;
        this.createdDate = createdDate;
        this.updatedUser = updatedUser;
        this.updatedDate = updatedDate;
        this.status = status;
    }

    public static Book createBook(CreateBookRequest createBookRequest, Users createdUser)
    {
        return Book.builder()
            .name(createBookRequest.getName())
            .createdUser(createdUser)
            .createdDate(LocalDateTime.now())
            .updatedUser(createdUser)
            .updatedDate(LocalDateTime.now())
            .status(BookStatus.AVAILABLE)
            .build();
    }


    public void updateBook(UpdateBookRequest request, Users updatedUser)
    {
        this.name = request.getName();
        this.updatedUser = updatedUser;
        this.updatedDate = LocalDateTime.now();
        this.status = request.getStatus();
    }

    public void updateStatus(BookStatus bookStatus)
    {
        this.status = bookStatus;
    }


    public void rented() {
        this.status = BookStatus.RENTED;
    }


}
