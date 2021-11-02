package com.berrygood.domain.badge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.berrygood.domain.badge.entity.UserToBadge;
@Repository
public interface UserToBadgeRepository extends JpaRepository<UserToBadge, Long>, JpaSpecificationExecutor<UserToBadge> {
	Optional<UserToBadge> findByUserNo(Long no); // 유저의 배지 조회
	
	

}
