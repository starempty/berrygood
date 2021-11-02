package com.berrygood.domain.user.repository;

import com.berrygood.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자 삭제
    @Transactional
    int deleteByEmail(String email);

    // 사용자 1인 조회
    Optional<User> findByEmail(String email);
    
    // No로 조회
    Optional<User> findByNo(Long no);

    // 이메일 중복 체크
    Boolean existsByEmail(String email);

    // 닉네임 중복 체크
    Boolean existsByNickname(String nickname);
}
