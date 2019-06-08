package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.paging.Pageble;
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
	//	buildingResponsitory.delete(buildingEntity);
	}

	@Override
	public BuildingDTO searchID(Long id) {
		BuildingEntity buildingSearch = buildingResponsitory.searchID(id);
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingDTO buildingDTO = buildingConverter.convertToDTO(buildingSearch);
		return buildingDTO;
	}

	@Override
	public List<BuildingDTO> findAll(Map<String,Object> properties,Pageble pageble,Object...where) {
		BuildingConverter buildingConverter = new BuildingConverter();
		List<BuildingEntity> buildingEntity =  buildingResponsitory.findAll(properties, pageble,where);
		List<BuildingDTO> buildingDTOList = new ArrayList<>();
		for(int i =0 ;i<buildingEntity.size();i++) {
			BuildingDTO buildingDTO = buildingConverter.convertToDTO(buildingEntity.get(i));
			buildingDTOList.add(buildingDTO);
		}
		return buildingDTOList;
	}


}
