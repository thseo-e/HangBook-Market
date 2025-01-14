package org.spectra.hangbookmarket.rent.service;

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
import org.spectra.hangbookmarket.exception.book.BookFailedRentException;
import org.spectra.hangbookmarket.user.api.dto.LoginRequest;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//@Transactional
@SpringBootTest
class RentServiceTest {
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
    void createBook() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId("test");
        loginRequest.setPasswd("test");

        user = Users.createdUser(loginRequest);

        for (int i = 1; i <= 5; i++) {
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
            throw new BookFailedRentException(bookId);
        }

        Long rentedId = rentJpaRepository.save(Rent.rented(book, user)).getId();

        rentJpaRepository.findById(rentedId).ifPresent(rent -> {
            assertThat(rent.getBook().getId()).isEqualTo(bookId);
        });


    }

    @Test
    public void testRentBook_Success() throws Exception {
        Long bookId = 1L;
        Long userId = 1L;
        Book book = mock(Book.class);
        Users user = mock(Users.class);
        Rent rent = mock(Rent.class);
        when(bookService.getBookEntity(bookId)).thenReturn(book);
        when(userService.findUser(userId)).thenReturn(user);
        when(book.isRented()).thenReturn(false);
        when(rentJpaRepository.save(any(Rent.class))).thenReturn(rent);
        when(rent.getId()).thenReturn(1L);
        Long rentId = rentService.rentBook(bookId, userId);
        assertEquals(1L, rentId);
        verify(bookService).getBookEntity(bookId);
        verify(userService).findUser(userId);
        verify(rentJpaRepository).save(any(Rent.class));
    }

    @Test
    public void testRentBook_Failed() throws BookNotFoundException {
        Long bookId = 1L;
        Long userId = 1L;
        Book book = mock(Book.class);
        when(bookService.getBookEntity(bookId)).thenReturn(book);
        when(book.isRented()).thenReturn(true);
        assertThrows(BookFailedRentException.class, () -> rentService.rentBook(bookId, userId));
    }

}