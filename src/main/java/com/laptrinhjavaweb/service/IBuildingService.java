package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO newBuilding);
	void update(BuildingDTO updateBuilding);
	void delete(BuildingDTO deleteBuilding);
	
	//n�ng cao
	List<BuildingEntity> searchID(BuildingDTO searchIDBuilding);
}
