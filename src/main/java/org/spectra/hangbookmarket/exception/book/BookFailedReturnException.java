package org.spectra.hangbookmarket.exception.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFailedReturnException extends Exception{

    private Long bookId;

    public BookFailedReturnException(Long bookId) {
        super("This Book is not available for Rent - " + bookId);
        this.bookId = bookId;
    }

    public BookFailedReturnException(String message) {
        super(message);
    }
}
