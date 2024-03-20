package com.payrollSystem.java;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.Signature;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class pdfGenerator {
	

	protected static void generatePDF(Connection connection,String EmpID) {
		try {
			
			String BPDa = null;
			String hra= null;
			String Conveyance= null;
			String ProvidentFund= null;
			String ESI= null;
			String Loan= null;
			String ProfessionTax= null;
			String TDSOrIT = null;
			
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("PaySlip.pdf"));;
			document.open();
			
			List<Map<String, Object>> salaryDetailsList = ReadDetails.ReadCompleteSalaryDetails(connection, EmpID);
			List<Map<String, Object>> employeeDetailsList = ReadDetails.ReadSpecificEmployeeDetails(connection, EmpID);
			connection.close();
			
			Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLDITALIC);
            headingFont.setColor(new BaseColor(22, 160, 133));
            Paragraph title = new Paragraph("PAY SLIP",headingFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
			
			
			for(Map<String, Object> empDetailMap : employeeDetailsList) {
				document.add(new Paragraph("Employee Name:" + empDetailMap.get("EmployeeName")));
				document.add(new Paragraph("Designation: " + empDetailMap.get("Designation")));
                document.add(new Paragraph("Department: " + empDetailMap.get("Department")));
			}
			for(Map<String, Object> salaryDetailMap : salaryDetailsList) {
				document.add(new Paragraph("Month : " + salaryDetailMap.get("Month")));
				document.add(new Paragraph("Year : " + salaryDetailMap.get("Year")));
			}
			
			PdfPTable salaryTable = new PdfPTable(4);
            salaryTable.setWidthPercentage(100);
//            salaryTable.addCell("Net Salary ID");
//            salaryTable.addCell("Total Earning");
//            salaryTable.addCell("Total Deduction");
//            salaryTable.addCell("Net Salary");
//            salaryTable.addCell("Month-Year");
//            for (Map<String, Object> salaryDetail : salaryDetailsList) {
//                salaryTable.addCell((String) salaryDetail.get("NetSalaryID"));
//                salaryTable.addCell(String.valueOf(salaryDetail.get("TotalEarning")));
//                salaryTable.addCell(String.valueOf(salaryDetail.get("TotalDeduction")));
//                salaryTable.addCell(String.valueOf(salaryDetail.get("netSalary")));
////                salaryTable.addCell(salaryDetail.get("Month") + "-" + salaryDetail.get("Year"));
//            }
			salaryTable.addCell("Earnings");
			salaryTable.addCell("");
			salaryTable.addCell("Deductions");
			salaryTable.addCell("");
			
			
//			for(Map<String, Object> salaryDetail : salaryDetailsList) {
////				BPDa = ((BigDecimal) salaryDetail.get("BPAndDA")).toString();
//				hra = ((BigDecimal) salaryDetail.get("HRA")).toString();
//				Conveyance = ((BigDecimal) salaryDetail.get("Conveyance")).toString();
//				ProvidentFund = ((BigDecimal) salaryDetail.get("ProvidentFund")).toString();
//				ESI = ((BigDecimal) salaryDetail.get("ESI")).toString();
//				Loan = ((BigDecimal) salaryDetail.get("Loan")).toString();
//				ProfessionTax = ((BigDecimal) salaryDetail.get("ProfessionTax")).toString();
//				TDSOrIT = ((BigDecimal) salaryDetail.get("TDSOrIT")).toString();
//
//			}
			
			salaryTable.addCell("Basic Pay & DA");
            BPDa = ((BigDecimal) salaryDetailsList.get(0).get("BPAndDA")).toString();
            salaryTable.addCell(BPDa);
            
            salaryTable.addCell("ProvidentFund");
            ProvidentFund = ((BigDecimal) salaryDetailsList.get(0).get("ProvidentFund")).toString();
            salaryTable.addCell(ProvidentFund);
            
            salaryTable.addCell("HRA");
            hra = ((BigDecimal) salaryDetailsList.get(0).get("HRA")).toString();
            salaryTable.addCell(hra);
			
            salaryTable.addCell("ESI");
            ESI = ((BigDecimal) salaryDetailsList.get(0).get("ESI")).toString();
            salaryTable.addCell(ESI);
            
            salaryTable.addCell("Conveyance");
            Conveyance = ((BigDecimal) salaryDetailsList.get(0).get("Conveyance")).toString();
            salaryTable.addCell(Conveyance);
            
            salaryTable.addCell("Loan");
            Loan = ((BigDecimal) salaryDetailsList.get(0).get("Loan")).toString();
            salaryTable.addCell(Loan);
            
            salaryTable.addCell("Night Shift Salary");
            String nytShiftSalary = ((BigDecimal) salaryDetailsList.get(0).get("nytShiftSalary")).toString();
            salaryTable.addCell(nytShiftSalary);
            
            salaryTable.addCell("ProfessionTax");
            ProfessionTax = ((BigDecimal) salaryDetailsList.get(0).get("ProfessionTax")).toString();
            salaryTable.addCell(ProfessionTax);
            
            salaryTable.addCell("");
            salaryTable.addCell("");
            
            salaryTable.addCell("TDSOrIT");
            TDSOrIT = ((BigDecimal) salaryDetailsList.get(0).get("TDSOrIT")).toString();
            salaryTable.addCell(TDSOrIT);
            
            salaryTable.addCell("");
            salaryTable.addCell("");
            
            salaryTable.addCell("leaveSalary");
            String leaveSalary = ((BigDecimal) salaryDetailsList.get(0).get("leaveSalary")).toString();
            salaryTable.addCell(leaveSalary);
            
            salaryTable.addCell("");
            salaryTable.addCell("");
            
            salaryTable.addCell("");
            salaryTable.addCell("");

            
            salaryTable.addCell("Total Addition");
            String totAdditionString = ((BigDecimal) salaryDetailsList.get(0).get("TotalEarning")).toString();
            salaryTable.addCell(totAdditionString);
            
            salaryTable.addCell("Total Deduction");
            String TotalDeduction = ((BigDecimal) salaryDetailsList.get(0).get("TotalDeduction")).toString();
            salaryTable.addCell(TotalDeduction);
            
            salaryTable.addCell("");
            salaryTable.addCell("");
            
            salaryTable.addCell("");
            salaryTable.addCell("");
            
            salaryTable.addCell("");
            salaryTable.addCell("");
            
            salaryTable.addCell("netSalary");
            String netSalary = ((BigDecimal) salaryDetailsList.get(0).get("netSalary")).toString();
            salaryTable.addCell(netSalary);
            
            document.add(new Paragraph("\n")); // Add some space
            document.add(salaryTable);

            
            Rectangle rect = new Rectangle(document.getPageSize());
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(1);
            rect.setBorderColor(BaseColor.BLACK);

            document.add(rect);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Check no ______________     Name of the Bank ____________________"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Date ________________"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Signature of Employee ________________          Director _______________"));
            // Close the document
            document.close();

            System.out.println("PDF created successfully.");
		} catch (DocumentException | FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
	}
}
