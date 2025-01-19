 package org.spectra.hangbookmarket.common.exception;

 import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private final CommonErrorType errorType;

    public CommonException(CommonErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
