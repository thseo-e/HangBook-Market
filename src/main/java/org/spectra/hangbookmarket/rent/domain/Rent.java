package org.spectra.hangbookmarket.rent.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.user.domain.Users;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*대여 관리 도메인*/
public class Rent
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @Comment("대여 번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    @Comment("대여한 책")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "rented_user")
    @Comment("대여한 유저")
    private Users rentedUser;

    @ManyToOne
    @JoinColumn(name = "updated_user")
    private Users updatedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Comment("대여/반납/연체")
    private RentStatus status;

    @Column(name = "rent_date")
    @Comment("대여일")
    private LocalDateTime rentDate;

    @Column(name = "due_date")
    @Comment("반납 예정일")
    private LocalDateTime dueDate;

    @Column(name = "returned_date")
    @Comment("반납일")
    private LocalDateTime returnedDate;

    @Column(name = "extend_flag")
    @Comment("반납일 연장 플래그")
    private boolean extendFlag;

    //TODO 연체 후 반납되었을 때 Flag가 필요할지? 아니면 Status에 추가할지?

    @Builder
    public Rent(Book book, Users rentedUser)
    {
        this.book = book;
        this.rentedUser = rentedUser;
        this.updatedUser = rentedUser;
        this.status = RentStatus.RENTED;
        this.rentDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusDays(14);

    }

    public static Rent rented(Book book, Users rentedUser) {
        Rent rent = Rent.builder()
                .book(book)
                .rentedUser(rentedUser)
                .build();

        book.rented(rent);

        rentedUser.addRentedHistory(rent);

        return rent;
    }

    public void returned() {
        this.status = RentStatus.RETURNED;
        this.returnedDate = LocalDateTime.now();

        this.book.returned(this);
        this.rentedUser.addRentedHistory(this);

    }

    public void extendDueDate(Users user) {
        this.extendFlag = true;
        this.dueDate = LocalDateTime.now().plusDays(7);
        this.updatedUser = user;
    }
}
