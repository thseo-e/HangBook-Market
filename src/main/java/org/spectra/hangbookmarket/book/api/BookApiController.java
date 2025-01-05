package org.spectra.hangbookmarket.book.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.spectra.hangbookmarket.book.api.dto.BookDto;
import org.spectra.hangbookmarket.book.api.dto.CreateBookRequest;
import org.spectra.hangbookmarket.book.api.dto.UpdateBookRequest;
import org.spectra.hangbookmarket.book.service.BookService;
import org.spectra.hangbookmarket.book.service.RentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "책 API", description = "책에 대한 API")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookApiController
{
    private final BookService bookService;
    private final RentService rentService;

    @Operation(summary = "책 등록", description = "책을 등록합니다.")
    @Parameter(name = "createBookRequest", description = "책 등록 요청", required = true)
    @ApiResponse(responseCode = "200", description = "책 등록 성공")
    @PostMapping
    public String createBook(@RequestBody CreateBookRequest createBookRequest, HttpSession session)
    {
        createBookRequest.setUserId((Long) session.getAttribute("userId"));

        Long createBookId = bookService.createBook(createBookRequest);

        return "create Book Success : book Id : " + createBookId;
    }

    @Operation(summary = "책 조회", description = "책을 조회합니다.")
    @Parameter(name = "bookId", description = "책 ID", required = true)
    @ApiResponse(responseCode = "200", description = "책 조회 성공")
    @GetMapping("/{bookId}")
    public BookDto getBook(@PathVariable Long bookId)
    {
        return bookService.getBookDto(bookId);
    }

    @Operation(summary = "책 수정", description = "책을 수정합니다.")
    @Parameter(name = "updateBookRequest", description = "책 수정 요청", required = true)
    @ApiResponse(responseCode = "200", description = "책 수정 성공")
    @PutMapping
    public String updateBook(@RequestBody UpdateBookRequest updateBookRequest, HttpSession session)
    {
        updateBookRequest.setUserId((Long) session.getAttribute("userId"));

        Long bookId = bookService.updateBook(updateBookRequest);

        return "update Book Success. bookId : " + bookId;
    }

    @Operation(summary = "책 삭제", description = "책을 삭제합니다.")
    @Parameter(name = "bookId", description = "책 ID", required = true)
    @ApiResponse(responseCode = "200", description = "책 삭제 성공")
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId)
    {
        bookService.deleteBook(bookId);

        return "delete Book Success";
    }

    @Operation(summary = "책 대여", description = "책을 대여합니다.")
    @Parameter(name = "bookId", description = "대여할 책의 ID", required = true)
    @PostMapping("/{bookId}/rent")
    public String rentBook(@PathVariable Long bookId, HttpSession session)
    {
        //TODO: 책 대여 로직 추가
        /*
        * 대
        * */
        rentService.rentBook(bookId, (Long) session.getAttribute("userId"));
        return "rent book";
    }

    @Operation(summary = "책 반납", description = "책을 반납합니다.")
    @Parameter(name = "bookId", description = "반납할 책의 ID", required = true)
    @PostMapping("/{bookId}/return")
    public String returnBook(@PathVariable Long bookId, HttpSession session)
    {
        //TODO: 책 반납 로직 추가
        return "return book";
    }

    @Operation(summary = "책 반납일자 연장", description = "책의 반납일자를 연장합니다.")
    @Parameter(name = "bookId", description = "반납일자를 연장할 책의 ID", required = true)
    @PostMapping("/{bookId}/extend")
    public String extendBook(@PathVariable Long bookId, HttpSession session)
    {
        //TODO: 책 반납일자 연장 로직 추가
        return "extend book";
    }

}
