package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.DTO.UserDTO;
import com.laptrinhjavaweb.converter.UserConverter;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.service.IUserService;

public class UserService implements IUserService {

	@Override
	public UserDTO save(UserDTO newUser) {
		// TODO Auto-generated method stub
		UserConverter userConverter = new UserConverter();
		UserEntity userEntity =userConverter.convertToEntity(newUser);
		return null;
	}

}
