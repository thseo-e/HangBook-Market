package org.spectra.hangbookmarket.book.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.api.dto.BookDto;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.domain.BookStatus;
import org.spectra.hangbookmarket.book.repository.RentJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Transactional
    public void rentBook(Long bookId, Long userId)
    {
        Book book = bookService.getBookEntity(bookId);

        if (isRented(book)) {
            //TODO 이미 대여중인 도서 처리
        }

        book.rented();



    }

    private static boolean isRented(Book book) {
        return book.getStatus() == BookStatus.RENTED;
    }
}
