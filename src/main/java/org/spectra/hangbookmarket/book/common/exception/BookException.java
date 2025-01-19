package org.spectra.hangbookmarket.book.common.exception;

import lombok.Getter;

@Getter
public class BookException extends RuntimeException{
    private final BookErrorType errorType;

    public BookException(BookErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
