package org.spectra.hangbookmarket.common.api;

import lombok.Getter;

@Getter
public class CommonApiResponse<T> {
    private final T data;
    private final boolean success;
    private final String message;

    private CommonApiResponse(T data, String message, boolean isSuccess) {
        this.data = data;
        this.message = message;
        this.success = isSuccess;
    }


    public static <T> CommonApiResponse<T> success(T data, String message) {
        return new CommonApiResponse<T>(data, message, true);
    }
    public static <T> CommonApiResponse<T> fail(String message) {
        return new CommonApiResponse<T>(null, message, false);
    }
}
