package com.example.FixLog.controller;

import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.UserIdDto;
import com.example.FixLog.dto.main.MainPageResponseDto;
import com.example.FixLog.service.MainPageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class MainPageController {
    private final MainPageService mainPageService;

    public MainPageController(MainPageService mainPageService){
        this.mainPageService = mainPageService;
    }

    @GetMapping
    public Response<Object> mainPageView(@RequestParam("sort") int sort, @RequestBody UserIdDto userIddto){
        MainPageResponseDto mainPageView = mainPageService.mainPageView(sort, userIddto);
        return Response.success("메인페이지 불러오기 성공", mainPageView);
    }

    @GetMapping("/full")
    public Response<Object> mainPageFullView(@RequestParam("sort") int sort, @RequestParam("page") int page,
                                             @RequestBody UserIdDto userIddto){
        MainPageResponseDto mainPageFullView = mainPageService.mainPageFullView(sort, page, userIddto);
        return Response.success("메인페이지 전체보기 성공", mainPageFullView);
    }
}
