package com.laptrinhjavaweb.entity;

import com.laptrinhweb.annotation.Column;
import com.laptrinhweb.annotation.Entity;

@Entity
public class UserRole {
	@Column(name="userId")
	private Long userId;
	
	@Column(name="roleid")
	private Long roleId;
}
