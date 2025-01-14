package org.spectra.hangbookmarket.book.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest
{
    private String title;

    private Long userId;

    public CreateBookRequest(String title, Long userId)
    {
        this.title = title;
        this.userId = userId;
    }
}
