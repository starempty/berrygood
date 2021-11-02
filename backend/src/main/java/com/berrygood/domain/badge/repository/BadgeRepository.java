package com.berrygood.domain.badge.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.berrygood.domain.badge.entity.Badge;
import com.berrygood.domain.badge.type.BadgeType;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
	@Transactional
	int deleteByNo(Long no);
	
	Optional<Badge> findByNo(Long no);
	
	Badge findByBadgeType(BadgeType type);
}
