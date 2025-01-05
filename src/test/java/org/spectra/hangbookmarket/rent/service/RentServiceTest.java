package org.spectra.hangbookmarket.rent.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.domain.BookStatus;
import org.spectra.hangbookmarket.book.repository.BookJpaRepository;
import org.spectra.hangbookmarket.book.service.RentService;
import org.spectra.hangbookmarket.book.api.RentApiRequest;
import org.spectra.hangbookmarket.book.domain.Rent;
import org.spectra.hangbookmarket.book.repository.RentJpaRepository;
import org.spectra.hangbookmarket.user.api.dto.LoginRequest;
import org.spectra.hangbookmarket.user.domain.Users;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RentServiceTest
{
    @Mock
    RentJpaRepository rentJpaRepository;

    @Mock
    BookJpaRepository bookJpaRepository;

    @InjectMocks
    RentService rentService;

    Users user;

    List<CreateBookRequest> createBookRequestList = new ArrayList<>();

    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void createBook()
    {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId("test");
        loginRequest.setPasswd("test");

        user = Users.createdUser(loginRequest);

        for (int i = 1; i <= 5; i++)
        {
            createBookRequestList.add(new CreateBookRequest("테스트 주도 개발 시작하기 " + i, user.getId()));
        }

    }

    protected void rentFlow(Book book) {
        if (book.getStatus() == BookStatus.AVAILABLE) {
            book.updateStatus(BookStatus.RENTED);

            Rent rent = Rent.builder()
                .book(book)
                .user(user)
                .build();
            rentJpaRepository.save(rent);
        }


    }
    @DisplayName("도서가 대여 가능한 상태면 대여 할 수 있다.")
    @Test
    void rentBookSuccess()
    {
        // given
        // Rent DTO로 받아옴
        // Rent는 대여 할 사람 / 대여 할 책 을 가지고 있음.
        RentApiRequest rentApiRequest = new RentApiRequest(1L, 1L);

        Book book = Book.createBook(createBookRequestList.get(0), user);

        when(bookJpaRepository.findById(rentApiRequest.getBookId())).thenReturn(Optional.of(book));

        // when
        // 책이 존재하는지 확인.
        // 책이 대여 가능한지 확인.
        // 대여 가능하면 대여 처리.
        bookJpaRepository.findById(rentApiRequest.getBookId())
            .ifPresent();



        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("도서가 대여 불가능한 상태면 대여 할 수 없다.")
    @Test

}