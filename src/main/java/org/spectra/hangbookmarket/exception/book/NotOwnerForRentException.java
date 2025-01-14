package org.spectra.hangbookmarket.exception.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotOwnerForRentException extends Exception{

    private Long bookId;

    public NotOwnerForRentException(Long bookId) {
        super("You are not the owner.");
        this.bookId = bookId;
    }

    public NotOwnerForRentException(String message) {
        super(message);
    }
}
