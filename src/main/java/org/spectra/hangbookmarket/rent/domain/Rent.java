package org.spectra.hangbookmarket.rent.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.user.domain.Users;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rent
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private LocalDateTime rentDate;

    private LocalDateTime dueDate;


}
