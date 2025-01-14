package org.spectra.hangbookmarket.book.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.spectra.hangbookmarket.book.domain.BookStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateBookRequest
{
    private Long id;

    private String title;

    private Long userId;

    private LocalDateTime createdDate;

    private BookStatus status;
}
