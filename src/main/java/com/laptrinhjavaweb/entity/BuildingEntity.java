package com.laptrinhjavaweb.entity;

import java.sql.Timestamp;

import com.laptrinhweb.annotation.Column;
import com.laptrinhweb.annotation.Entity;
import com.laptrinhweb.annotation.Table;

@Entity
@Table(name="building")
public class BuildingEntity extends BaseEntity{
	@Column(name="name")
	private String name;
	
	@Column(name ="ward")
	private String ward;

	@Column(name ="street")
	private String street;
	
	@Column(name="structure")
	private String structure;
	
	@Column(name = "numberofbasement")
	private Integer numberOfBasement;
	
	@Column(name = "buildingarea")
	private Integer buildingArea; 
	
	@Column(name = "districtid")
	private String districtid;
	
	@Column(name = "costrent")
	private Integer costRent;
	
//	@Column(name = "cosdescription")
//	private String costDescription;

	@Column(name = "deposit")
	private String deposit;
	
	@Column(name = "servicecost")
	private String serviceCost;
	
	@Column(name = "carcost")
	private String carCost;
	
	@Column(name = "motorbikecost")
	private String motorBikeCost;
	
	@Column(name = "overtimecost")
	private String overTimeCost;
	
	@Column(name = "electricitycost")
	private String electricityCost;
	
	@Column(name = "timerent")
	private String timeRent;
	
	@Column(name = "timedecorator")
	private String timeDecorator;
	
	@Column(name = "managername")
	private String managername;
	
	@Column(name = "managerphone")
	private String managerphone;
	
	@Column(name = "payment")
	private String payment;
	
	@Column(name = "type")
	private String type;
	
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getElectricityCost() {
		return electricityCost;
	}
	public void setElectricityCost(String electricityCost) {
		this.electricityCost = electricityCost;
	}
	public String getMotorBikeCost() {
		return motorBikeCost;
	}
	public void setMotorBikeCost(String motorBikeCost) {
		this.motorBikeCost = motorBikeCost;
	}
	public String getOverTimeCost() {
		return overTimeCost;
	}
	public void setOverTimeCost(String overTimeCost) {
		this.overTimeCost = overTimeCost;
	}
	public String getTimeRent() {
		return timeRent;
	}
	public void setTimeRent(String timeRent) {
		this.timeRent = timeRent;
	}
	public String getTimeDecorator() {
		return timeDecorator;
	}
	public void setTimeDecorator(String timeDecorator) {
		this.timeDecorator = timeDecorator;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public String getManagerphone() {
		return managerphone;
	}
	public void setManagerphone(String managerphone) {
		this.managerphone = managerphone;
	}
	public String getName() {
		return name;
	}
	
	
	public String getDistrictid() {
		return districtid;
	}
	public void setDistrictid(String districtid) {
		this.districtid = districtid;
	}
	public Integer getCostRent() {
		return costRent;
	}
	public void setCostRent(Integer costRent) {
		this.costRent = costRent;
	}
//	public String getCostDescription() {
//		return costDescription;
//	}
//	public void setCostDescription(String costDescription) {
//		this.costDescription = costDescription;
//	}
	public String getServiceCost() {
		return serviceCost;
	}
	public void setServiceCost(String serviceCost) {
		this.serviceCost = serviceCost;
	}
	public String getCarCost() {
		return carCost;
	}
	public void setCarCost(String carCost) {
		this.carCost = carCost;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public Integer getBuildingArea() {
		return buildingArea;
	}
	public void setBuildingArea(Integer buildingArea) {
		this.buildingArea = buildingArea;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

}
