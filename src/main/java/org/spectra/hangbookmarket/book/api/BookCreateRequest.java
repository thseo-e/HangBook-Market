package org.spectra.hangbookmarket.book.api;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.spectra.hangbookmarket.user.domain.Users;

@Getter
@Setter
public class BookCreateRequest
{
    private Long id;

    private String name;

    private Long userId;

    private LocalDateTime createDate;

    private String status;

    public BookCreateRequest(Long id, String name, Long userId, LocalDateTime createDate, String status)
    {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.createDate = createDate;
        this.status = status;
    }
}
