package com.laptrinhjavaweb.responsitory.impl;

import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.reponsitory.IBuildingResponsitory;

public class BuildingResponsitory extends AbstractJDBC<BuildingEntity> implements IBuildingResponsitory{
//
//	@Override
//	public Long Insert(BuildingEntity buildingEntity) {
//		StringBuilder sql = new StringBuilder("INSERT INTO building(name,numberofbasement,buildingarea,district,ward,street,structure,costrent,costdesciption,servicecost)");
//		sql.append(",carcost,motorbikecost,overtimecost,electricitycost,deposit,payment,timerent,timedecorator,managername,managerphone,createddate");
//		sql.append(",modifieddate,createdby,modifiedby");
//		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//		return this.insert(sql.toString(), buildingEntity.getName(),buildingEntity.getNumberOfBasement(),buildingEntity.getBuildingArea(),buildingEntity.getDictrict(),
//							buildingEntity.getWard(),buildingEntity.getStreet(),buildingEntity.getStructure(),buildingEntity.getCostRent(),
//							buildingEntity.getCostDescription(),buildingEntity.getCarCost(),buildingEntity.getMotorBikeCost(),buildingEntity.getOverTimeCost(),
//							buildingEntity.getElectricityCost(),buildingEntity.getDeposit(),buildingEntity.getPayment(),buildingEntity.getTimeRent(),
//							buildingEntity.getTimeDecorator(),buildingEntity.getManagername(),buildingEntity.getManagerphone(),buildingEntity.getCreatedDate(),
//							buildingEntity.getModifiedDate(),buildingEntity.getCreatedBy(),buildingEntity.getModifyBy());
//	}

}
