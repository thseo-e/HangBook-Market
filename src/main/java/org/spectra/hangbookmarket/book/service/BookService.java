package org.spectra.hangbookmarket.book.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.api.dto.BookApiResponse;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.repository.BookRepository;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class BookService
{
    private final BookRepository bookRepository;
    private final UserService userService;


    //Book의 CRUD를 담당하는 서비스를 구현해주세요.

    public Long createBook(CreateBookRequest createBookRequest)
    {
        Users user = userService.findUser(createBookRequest.getUserId());

        Book savedBook = bookRepository.save(Book.createBook(createBookRequest, user));

        return savedBook.getId();
    }

    @Transactional(readOnly = true)
    public BookApiResponse getBook(Long bookId)
    {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
            new IllegalArgumentException("해당 책이 존재하지 않습니다.")
        );

        return BookApiResponse.of(book);
    }

    public Long updateBook(UpdateBookRequest updateBookRequest)
    {
        Book findBook = bookRepository.findById(updateBookRequest.getId()).orElseThrow(() ->
            new IllegalArgumentException("해당 책이 존재하지 않습니다.")
        );

        Users updateUser = userService.findUser(updateBookRequest.getUserId());

        findBook.updateBook(updateBookRequest, updateUser);

        return findBook.getId();
    }

    public void deleteBook(Long bookId)
    {
        bookRepository.findById(bookId).ifPresentOrElse(
            bookRepository::delete, () -> {
            throw new IllegalArgumentException("해당 책이 존재하지 않습니다.");
        });
    }


}
