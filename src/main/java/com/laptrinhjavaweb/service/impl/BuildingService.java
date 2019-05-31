package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.reponsitory.IBuildingResponsitory;
import com.laptrinhjavaweb.responsitory.impl.BuildingResponsitory;
import com.laptrinhjavaweb.service.IBuildingService;

public class BuildingService implements	IBuildingService{
	
	private IBuildingResponsitory buildingResponsitory;
	
	public BuildingService() {
		buildingResponsitory = new BuildingResponsitory();
	}

	@Override
	public BuildingDTO save(BuildingDTO buildingDTO) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingDTO);
		buildingEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		Long id = buildingResponsitory.insert(buildingEntity);
		return null;
	}

	@Override
	public void update(BuildingDTO updateBuilding) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(updateBuilding);
		buildingResponsitory.update(buildingEntity);
	}

	@Override
	public void delete(BuildingDTO deleteBuilding) {
		// TODO Auto-generated method stub
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(deleteBuilding);
		buildingResponsitory.delete(buildingEntity);
	}

	@Override
	public List<BuildingEntity> searchID(BuildingDTO searchIDBuilding) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(searchIDBuilding);
		return buildingResponsitory.searchID(buildingEntity);
	}

}
