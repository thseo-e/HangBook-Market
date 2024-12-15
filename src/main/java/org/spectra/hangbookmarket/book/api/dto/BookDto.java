package org.spectra.hangbookmarket.book.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.domain.BookStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class BookDto
{
    private Long id;

    private String name;

    private Long createdUserId;

    private String createdUserName;

    private LocalDateTime createdDate;

    private Long updatedUserId;

    private String updatedUserName;

    private LocalDateTime updatedDate;

    private BookStatus status;

    @Builder
    public BookDto(Long id, String name, Long createdUserId, String createdUserName, LocalDateTime createdDate, Long updatedUserId, String updatedUserName, LocalDateTime updatedDate, BookStatus status)
    {
        this.id = id;
        this.name = name;
        this.createdUserId = createdUserId;
        this.createdUserName = createdUserName;
        this.createdDate = createdDate;
        this.updatedUserId = updatedUserId;
        this.updatedUserName = updatedUserName;
        this.updatedDate = updatedDate;
        this.status = status;
    }

    public static BookDto of(Book book)
    {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .createdUserId(book.getCreatedUser().getId())
                .createdUserName(book.getCreatedUser().getName())
                .createdDate(book.getCreatedDate())
                .updatedUserId(book.getUpdatedUser().getId())
                .updatedUserName(book.getUpdatedUser().getName())
                .updatedDate(book.getUpdatedDate())
                .status(book.getStatus())
                .build();
    }
}
