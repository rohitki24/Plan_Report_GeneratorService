package com.oop.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.oop.repository.PlanRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.oop.entity.Plan;
import com.oop.request.Request;
import com.oop.response.Response;

@Service
public class PlanServiceImpl implements PlanService{
	@Autowired
	private PlanRepository planRepo;

	

	  public List<String> getPlanNames() { 
		  return planRepo.findPlanNames();
		  }
	 
	 public List<String> getPlanStatus() { 
		 return planRepo.findPlanStatus(); 
		 }
	 
	public List<Response> search(Request request) {

		
		Plan plan = new Plan();

		String planName = request.getPlanName();
		if (planName != null && !planName.equals("")) {
			plan.setPlanName(planName);
		}

		String planStatus = request.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			plan.setPlanStatus(planStatus);
		}
		
		List<Response> response = new ArrayList<>();
		Example<Plan> of = Example.of(plan);

		List<Plan> plan2 = planRepo.findAll(of);
		for(Plan p:plan2)
		{
			Response sresponse = new Response();
			sresponse.setName(p.getName());
			sresponse.setMobile(p.getMobile());
			sresponse.setEmail(p.getEmail());
			sresponse.setGender(p.getGender());
			sresponse.setSsn(p.getSsn());
			response.add(sresponse);
		}
		return response;
	}
	
	public void generateExcel(HttpServletResponse response) throws Exception {
		List<Plan> entities = planRepo.findAll();

		HSSFWorkbook workBook = new HSSFWorkbook();

		HSSFSheet sheet = workBook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Id");
		headerRow.createCell(1).setCellValue("Name     ");
		headerRow.createCell(2).setCellValue("Email");
		headerRow.createCell(3).setCellValue("Mobile");
		headerRow.createCell(4).setCellValue("Gender");
		headerRow.createCell(5).setCellValue("SSN");
		headerRow.createCell(6).setCellValue("planName");
		headerRow.createCell(7).setCellValue("planStatus");
		
		int i = 1;
		
		for(Plan entity : entities) {
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(String.valueOf(entity.getId()));
			dataRow.createCell(1).setCellValue(entity.getName());
			dataRow.createCell(2).setCellValue(entity.getEmail());
			dataRow.createCell(3).setCellValue(entity.getMobile());
			dataRow.createCell(4).setCellValue(String.valueOf(entity.getGender()));
			dataRow.createCell(5).setCellValue(entity.getSsn());
			dataRow.createCell(6).setCellValue(entity.getPlanName());
			dataRow.createCell(7).setCellValue(entity.getPlanStatus());
			
			
			i++;
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();

	}

	@Override
	public void generatePdf(HttpServletResponse response) throws Exception {
		List<Plan> entities = planRepo.findAll();

		Document document = new Document(PageSize.A4);

		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(CMYKColor.BLUE);

		Paragraph p = new Paragraph("Plan Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 4.5f, 5.5f, 3.5f, 3.0f,3.0f, 4.0f, 4.0f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(CMYKColor.BLUE);
		cell.setPadding(5);

		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(CMYKColor.WHITE);
		
		cell.setPhrase(new Phrase("Id", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("E-mail", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Phno", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("PlanName", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("PlanStatus", font));
		table.addCell(cell);

		for (Plan entity : entities) {
			table.addCell(String.valueOf(entity.getId()));
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobile()));
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getSsn()));
			table.addCell(entity.getPlanName());	
			table.addCell(entity.getPlanStatus());
		}
		
		document.add(table);
		
		document.close();
	}

}
