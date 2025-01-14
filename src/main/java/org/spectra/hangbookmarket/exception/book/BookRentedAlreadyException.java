package org.spectra.hangbookmarket.exception.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRentedAlreadyException extends Exception{

    private Long bookId;

    public BookRentedAlreadyException(Long bookId) {
        super("This Book is Already Rented. - " + bookId);
        this.bookId = bookId;
    }

    public BookRentedAlreadyException(String message) {
        super(message);
    }
}
