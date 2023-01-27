package com.oop.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.request.Request;
import com.oop.response.Response;
import com.oop.service.PlanService;

@RestController
public class PlanController {

	@Autowired
	private PlanService planService;

	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlanNames() {
		List<String> planNames = planService.getPlanNames();
		return new ResponseEntity<>(planNames, HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<List<String>> getPlanStatuses() {
		List<String> status = planService.getPlanStatus();
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	
	  @PostMapping("/search") public ResponseEntity<List<Response>>
	  search(@RequestBody Request request) { 
		  List<Response> response =planService.search(request); 
	  return new ResponseEntity<>(response,HttpStatus.OK);
	  }
	  
	  @GetMapping("/excel")
		public void excelReport(HttpServletResponse response) throws Exception{
			response.setContentType("application/octet-stream");
			
			String headerKey = "Content-Disposition";
			String headerValue = "attachment;filename=plan.xls";
			
			response.setHeader(headerKey, headerValue);
			
			planService.generateExcel(response);
		}
		
		@GetMapping("/pdf")
		public void pdfReport(HttpServletResponse response) throws Exception {
			response.setContentType("application/pdf");
			
			String headerKey = "Content-Disposition";
			String headerValue = "attachment;filename=plan.pdf";
			
			response.setHeader(headerKey, headerValue);
			
			planService.generatePdf(response);
		}
}