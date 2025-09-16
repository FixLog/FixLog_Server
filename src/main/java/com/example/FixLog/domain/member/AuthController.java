package com.example.FixLog.domain.member;

import com.example.FixLog.common.exception.Response;
import com.example.FixLog.domain.member.dto.LoginRequestDto;
import com.example.FixLog.domain.member.dto.LoginResponseDto;
import com.example.FixLog.common.response.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto result = authService.login(requestDto);
        return ResponseEntity.ok(Response.success("로그인 성공", result));
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<String>> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return ResponseEntity.ok(Response.success("로그아웃 완료. 클라이언트에서 토큰을 삭제하세요.", null));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(Response.fail(ErrorStatus.UNAUTHORIZED.getMessage()));
        }
    }

}
