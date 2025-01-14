package org.spectra.hangbookmarket.book.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.domain.Rent;
import org.spectra.hangbookmarket.book.repository.RentJpaRepository;
import org.spectra.hangbookmarket.exception.book.BookRentedAlreadyException;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentService
{
    private final RentJpaRepository rentJpaRepository;
    private final BookService bookService;
    private final UserService userService;

    /*
    * 대여 로직
    * 도서가 빌릴 수 있는 상태인지 확인. ->
    * 빌릴 수 있다면,
    *   도서 상태 변경 -> 대여중
    *   대여한 내역 기록 (유저, 날짜,
    * */
    @Transactional
    public Long rentBook(Long bookId, Long userId) throws Exception
    {
        Book book = bookService.getBookEntity(bookId);
        Users user = userService.findUser(userId);

        if (book.isRented()) {
            throw new BookRentedAlreadyException(bookId);
        }

        return rentJpaRepository.save(Rent.rented(book, user)).getId();
    }

}
