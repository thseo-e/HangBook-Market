package org.spectra.hangbookmarket.book.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.api.BookCreateRequest;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.repository.BookRepository;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService
{
    private final BookRepository bookRepository;
    private final UserService userService;


    //Book의 CRUD를 담당하는 서비스를 구현해주세요.

    public String createBook(BookCreateRequest bookCreateRequest)
    {
        Users user = userService.findUser(bookCreateRequest.getUserId());

        Book savedBook = bookRepository.save(Book.createBook(bookCreateRequest, user));

        return savedBook.getName();
    }

    public Book getBook(Long bookId)
    {
        return bookRepository.findById(bookId).orElse(null);
    }

    public Book updateBook(Long bookId, Book book)
    {
        Book findBook = bookRepository.findById(bookId).orElse(null);

        if(findBook == null)
        {
            return null;
        }

        return bookRepository.save(findBook);
    }

    public void deleteBook(Long bookId)
    {
        bookRepository.deleteById(bookId);
    }


}
