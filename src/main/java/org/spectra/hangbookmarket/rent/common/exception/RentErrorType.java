package org.spectra.hangbookmarket.rent.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RentErrorType {
    RENT_NOT_FOUND("Rent Not Found");

    private final String message;
}
