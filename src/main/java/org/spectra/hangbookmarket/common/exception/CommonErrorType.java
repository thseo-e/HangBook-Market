package org.spectra.hangbookmarket.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonErrorType {
    UNAUTHORIZED("You Have Not Authorized"),;

    private final String message;



}
