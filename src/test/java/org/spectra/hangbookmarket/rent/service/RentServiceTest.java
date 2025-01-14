package org.spectra.hangbookmarket.rent.service;

import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.domain.Rent;
import org.spectra.hangbookmarket.book.repository.BookJpaRepository;
import org.spectra.hangbookmarket.book.repository.RentJpaRepository;
import org.spectra.hangbookmarket.book.service.BookService;
import org.spectra.hangbookmarket.book.service.RentService;
import org.spectra.hangbookmarket.exception.book.BookNotFoundException;
import org.spectra.hangbookmarket.exception.book.BookRentedAlreadyException;
import org.spectra.hangbookmarket.user.api.dto.LoginRequest;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//@Transactional
@SpringBootTest
class RentServiceTest
{
    @Mock
    RentJpaRepository rentJpaRepository;

    @Mock
    BookJpaRepository bookJpaRepository;

    @Mock
    BookService bookService;

    @Mock
    UserService userService;

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
            bookService.createBook(new CreateBookRequest("테스트 주도 개발 시작하기 " + i, user.getId()));
        }
    }

    @BeforeEach
    void checkInitData() throws BookNotFoundException {
        bookService.getBookDto(1L);
    }



    @DisplayName("도서가 대여 가능한 상태면 대여 할 수 있다.")
    @Test
    void rentBookSuccess() throws Exception {
        //given
        Long bookId = 1L;
        Long userId = 1L;
        Book book = bookService.getBookEntity(bookId);
        Users user = userService.findUser(userId);

        if (book.isRented()) {
            //TODO 이미 대여중인 도서 처리
            throw new BookRentedAlreadyException(bookId);
        }

        Long rentedId = rentJpaRepository.save(Rent.rented(book, user)).getId();

        rentJpaRepository.findById(rentedId).ifPresent(rent -> {
            assertThat(rent.getBook().getId()).isEqualTo(bookId);
        });


    }

}