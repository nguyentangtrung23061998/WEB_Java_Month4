package com.laptrinhjavaweb.responsitory.impl;

import com.laptrinhjavaweb.DTO.UserDTO;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.service.IUserService;

public class UserResponsitory extends AbstractJDBC<UserEntity> implements IUserService{

	@Override
	public UserDTO save(UserDTO newUser) {
		// TODO Auto-generated method stub
		return null;
	}

}
