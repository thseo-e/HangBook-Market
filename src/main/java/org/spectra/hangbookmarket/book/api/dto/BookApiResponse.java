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
public class BookApiResponse
{
    private Long id;

    private String name;

    private Long createUserId;

    private String createUserName;

    private LocalDateTime createDate;

    private Long updateUserId;

    private String updatedUserName;

    private LocalDateTime updateDate;

    private BookStatus status;

    @Builder
    public BookApiResponse(Long id, String name, Long createUserId, String createUserName, LocalDateTime createDate, Long updateUserId, String updatedUserName, LocalDateTime updateDate, BookStatus status)
    {
        this.id = id;
        this.name = name;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.createDate = createDate;
        this.updateUserId = updateUserId;
        this.updatedUserName = updatedUserName;
        this.updateDate = updateDate;
        this.status = status;
    }

    public static BookApiResponse of(Book book)
    {
        return BookApiResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .createUserId(book.getCreateUser().getId())
                .createUserName(book.getCreateUser().getName())
                .createDate(book.getCreateDate())
                .updateUserId(book.getUpdateUser().getId())
                .updatedUserName(book.getUpdateUser().getName())
                .updateDate(book.getUpdateDate())
                .status(book.getStatus())
                .build();
    }
}
