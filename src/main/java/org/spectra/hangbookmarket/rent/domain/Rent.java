package org.spectra.hangbookmarket.rent.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.user.domain.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*대여 관리 도메인*/
public class Rent
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "rent")
    @Column(name = "book_id")
    List<Book> book = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "rented_user_id")
    private Users rentedUser;

    @ManyToOne
    @JoinColumn(name = "updated_user_id")
    private Users updatedUser;

    @Enumerated(EnumType.STRING)
    private RentStatus status;

    @Column(name = "rent_date")
    private LocalDateTime rentDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Builder
    public Rent(List<Book> book, Users rentedUser)
    {
        this.book.addAll(book);
        this.rentedUser = rentedUser;
        this.updatedUser = rentedUser;
        this.status = RentStatus.RENTED;
        this.rentDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusDays(14);

    }
}
