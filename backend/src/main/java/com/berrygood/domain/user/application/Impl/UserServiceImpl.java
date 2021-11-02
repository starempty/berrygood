package com.berrygood.domain.user.application.Impl;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.user.api.request.UserInfo;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void registerUser(User user) { userRepository.save(user); }

    // 사용자 정보 수정
    @Override
    public int updateUser(UserInfo userInfo) {
        Optional<User> optionalUser = userRepository.findByEmail(userInfo.getEmail());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            if(StringUtils.isNotBlank(userInfo.getPhone())) {
                user.setPhone(userInfo.getPhone());
            }
            if(StringUtils.isNotBlank(userInfo.getAddress())) {
                user.setAddress(userInfo.getAddress());
            }
            if(StringUtils.isNotBlank(userInfo.getNickname())) {
                user.setNickname(userInfo.getNickname());
            }

            userRepository.save(user);
            return 1;
        }
        return 0;
    }

    // 사용자 삭제
    @Override
    public int deleteUser(String email) {
        return userRepository.deleteByEmail(email);
    }

    // 사용자 1인 조회
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByNo(Long no) {
    	return userRepository.findByNo(no);
    }
    
    // 이메일 중복 체크
    @Override
    public Boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 닉네임 중복 체크
    @Override
    public Boolean existNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

}
