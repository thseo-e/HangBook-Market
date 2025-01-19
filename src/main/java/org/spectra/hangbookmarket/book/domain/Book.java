package org.spectra.hangbookmarket.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
import org.spectra.hangbookmarket.rent.domain.Rent;
import org.spectra.hangbookmarket.user.domain.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @Comment("책 번호")
    private Long id;

    @Column(name = "title", nullable = false)
    @Comment("책 제목")
    private String title;

    @OneToMany(mappedBy = "book")
    @Column(name = "rented_history")
    @Comment("책 대여 기록")
    private final List<Rent> rentedHistory = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user")
    private Users createdUser;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_user")
    private Users updatedUser;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Comment("대여가능/대여중")
    private BookStatus status;

    @Builder
    private Book(Long id, String title, Users createdUser, LocalDateTime createdDate, Users updatedUser, LocalDateTime updatedDate, BookStatus status)
    {
        this.id = id;
        this.title = title;
        this.createdUser = createdUser;
        this.createdDate = createdDate;
        this.updatedUser = updatedUser;
        this.updatedDate = updatedDate;
        this.status = status;
    }

    public static Book createBook(CreateBookRequest createBookRequest, Users createdUser)
    {
        return Book.builder()
            .title(createBookRequest.getTitle())
            .createdUser(createdUser)
            .createdDate(LocalDateTime.now())
            .updatedUser(createdUser)
            .updatedDate(LocalDateTime.now())
            .status(BookStatus.AVAILABLE)
            .build();
    }


    public void updateBook(UpdateBookRequest request, Users updatedUser)
    {
        this.title = request.getTitle();
        this.updatedUser = updatedUser;
        this.updatedDate = LocalDateTime.now();
        this.status = request.getStatus();
    }

    public void updateStatus(BookStatus bookStatus)
    {
        this.status = bookStatus;
    }


    public void rented(Rent rent) {
        this.status = BookStatus.RENTED;
        this.rentedHistory.add(rent);
    }

    public boolean isRented() {
        return this.status == BookStatus.RENTED;
    }


    public void returned(Rent rent) {
        this.status = BookStatus.AVAILABLE;
        this.rentedHistory.add(rent);
    }
}
