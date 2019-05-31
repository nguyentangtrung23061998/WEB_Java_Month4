package com.laptrinhjavaweb.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.DTO.UserDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingService;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.HttpUtil;


@WebServlet(urlPatterns = { "/api-admin-building" })
public class BuildingAPI extends HttpServlet {

	private static final long serialVersionUID = -2776194996498547100L;

	private IBuildingService buildingService;
	
	public BuildingAPI() {
		buildingService = new BuildingService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO building = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		List<BuildingEntity> buildingEntitiess = buildingService.searchID(building);
		//buildingService.searchID(building);
		System.out.println(building);
		mapper.writeValue(response.getOutputStream(), buildingEntitiess);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO buildingDTO = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		//logic
		buildingDTO = buildingService.save(buildingDTO);	
		mapper.writeValue(response.getOutputStream(), buildingDTO);
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO buildingDTO = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		buildingService.update(buildingDTO);
		mapper.writeValue(response.getOutputStream(), buildingDTO);
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO buildingDTO = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		buildingService.delete(buildingDTO);
		mapper.writeValue(response.getOutputStream(), "{}");
	}
}
