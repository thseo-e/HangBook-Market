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

    private CreateBookRequest createBookRequest;

    @BeforeEach
    void createBookRequest()
    {
        createBookRequest = new CreateBookRequest( "책 이름", 1L);
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
        Book book = Book.createBook(createBookRequest, user);

        Book savedBook = bookRepository.save(book);

        // then
        assertThat(savedBook.getName()).isEqualTo(createBookRequest.getName());
    }

    @DisplayName("책 등록 실패 - 이름이 없는 경우")
    @Test
    void createNoNameBook()
    {
        // given
        Users user = userRepository.findById(1L).orElse(null);

        // when
        createBookRequest.setName(null);
        Book book = Book.createBook(createBookRequest, user);

        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(book), "책 등록 성공");

    }

    @DisplayName("책 내용 수정")
    @Test
    void updateBook()
    {
        // given

        Users user = userRepository.findById(1L).orElse(null);
        Book book = Book.createBook(createBookRequest, user);

        bookRepository.save(book);

        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setName("updatedName");

        Optional<Book> findBook = bookRepository.findById(book.getId());

        // when
        findBook.ifPresent(b ->
            b.updateBook(updateBookRequest, user)
        );

        // then
        assertThat(book.getId()).isEqualTo(findBook.get().getId());
        assertThat(book.getName()).isNotEqualTo(findBook.get().getName());

    }

    @DisplayName("책 삭제")
    @Test
    void deleteBook()
    {
        // given
        Long bookId = 1L;
        Users user = userRepository.findById(bookId).orElse(null);

        Book book = Book.createBook(createBookRequest, user);

        bookRepository.save(book);


        // when
        bookRepository.findById(bookId).ifPresentOrElse(
            bookRepository::delete, () -> {
                throw new IllegalArgumentException("해당 책이 존재하지 않습니다.");
            });
        // then
        assertThat(bookRepository.findById(bookId).isEmpty()).isTrue();
    }

    @DisplayName("책 삭제 실패 - 다른 ID로 삭제 시도")
    @Test
    void deleteBookFail()
    {
        // given
        Long bookId = 1L;
        Users user = userRepository.findById(bookId).orElse(null);

        Book book = Book.createBook(createBookRequest, user);

        bookRepository.save(book);

        // when
        Long otherBookId = 2L;
        bookRepository.findById(otherBookId).ifPresentOrElse(
            bookRepository::delete, () -> {
                throw new IllegalArgumentException("해당 책이 존재하지 않습니다.");
            });
        // then
        assertThat(bookRepository.findById(bookId).isEmpty()).isFalse();
    }
}