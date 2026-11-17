package com.sprint.mission.discodeit.user.web;

import com.sprint.mission.discodeit.user.application.UserApplicationService;
import com.sprint.mission.discodeit.user.web.dto.req.UserLoginRequestDTO;
import com.sprint.mission.discodeit.user.web.dto.res.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final UserApplicationService userApplicationService;

    @GetMapping("/csrf-token")
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {
      String token = csrfToken.getToken();
      log.info("CSRF 토큰 요청: {}", token);
      return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(
        @RequestBody UserLoginRequestDTO request
    ){
        UserResponseDTO response = userApplicationService.login(request);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }
}
