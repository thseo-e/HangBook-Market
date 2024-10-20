package org.spectra.hangbookmarket.book.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.api.dto.BookApiResponse;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
import org.spectra.hangbookmarket.book.service.BookService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookApiController
{
    private final BookService bookService;

    //책 등록
    @PostMapping("/books")
    public String createBook(@RequestBody CreateBookRequest createBookRequest, HttpSession session)
    {
        createBookRequest.setUserId((Long) session.getAttribute("userId"));

        Long createBookId = bookService.createBook(createBookRequest);

        return "create Book Success : book Id : " + createBookId;
    }

    @GetMapping("/books/{bookId}")
    public BookApiResponse getBook(@PathVariable Long bookId)
    {
        return bookService.getBook(bookId);
    }

    @PutMapping("/books")
    public String updateBook(@RequestBody UpdateBookRequest updateBookRequest, HttpSession session)
    {
        updateBookRequest.setUserId((Long) session.getAttribute("userId"));

        Long bookId = bookService.updateBook(updateBookRequest);

        return "update Book Success. bookId : " + bookId;
    }

    @DeleteMapping("/books/{bookId}")
    public String deleteBook(@PathVariable Long bookId)
    {
        bookService.deleteBook(bookId);

        return "delete Book Success";
    }

}
