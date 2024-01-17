package com.ra.service.impl;

import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.JwtResponse;
import com.ra.model.entity.Roles;
import com.ra.model.entity.Users;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principal.UserPrincipal;
import com.ra.service.IRoleService;
import com.ra.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Override
	public JwtResponse handleLogin(UserLogin userLogin) {
		Authentication authentication;
		try {
			authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(),userLogin.getPassword()));
		} catch (AuthenticationException e) {
			throw new RuntimeException("tài khoản hoặc mật khẩu sai");
		}
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		if(!userPrincipal.getUsers().getStatus()) {
			throw new RuntimeException("your account is blocked");
		}
		return JwtResponse.builder()
				  .accessToken(jwtProvider.generateToken(userPrincipal))
				  .fullName(userPrincipal.getUsers().getFullName())
				  .username(userPrincipal.getUsername())
				  .status(userPrincipal.getUsers().getStatus())
				  .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
				  .build();
	}
	
	@Override
	public String handleRegister(UserRegister userRegister) {
		if(userRepository.existsByUsername(userRegister.getUsername())) {
			throw new RuntimeException("username is exists");
		}
		
		Set<Roles> roles = new HashSet<>();
		roles.add(roleService.findByRoleName("ROLE_USER"));
		
		Users users = Users.builder()
				  .fullName(userRegister.getFullName())
				  .username(userRegister.getUsername())
				  .password(passwordEncoder.encode(userRegister.getPassword()))
				  .status(true)
				  .roles(roles)
				  .build();
		userRepository.save(users);
		return "Success";
	}
}
