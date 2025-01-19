package org.spectra.hangbookmarket.book.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookErrorType {
    BOOK_NOT_FOUND("Book not found"),
    BOOK_RENT_FAIL("Failed Rent Book"),
    BOOK_RETURN_FAIL("Failed Return Book"),
    BOOK_NOT_OWNER("Not Owner Book"),;

    private final String message;



}
