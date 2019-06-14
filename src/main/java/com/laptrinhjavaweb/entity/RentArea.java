package com.laptrinhjavaweb.entity;

import com.laptrinhweb.annotation.Column;
import com.laptrinhweb.annotation.Entity;
import com.laptrinhweb.annotation.Table;

@Entity
@Table(name="rentarea")
public class RentArea extends BaseEntity{
	@Column(name="value")
	private String value;
	
	@Column(name="buildingid")
	private Long buildingId;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}	
}
