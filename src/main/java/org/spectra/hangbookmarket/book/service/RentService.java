package org.spectra.hangbookmarket.book.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.domain.Rent;
import org.spectra.hangbookmarket.book.repository.RentJpaRepository;
import org.spectra.hangbookmarket.exception.book.BookFailedRentException;
import org.spectra.hangbookmarket.exception.book.BookFailedReturnException;
import org.spectra.hangbookmarket.exception.book.NotOwnerForRentException;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentService {
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
    public Long rentBook(Long bookId, Long userId) throws Exception {
        Book book = bookService.getBookEntity(bookId);
        Users user = userService.findUser(userId);

        if (book.isRented()) {
            throw new BookFailedRentException(bookId);
        }

        return rentJpaRepository.save(Rent.rented(book, user)).getId();
    }

    @Transactional
    public Long returnBook(Long rentId, Long userId) throws Exception {
        //TODO 에러 처리를 어떻게할지...?
        rentJpaRepository.findById(rentId)
                .map(rent -> {
                    try {
                        validateRent(rent, userId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    rent.returned();
                    return rentId;
                })
                .orElseThrow(() -> new BookFailedReturnException(rentId));
        return rentId;
    }

    private void validateRent(Rent rent, Long userId) throws Exception {
        Book book = rent.getBook();

        if(!Objects.equals(rent.getRentedUser().getId(), userId)) {
            throw new NotOwnerForRentException(book.getId());
        }
        if (!book.isRented()) {
            throw new BookFailedReturnException(book.getId());
        }
    }
}
