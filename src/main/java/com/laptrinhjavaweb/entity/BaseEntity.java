package com.laptrinhjavaweb.entity;

import java.sql.Timestamp;

import com.laptrinhweb.annotation.Column;

public class BaseEntity {
	@Column(name="id")
	private Long id;
	
	@Column(name="createdby")
	private String createdBy;
	
	@Column(name="modifiedby")
	private String modifiedby;
	
	@Column(name="createddate")
	private Timestamp createdDate;
	
	@Column(name="modifieddate")
	private Timestamp modifieddate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}
	
	
	
}
