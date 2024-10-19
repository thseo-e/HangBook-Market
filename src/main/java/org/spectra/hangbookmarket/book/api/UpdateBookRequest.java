package org.spectra.hangbookmarket.book.api;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
