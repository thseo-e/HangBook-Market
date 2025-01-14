package org.spectra.hangbookmarket.exception.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFailedRentException extends Exception{

    private Long bookId;

    public BookFailedRentException(Long bookId) {
        super("This Book is Already Rented. - " + bookId);
        this.bookId = bookId;
    }

    public BookFailedRentException(String message) {
        super(message);
    }
}
