package com.berrygood.domain.user.application;

import com.berrygood.domain.user.api.request.UserInfo;
import com.berrygood.domain.user.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserService {
    @Transactional
    void registerUser(User user);

    @Transactional
    int deleteUser(String email);

    @Transactional
    int updateUser(UserInfo user);

    Optional<User> getUserByEmail(String email);
    
    Optional<User> getUserByNo(Long no);

    Boolean existEmail(String email);

    Boolean existNickname(String nickName);
}
