package com.berrygood.domain.badge.application.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.badge.application.BadgeService;
import com.berrygood.domain.badge.entity.Badge;
import com.berrygood.domain.badge.entity.UserToBadge;
import com.berrygood.domain.badge.repository.BadgeRepository;
import com.berrygood.domain.badge.repository.UserToBadgeRepository;
import com.berrygood.domain.badge.type.BadgeType;
import com.berrygood.domain.donate.entity.DonateParticipant;
import com.berrygood.domain.donate.repository.DonateParticipantRepository;
import com.berrygood.domain.funding.entity.FundingParticipant;
import com.berrygood.domain.funding.repository.FundingParticipantRepository;
import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.user.repository.UserRepository;
import com.berrygood.domain.volunteer.entity.VolunteerParticipant;
import com.berrygood.domain.volunteer.repository.VolunteerParticipantRepository;
@Service
public class BadgeServiceImpl implements BadgeService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BadgeRepository badgeRepo;
	
	@Autowired
	UserToBadgeRepository U2BRepo;
	
	@Autowired
	FundingParticipantRepository FPRepo;
	
	@Autowired
	VolunteerParticipantRepository VPRepo;
	
	@Autowired
	DonateParticipantRepository DPRepo;
	
	@Override
	public void registerBadge(Badge badge) {
		badgeRepo.save(badge);
	}

	@Override
	public int deleteBadge(Long no) {
		return badgeRepo.deleteByNo(no);
	}

	@Override
	public Badge selectBadge(BadgeType type) {
		return badgeRepo.findByBadgeType(type);
	}
	

	@Override
	public HashMap<String, Long> selectUserToDonateBadge(Long no) {
		HashMap<String, Long> DPMap = new HashMap<String, Long>();
		if(userRepo.findByNo(no).isPresent()) {
			User user = userRepo.findByNo(no).get();
			Long DPSum = 0L;
			if(DPRepo.findAllByUser(user).isPresent()) {
				List<DonateParticipant> DP = DPRepo.findAllByUser(user).get();
				for (int i = 0; i < DP.size(); i++) {
					Long DPtmp = DP.get(i).getAmount();
					DPSum += DPtmp;
				}
				DPMap.put("size", (long) DP.size());
				DPMap.put("amount", DPSum);
				return DPMap;
			}
		}
		return DPMap;
	}

	@Override
	public HashMap<String, Long> selectUserToVolunteerBadge(Long no) {
		HashMap<String, Long> VPMap = new HashMap<String, Long>();
		if(userRepo.findById(no).isPresent()) {
			User user = userRepo.findById(no).get();
			Long VPSum = 0L;
			if(VPRepo.findAllByUser(user).isPresent()) {
				List<VolunteerParticipant> VP = VPRepo.findAllByUser(user).get();
				for (int i = 0; i < VP.size(); i++) {
					Long VPtmp = VP.get(i).getAmount();
					VPSum += VPtmp;
				}
				VPMap.put("size", (long) VP.size());
				VPMap.put("amount", VPSum);
				return VPMap;
			}
		}
		return VPMap;
	}

	@Override
	public HashMap<String, Long> selectUserToFundingBadge(Long no) {
		HashMap<String, Long> FPMap = new HashMap<String, Long>(); 
		if(userRepo.findByNo(no).isPresent()) {
			User user = userRepo.findByNo(no).get();
			Long FPSum = 0L;
			if(FPRepo.findAllByUser(user).isPresent()) {
				List<FundingParticipant> FP = FPRepo.findAllByUser(user).get();
				for (int i = 0; i < FP.size(); i++) {
					Long FPtmp = FP.get(i).getAmount();
					FPSum += FPtmp;
				}
				FPMap.put("size", (long) FP.size());
				FPMap.put("amount", FPSum);
				return FPMap;
			}
		}
		return FPMap;
	}

	@Override
	public int updateUserToDonateBadge(Long no, UserToBadge badge) {
		Optional<UserToBadge> optionalU2B = U2BRepo.findById(no);
		if(optionalU2B.isPresent()) {
			UserToBadge u2b = optionalU2B.get();
			u2b.setBadge(badge.getBadge());
			U2BRepo.save(u2b);
			return 1;
		}
		return 0;
	}

	@Override
	public int updateUserToVolunteerBadge(Long no, UserToBadge badge) {
		Optional<UserToBadge> optionalU2B = U2BRepo.findById(no);
		if(optionalU2B.isPresent()) {
			UserToBadge u2b = optionalU2B.get();
			u2b.setBadge(badge.getBadge());
			U2BRepo.save(u2b);
			return 1;
		}
		return 0;
	}

	@Override
	public int updateUserToFundingBadge(Long no, UserToBadge badge) {
		Optional<UserToBadge> optionalU2B = U2BRepo.findById(no);
		if(optionalU2B.isPresent()) {
			UserToBadge u2b = optionalU2B.get();
			u2b.setBadge(badge.getBadge());
			U2BRepo.save(u2b);
			return 1;
		}
		return 0;
	}



}

