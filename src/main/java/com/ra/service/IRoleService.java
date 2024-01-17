package com.ra.service;

import com.ra.model.entity.Roles;

public interface IRoleService {
	Roles findByRoleName(String roleName);
}
