package com.berrygood.global.auth;

import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import com.berrygood.global.common.BaseUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BerryGoodUserDetailService implements UserDetailsService {
    final
    UserService userService;

    final
    CompService compService;

    public BerryGoodUserDetailService(UserService userService, CompService compService) {
        this.userService = userService;
        this.compService = compService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BerryGoodUserDetails userDetails;
        if(userService.getUserByEmail(email).isPresent()) {
            BaseUserEntity user = userService.getUserByEmail(email).get();
            userDetails = new BerryGoodUserDetails(user);
            return userDetails;
        } else if(compService.getCompByEmail(email).isPresent()) {
            BaseUserEntity user = compService.getCompByEmail(email).get();
            userDetails = new BerryGoodUserDetails(user);
            return userDetails;
        }
        return null;
    }
}