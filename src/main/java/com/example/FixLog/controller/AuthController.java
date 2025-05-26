package com.example.FixLog.controller;

import com.example.FixLog.dto.Response;
import com.example.FixLog.dto.member.LoginRequestDto;
import com.example.FixLog.dto.member.LoginResponseDto;
import com.example.FixLog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto result = authService.login(requestDto);
        return ResponseEntity.ok(Response.success("로그인 성공", result));
    }
}
