package com.ra.service.impl;

import com.ra.model.entity.Roles;
import com.ra.repository.IRoleRepository;
import com.ra.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceIMPL implements IRoleService {
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Override
	public Roles findByRoleName(String roleName) {
		Roles roles = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("role not found"));
		return roles;
	}
}
