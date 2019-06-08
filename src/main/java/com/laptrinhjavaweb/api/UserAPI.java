package com.laptrinhjavaweb.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.DTO.BuildingDTO;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.HttpUtil;


@WebServlet(urlPatterns = { "/manager" })
public class UserAPI extends HttpServlet {

	private static final long serialVersionUID = -2776194996498547100L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
	//	BuildingDTO building = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
	//	mapper.writeValue(response.getOutputStream(), building);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO building = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		//logic
		
		
		mapper.writeValue(response.getOutputStream(), building);
	}
}
