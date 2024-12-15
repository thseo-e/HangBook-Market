package org.spectra.hangbookmarket.book.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.api.dto.BookDto;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
import org.spectra.hangbookmarket.book.domain.BookStatus;
import org.spectra.hangbookmarket.rent.domain.Rent;
import org.spectra.hangbookmarket.rent.repository.RentJpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentService
{
    private final RentJpaRepository rentJpaRepository;
    private final BookService bookService;
    /*
    * 대여 로직
    * 도서가 빌릴 수 있는 상태인지 확인. ->
    * 빌릴 수 있다면,
    *   도서 상태 변경 -> 대여중
    *   대여한 내역 기록 (유저, 날짜,
    * */
    public void rentBook(Long bookId, Long userId)
    {
        BookDto bookDto = bookService.getBook(bookId);

        if (bookDto.getStatus() == BookStatus.RENTED) {
            //TODO 이미 대여중인 도서 처리
        }

        UpdateBookRequest updateBookRequest = new UpdateBookRequest();


        bookService.updateBook();

        Rent

        rentJpaRepository.
    }
}
