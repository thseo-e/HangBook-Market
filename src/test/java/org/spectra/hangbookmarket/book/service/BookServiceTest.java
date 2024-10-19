package org.spectra.hangbookmarket.book.service;


import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spectra.hangbookmarket.book.api.BookCreateRequest;
import org.spectra.hangbookmarket.book.api.UpdateBookRequest;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.repository.BookRepository;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class BookServiceTest
{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private BookCreateRequest bookCreateRequest;

    @BeforeEach
    void createBookRequest()
    {
        // given
        Users user = userRepository.findById(1L).orElse(null);
        bookCreateRequest = new BookCreateRequest(1L, "책 이름", 1L, LocalDateTime.now(), "대여 가능");
    }

    @BeforeEach
    void createUser()
    {
        Users user = Users.builder()
            .name("test")
            .password("1234")
            .ldapId("test")
            .build();

        userRepository.save(user);
    }

    @DisplayName("새로운 책 등록")
    @Test
    void createBook()
    {
        // given
        Users user = userRepository.findById(1L).orElse(null);

        // when
        Book book = Book.createBook(bookCreateRequest, user);

        Book savedBook = bookRepository.save(book);

        // then
        assertThat(savedBook.getName()).isEqualTo(bookCreateRequest.getName());
    }

    @DisplayName("책 등록 실패 - 이름이 없는 경우")
    @Test
    void createNoNameBook()
    {
        // given
        Users user = userRepository.findById(1L).orElse(null);

        // when
        bookCreateRequest.setName(null);
        Book book = Book.createBook(bookCreateRequest, user);

        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(book), "책 등록 성공");

    }

    @DisplayName("책 내용 수정")
    @Test
    void updateBook()
    {
        // given

        Users user = userRepository.findById(1L).orElse(null);
        Book book = Book.createBook(bookCreateRequest, user);

        bookRepository.save(book);

        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setName("updatedName");

        Optional<Book> findBook = bookRepository.findById(book.getId());

        // when
        findBook.ifPresent(b -> {
            b.updateBook(updateBookRequest, user);
        });

        // then
        assertThat(book.getId()).isEqualTo(findBook.get().getId());
        assertThat(book.getName()).isNotEqualTo(findBook.get().getName());

    }

}