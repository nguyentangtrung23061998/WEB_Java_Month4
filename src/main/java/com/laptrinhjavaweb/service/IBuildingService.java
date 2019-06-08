package com.laptrinhjavaweb.service;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.paging.Pageble;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO newBuilding);
	void update(BuildingDTO updateBuilding);
	void delete(BuildingDTO deleteBuilding);
	
	//nâng cao
	BuildingDTO searchID(Long id);
	List<BuildingDTO> findAll(Map<String,Object> properties,Pageble pageble,Object...where);
}
