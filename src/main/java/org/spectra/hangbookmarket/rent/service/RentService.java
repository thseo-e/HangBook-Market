package org.spectra.hangbookmarket.rent.service;

import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.common.exception.BookException;
import org.spectra.hangbookmarket.book.common.exception.BookErrorType;
import org.spectra.hangbookmarket.book.domain.Book;
import org.spectra.hangbookmarket.book.service.BookService;
import org.spectra.hangbookmarket.common.exception.CommonErrorType;
import org.spectra.hangbookmarket.common.exception.CommonException;
import org.spectra.hangbookmarket.rent.common.exception.RentErrorType;
import org.spectra.hangbookmarket.rent.common.exception.RentException;
import org.spectra.hangbookmarket.rent.domain.Rent;
import org.spectra.hangbookmarket.rent.repository.RentJpaRepository;
import org.spectra.hangbookmarket.user.domain.Users;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentService {
    private final RentJpaRepository rentJpaRepository;
    private final BookService bookService;
    private final UserService userService;

    /*
    * 대여 내역을 가져온다.
    * */
    public Rent getRent(Long rentId, Long userId) {
        return rentJpaRepository.findById(rentId)
                .orElseThrow(() -> new RentException(RentErrorType.RENT_NOT_FOUND)
                );
    }

    /*
    * 대여 목록을 가져온다.
    * */
    public List<Rent> rentedList(Long userId) {

        return rentJpaRepository.findAllByRentedUser(userService.findUser(userId));
    }

    /*
     * 대여 로직
     * 도서가 빌릴 수 있는 상태인지 확인. ->
     * 빌릴 수 있다면,
     *   도서 상태 변경 -> 대여중
     *   대여한 내역 기록 (유저, 날짜,
     * */
    @Transactional
    public Long rentBook(Long bookId, Long userId){
        Book book = bookService.getBookEntity(bookId);
        Users user = userService.findUser(userId);

        if (book.isRented()) {
            throw new BookException(BookErrorType.BOOK_RENT_FAIL);
        }

        return rentJpaRepository.save(Rent.rented(book, user)).getId();
    }

    @Transactional
    public Long returnBook(Long rentId, Long userId){
        //TODO 연체 후 반납되었을 때 Flag가 필요할지? 아니면 Status에 추가할지?
        rentJpaRepository.findById(rentId)
                .map(rent -> {
                    validateRent(rent, userId);

                    rent.returned();
                    return rentId;
                })
                .orElseThrow(() -> new BookException(BookErrorType.BOOK_RETURN_FAIL));
        return rentId;
    }

    private void validateRent(Rent rent, Long userId){
        Book book = rent.getBook();

        if(!Objects.equals(rent.getRentedUser().getId(), userId)) {
            throw new CommonException(CommonErrorType.UNAUTHORIZED);
        }
        if (!book.isRented()) {
            throw new BookException(BookErrorType.BOOK_RETURN_FAIL);
        }
    }

    /*
    * 대여 기간을 늘릴 수 있음.
    * */
    public boolean extendDueDate(Long rentId, Long userId) {
        Rent rent = rentJpaRepository.findById(rentId)
                .orElseThrow(() -> new RentException(RentErrorType.RENT_NOT_FOUND));

        Users rentedUser = rent.getRentedUser();

        if(rentedUser != null && Objects.equals(rentedUser.getId(), userId)) {
            throw new CommonException(CommonErrorType.UNAUTHORIZED);
        }

        rent.extendDueDate(rentedUser);

        return true;
    }
}
