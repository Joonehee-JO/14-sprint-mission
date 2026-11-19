package com.sprint.mission.discodeit.global.security;

import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import com.sprint.mission.discodeit.global.security.exception.InvalidTokenException;
import com.sprint.mission.discodeit.user.security.DiscodeitUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtTokenProvider jwtTokenProvider;
  private final DiscodeitUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);      // 다음 필터 호출 (로그인)
      return;
    }

    String token = header.substring(7);

    try {
      UUID userId = jwtTokenProvider.getUserIdFromAccessToken(token);
      UserDetails userDetails = userDetailsService.loadUserByUserId(userId);

      // 인증완료 > 인증서 시큐리티 컨텍스트 홀더 내부 저장
      var authentication = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (InvalidTokenException e) {
      // 토큰이 유효하지 않은 경우
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    } catch (UsernameNotFoundException e) {
      // 사용자 못찾은 경우 > 401 예외
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);
  }
}
