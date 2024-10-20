package org.spectra.hangbookmarket.book.api.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.spectra.hangbookmarket.book.domain.BookStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateBookRequest
{
    private Long id;

    private String name;

    private Long userId;

    private LocalDateTime createDate;

    private BookStatus status;
}
