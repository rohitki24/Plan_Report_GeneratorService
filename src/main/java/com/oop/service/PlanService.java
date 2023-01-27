package com.oop.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.oop.request.Request;
import com.oop.response.Response;

public interface PlanService {
	
	public List<String> getPlanNames();

	public List<String> getPlanStatus();
	
	public List<Response> search(Request request);
	
	public void generateExcel(HttpServletResponse response)throws Exception;

	public void generatePdf(HttpServletResponse response) throws Exception;




}
