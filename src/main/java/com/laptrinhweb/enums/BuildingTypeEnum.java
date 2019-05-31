package com.laptrinhweb.enums;

public enum BuildingTypeEnum {
	
	TANG_TRET("Tang Tret"),
	NGUYEN_CAN("Nguyen Can"),
	NOI_THAT("Noi That");
	
	private String value;
	
	BuildingTypeEnum(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
