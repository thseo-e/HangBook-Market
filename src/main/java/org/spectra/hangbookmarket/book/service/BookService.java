package org.spectra.hangbookmarket.book.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.api.dto.BookDto;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
import org.spectra.hangbookmarket.book.common.exception.BookException;
import org.spectra.hangbookmarket.book.common.exception.BookErrorType;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.repository.BookJpaRepository;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookJpaRepository bookJpaRepository;
    private final UserService userService;


    //Book의 CRUD를 담당하는 서비스를 구현해주세요.

    public Long createBook(CreateBookRequest createBookRequest) {
        Users user = userService.findUser(createBookRequest.getUserId());

        Book savedBook = bookJpaRepository.save(Book.createBook(createBookRequest, user));

        return savedBook.getId();
    }

    public BookDto getBookDto(Long bookId) {
        Book book = bookJpaRepository.findById(bookId).orElseThrow(() ->
                new BookException(BookErrorType.BOOK_NOT_FOUND)
        );

        return BookDto.of(book);
    }

    public Book getBookEntity(Long bookId) {
        return bookJpaRepository.findById(bookId).orElseThrow(() ->
                new BookException(BookErrorType.BOOK_NOT_FOUND)
        );
    }

    public Long updateBook(UpdateBookRequest updateBookRequest) {
        Book findBook = bookJpaRepository.findById(updateBookRequest.getId()).orElseThrow(() ->
                new BookException(BookErrorType.BOOK_NOT_FOUND)
        );

        Users updatedUser = userService.findUser(updateBookRequest.getUserId());

        findBook.updateBook(updateBookRequest, updatedUser);

        return findBook.getId();
    }

    public void deleteBook(Long bookId) {
        bookJpaRepository.findById(bookId).ifPresentOrElse(
                bookJpaRepository::delete, () -> {
                    throw new IllegalArgumentException("해당 책이 존재하지 않습니다.");
                });
    }


}
