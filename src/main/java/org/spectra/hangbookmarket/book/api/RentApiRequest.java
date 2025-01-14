package org.spectra.hangbookmarket.book.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentApiRequest
{
    private Long bookId;

    private Long userId;

    public RentApiRequest(Long bookId, Long userId)
    {
        this.bookId = bookId;
        this.userId = userId;
    }
}
