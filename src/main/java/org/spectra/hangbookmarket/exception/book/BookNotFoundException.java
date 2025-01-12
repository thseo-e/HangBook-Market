package org.spectra.hangbookmarket.exception.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookNotFoundException extends Exception {

    private Long bookId;

    public BookNotFoundException(Long bookId) {
        super("Book not found ID : " + bookId);
        this.bookId = bookId;
    }

    public BookNotFoundException(String message) {
        super(message);
    }


}
