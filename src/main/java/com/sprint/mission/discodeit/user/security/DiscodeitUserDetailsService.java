package com.sprint.mission.discodeit.user.security;

import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DiscodeitUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        SecurityUserInfo userInfo = SecurityUserInfo.init(user.getId(), user.getName(), user.getRole());
        return DiscodeitUserDetails.init(userInfo, user.getUserPassword());
    }

    // 사용자 아이디 말고 pk 값으로 인증 처리 해야함
    public UserDetails loadUserByUserId(UUID userId){
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

      SecurityUserInfo userInfo = SecurityUserInfo.init(userId, user.getName(), user.getRole());
      return DiscodeitUserDetails.init(userInfo, user.getUserPassword());
    }
}
