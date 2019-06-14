package com.laptrinhweb.enums;

public enum DistrictEnum {
	
	QUAN_1("QUAN 1"),
	QUAN_2("QUAN 2"),
	QUAN_3("QUAN 3");
	
	private String value;
	
	DistrictEnum(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
