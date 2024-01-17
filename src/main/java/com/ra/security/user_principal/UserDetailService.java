package com.ra.security.user_principal;

import com.ra.model.entity.Users;
import com.ra.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> optionalUsers = userRepository.findByUsername(username);
		if(optionalUsers.isPresent()) {
			Users users = optionalUsers.get();
			UserPrincipal userPrincipal = UserPrincipal.builder()
					  .users(users)
					  .authorities(users.getRoles()
								 .stream()
								 .map(item -> new SimpleGrantedAuthority(item.getRoleName()))
								 .collect(Collectors.toSet()))
					  .build();
			return userPrincipal;
		}
		throw new RuntimeException("role not found");
	}
}
