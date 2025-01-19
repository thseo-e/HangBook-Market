package org.spectra.hangbookmarket.rent.common.exception;

import lombok.Getter;

@Getter
public class RentException extends RuntimeException{
    private final RentErrorType errorType;

    public RentException(RentErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
