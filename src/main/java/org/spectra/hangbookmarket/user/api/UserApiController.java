package org.spectra.hangbookmarket.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.service.RentService;
import org.spectra.hangbookmarket.user.api.dto.UserApiDto;
import org.spectra.hangbookmarket.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class UserApiController
{
    private final UserService userService;
    private final RentService rentService;

    @Operation(summary = "내가 대여한 책 정보", description = "내가 대여한 책 정보를 조회합니다.")
    @GetMapping("/rented")
    public String myRentedInfo(HttpSession session)
    {
        //TODO : 세션에서 유저 정보를 가져와서 대여한 책 정보를 조회하는 로직을 구현해주세요.

        return "my rented info";
    }
    @Operation(summary = "책 반납", description = "책을 반납합니다.")
    @Parameter(name = "rentId", description = "대여ID", required = true)
    @PostMapping("/rented/{rentId}/return")
    public String returnBook(@PathVariable(name = "rentId") Long rentId, HttpSession session)
    {
        //TODO: 책 반납 로직 추가
        try {
            Long returnedId = rentService.returnBook(rentId, (Long) session.getAttribute("userId"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "return book";
    }

    @Operation(summary = "책 반납일자 연장", description = "책의 반납일자를 연장합니다.")
    @Parameter(name = "rentId", description = "반납일자를 연장할 책의 ID", required = true)
    @PostMapping("/rented/{rentId}/extend")
    public String extendBook(@PathVariable(name = "rentId") Long rentId, HttpSession session)
    {
        //TODO: 책 반납일자 연장 로직 추가
        return "extend book";
    }
}
