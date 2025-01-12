package org.spectra.hangbookmarket.book.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.repository.BookJpaRepository;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class BookServiceTest
{
    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private UserRepository userRepository;

    private CreateBookRequest createBookRequest;

    @BeforeEach
    void createBookRequest()
    {
        createBookRequest = new CreateBookRequest( "책 이름", 1L);
    }

    @BeforeEach
    void createdUser()
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
        Book book = Book.createBook(createBookRequest, user);

        Book savedBook = bookJpaRepository.save(book);

        // then
        assertThat(savedBook.getTitle()).isEqualTo(createBookRequest.getTitle());
    }

    @DisplayName("책 등록 실패 - 이름이 없는 경우")
    @Test
    void createNoNameBook()
    {
        // given
        Users user = userRepository.findById(1L).orElse(null);

        // when
        createBookRequest.setTitle(null);
        Book book = Book.createBook(createBookRequest, user);

        assertThrows(DataIntegrityViolationException.class, () -> bookJpaRepository.save(book), "책 등록 성공");

    }

    @DisplayName("책 내용 수정")
    @Test
    void updateBook()
    {
        // given

        Users user = userRepository.findById(1L).orElse(null);
        Book book = Book.createBook(createBookRequest, user);

        bookJpaRepository.save(book);

        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setTitle("updatedName");

        Optional<Book> findBook = bookJpaRepository.findById(book.getId());

        // when
        findBook.ifPresent(b ->
            b.updateBook(updateBookRequest, user)
        );

        // then
        assertThat(book.getId()).isEqualTo(findBook.get().getId());
        assertThat(book.getTitle()).isNotEqualTo(findBook.get().getTitle());

    }

    @DisplayName("책 삭제")
    @Test
    void deleteBook()
    {
        // given
        Long bookId = 1L;
        Users user = userRepository.findById(bookId).orElse(null);

        Book book = Book.createBook(createBookRequest, user);

        bookJpaRepository.save(book);


        // when
        bookJpaRepository.findById(bookId).ifPresentOrElse(
            bookJpaRepository::delete, () -> {
                throw new IllegalArgumentException("해당 책이 존재하지 않습니다.");
            });
        // then
        assertThat(bookJpaRepository.findById(bookId).isEmpty()).isTrue();
    }

    @DisplayName("책 삭제 실패 - 다른 ID로 삭제 시도")
    @Test
    void deleteBookFail()
    {
        // given
        Long bookId = 1L;
        Users user = userRepository.findById(bookId).orElse(null);

        Book book = Book.createBook(createBookRequest, user);

        bookJpaRepository.save(book);

        // when
        Long otherBookId = 2L;
        bookJpaRepository.findById(otherBookId).ifPresentOrElse(
            bookJpaRepository::delete, () -> {
                throw new IllegalArgumentException("해당 책이 존재하지 않습니다.");
            });
        // then
        assertThat(bookJpaRepository.findById(bookId).isEmpty()).isFalse();
    }
}