package org.spectra.hangbookmarket.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.common.exception.CommonException;
import org.spectra.hangbookmarket.rent.common.exception.RentException;
import org.spectra.hangbookmarket.rent.domain.Rent;
import org.spectra.hangbookmarket.rent.service.RentService;
import org.spectra.hangbookmarket.common.api.CommonApiResponse;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final RentService rentService;

    @Operation(summary = "내가 대여한 모든 책 정보", description = "내가 대여한 모든 책 정보를 조회합니다.")
    @GetMapping("/rents")
    public ResponseEntity<CommonApiResponse<?>> myRentList(HttpSession session) {
        List<Rent> rentedList = rentService.rentedList((Long) session.getAttribute("userId"));

        return ResponseEntity.ok(
                CommonApiResponse.success(rentedList, "success")
        );
    }

    @Operation(summary = "내가 대여한 책의 정보", description = "내가 대여한 책 정보를 조회합니다.")
    @GetMapping("/rents/{rentId}")
    public ResponseEntity<CommonApiResponse<?>> myRent(@PathVariable(name = "rentId") Long rentId, HttpSession session) {
        Rent rent = rentService.getRent(rentId, (Long) session.getAttribute("userId"));

        return ResponseEntity.ok(
                CommonApiResponse.success(rent, "success")
        );
    }

    @Operation(summary = "책 반납", description = "책을 반납합니다.")
    @Parameter(name = "rentId", description = "대여ID", required = true)
    @PostMapping("/rents/{rentId}/return")
    public ResponseEntity<CommonApiResponse<?>> returnBook(@PathVariable(name = "rentId") Long rentId, HttpSession session) {
        try {
            rentService.returnBook(rentId, (Long) session.getAttribute("userId"));

            return ResponseEntity.ok(CommonApiResponse.success(rentId, "success"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CommonApiResponse.fail(e.getMessage()));
        }
    }

    @Operation(summary = "책 반납일자 연장", description = "책의 반납일자를 연장합니다.")
    @Parameter(name = "rentId", description = "반납일자를 연장할 책의 ID", required = true)
    @PatchMapping("/rents/{rentId}/extend-due-date")
    public ResponseEntity<CommonApiResponse<?>> extendBook(@PathVariable(name = "rentId") Long rentId, HttpSession session) {
        //TODO: 책 반납일자 연장 로직 추가
        try {
            rentService.extendDueDate(rentId, (Long) session.getAttribute("userId"));

            return ResponseEntity.ok(CommonApiResponse.success(rentId, "success"));
        } catch (RentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CommonApiResponse.fail(e.getMessage()));
        } catch (CommonException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CommonApiResponse.fail(e.getMessage()));
        }

    }
}
