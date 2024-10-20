package org.spectra.hangbookmarket.book.api.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.spectra.hangbookmarket.book.domain.BookStatus;

@Getter
@Setter
public class CreateBookRequest
{
    private String name;

    private Long userId;

    public CreateBookRequest(String name, Long userId)
    {
        this.name = name;
        this.userId = userId;
    }
}
