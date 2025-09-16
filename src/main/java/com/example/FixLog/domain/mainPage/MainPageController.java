package com.example.FixLog.domain.mainPage;

import com.example.FixLog.common.exception.Response;
import com.example.FixLog.domain.mainPage.dto.MainPageResponseDto;
import com.example.FixLog.domain.mainPage.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainPageController {
    private final MainPageService mainPageService;

    @GetMapping
    public Response<Object> mainPageView(@RequestParam(value = "sort", defaultValue = "0") int sort,
                                         @RequestParam(value = "size", defaultValue = "12") int size){
        MainPageResponseDto mainPageView = mainPageService.mainPageView(sort, size);
        return Response.success("메인페이지 불러오기 성공", mainPageView);
    }

    @GetMapping("/full")
    public Response<Object> mainPageFullView(@RequestParam(value = "sort", defaultValue = "0") int sort,
                                             @RequestParam(value = "page", defaultValue = "1") int page,
                                             @RequestParam(value = "size", defaultValue = "12") int size){
        MainPageResponseDto mainPageFullView = mainPageService.mainPageFullView(sort, page, size);
        return Response.success("메인페이지 전체보기 성공", mainPageFullView);
    }
}
