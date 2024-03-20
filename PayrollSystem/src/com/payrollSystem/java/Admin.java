package com.payrollSystem.java;



import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
//import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;




public class Admin {
	
	static Scanner scanner = new Scanner(System.in);
	
	
	protected static double salaryCalculation(Connection connection) {
		
		
		
		List<Map<String, Object>> EmpSalaryListHash = new ArrayList<>();
		HashMap<String, Object> empSalaryHash = new HashMap<>();
		List<Map<String, Object>> NetSalaryListHash = new ArrayList<>();
		HashMap<String, Object> NetSalaryHash = new HashMap<>();
//		double netSalry =0.0;
		Random random = new Random();
		String SalaryID = "ESD" + random.nextInt(999); 
		System.out.println("Enter Employee ID:");
        String empId = scanner.nextLine();
        System.out.println("Enter Month (1-12):");
        int month = scanner.nextInt();
        System.out.println("Enter Year:");
        int year = scanner.nextInt();
        System.out.print("Fromdate: ");
        LocalDate date1 = LocalDate.parse(scanner.next());
        System.out.println("ToDate: ");
        LocalDate date2 = LocalDate.parse(scanner.next());
        List<String> Department = getFunctions.getDept(connection, empId);
        List<String> designation = getFunctions.getDesignation(connection, empId);
        
        double basicpay = FindBasicPay(connection, Department.get(0), designation.get(0));
        double HRA = calcualteHRA(basicpay);
        double conveyance = 500.00;
        double pf = promientFundCalculation(basicpay);
        double esi = esiCalculation(basicpay);
        double loanAmount = getLoanAmount(connection, empId);
        double professionTax = CalculateProfessionalTax(basicpay);
        double tsd_it = calculateTSDIT(basicpay);
        double SalaryForLeave = getLeaveSalaryAmount(empId, connection);
        double NytShiftSalary = getFunctions.NytShiftSalary(connection, date1, date2, empId);
        
        
        double totalEarning = basicpay + HRA + conveyance + NytShiftSalary;
        double totalDeduction = pf + esi + loanAmount + professionTax + tsd_it+SalaryForLeave;
        double netSalary = totalEarning - totalDeduction;
        
        
        empSalaryHash.put("SalaryID", SalaryID);
        empSalaryHash.put("EmpID", empId);
        empSalaryHash.put("Month", month);
        empSalaryHash.put("Year",year);
        empSalaryHash.put("BPAndDA", basicpay);
        empSalaryHash.put("HRA", HRA);
        empSalaryHash.put("Conveyance", conveyance);
        empSalaryHash.put("ProvidentFund", pf);
        empSalaryHash.put("ESI", esi);
        empSalaryHash.put("Loan", loanAmount);
        empSalaryHash.put("ProfessionTax", professionTax);
        empSalaryHash.put("TDSOrIT", tsd_it);
        empSalaryHash.put("nytShiftSalary", NytShiftSalary);
        empSalaryHash.put("leaveSalary", SalaryForLeave);
        
        EmpSalaryListHash.add(empSalaryHash);
        
        WriteDetails.insertData(connection, "EmpSalary", EmpSalaryListHash);
        
       
		String NetSalaryID = "ENSD" + random.nextInt(999); 
        
        NetSalaryHash.put("TotalEarning", totalEarning);
        NetSalaryHash.put("TotalDeduction", totalDeduction);
        NetSalaryHash.put("NetSalaryID", NetSalaryID);
        NetSalaryHash.put("salaryID", SalaryID);
        NetSalaryHash.put("netSalary", netSalary);
        
        NetSalaryListHash.add(NetSalaryHash);
        
        WriteDetails.insertData(connection, "netSalaryDetails", NetSalaryListHash);
        
        viewEmpSalaryDetails(connection, empId);
        
        System.out.println("Send Email: 1.yes 2.No");
        int choice = scanner.nextInt();
        if (choice == 1) {
        	System.out.println("Enter the Employee mail id");
        	String mailId = scanner.next();
			pdfGenerator.generatePDF(connection, empId);
			EmailSend.MailSending(mailId);
        }
        return netSalary;
	}
	
	protected static double FindBasicPay(Connection connection,String Dept,String Design) {
		double basicpay = 0.0;
		 String bpQuery = "SELECT BasicPay FROM SalaryDetails WHERE Department = ? AND Designation = ?" ; 
		try (PreparedStatement preparedStatement = connection.prepareStatement(bpQuery)) {
			
        	preparedStatement.setString(1, Dept);
        	preparedStatement.setString(2, Design);
        	ResultSet resultSet = preparedStatement.executeQuery();
        	if (resultSet.next()) {
				basicpay = resultSet.getDouble("BasicPay");
			}
        	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return basicpay;	
	}
	
	protected static double calcualteHRA(double basicPay) {
		double HRA = 0.4;
		return HRA * basicPay;
		
	}
	
	protected static double promientFundCalculation(double basicPay ) {
		if(basicPay <= 15000) {
			return basicPay * 0.12;
		}
		return 0.0;
	}
	
	protected static double esiCalculation(double basicPay ) {
		if(basicPay <= 21000) {
			return basicPay * 0.75;
		}
		return 0.0;
	}
	
	protected static double getLoanAmount(Connection connection, String empId) {
	    double monthlyInterest = 0.0;
	    String loanQuery = "SELECT loanAmount, interest FROM LoanDetail WHERE EmpID = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(loanQuery)) {
	        preparedStatement.setString(1, empId);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        if (resultSet.next()) {
	            double loanAmount = resultSet.getDouble("loanAmount");
	            double interestRate = resultSet.getDouble("interest");
	          
	            monthlyInterest = (loanAmount * interestRate) / 12.0;
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
        monthlyInterest = Math.round(monthlyInterest * 100.0) / 100.0;

	    
//	    System.out.printf("%10.2f",monthlyInterest);
//	   DecimalFormat decimalFormat = new DecimalFormat("#.00");
//	    System.out.println(monthlyInterest);
	    return monthlyInterest;
	}
	
	protected static double calculateTSDIT(double basicPay) {
        double tsdIt = 0.0;
        double totalTaxableIncome = basicPay * 12;
        
        if (totalTaxableIncome <= 250000) {
            tsdIt = 0.0;
        } else if (totalTaxableIncome > 250000 && totalTaxableIncome <= 500000) {
            tsdIt = (totalTaxableIncome - 250000) * 0.05;
        } else if (totalTaxableIncome > 500000 && totalTaxableIncome <= 1000000) {
            tsdIt = 12500 + (totalTaxableIncome - 500000) * 0.2;
        } else {
            tsdIt = 112500 + (totalTaxableIncome - 1000000) * 0.3;
        }

        return tsdIt;
    }

	protected static double CalculateProfessionalTax(double basicPay) {
		double pt = 0.0;
		
		if (basicPay <=3500) {
			pt = 0.0;
		}
		else if(basicPay >3500 && basicPay <=5000 ) {
			pt = 22.5;
		}
		else if (basicPay > 5000 && basicPay <= 7500) {
			pt = 52.5;
		}
		else if(basicPay > 7500 && basicPay <= 10000) {
			pt = 115;
		}
		else if (basicPay > 10000 && basicPay <=12500) {
			pt = 171;
		}
		else {
			pt = 208;
		}
		return pt;
	}
		protected static double getLeaveSalaryAmount(String empID,Connection connection) {
			double leaveSalary = 0.0;
			List<Map<String, Object>> leavesalaryDetail = getFunctions.getLeaveSalaryDetails(connection, empID);
			for(Map<String, Object> leavesalaryMap : leavesalaryDetail) {
				leaveSalary = (double)leavesalaryMap.get("LeaveSalary");
			}
			return leaveSalary;
		}
		
		protected static String AddLeave(Connection connection) {
			
			HashMap<String, Object> leaveMap = new HashMap<>();
			
	//		List<String> keyValue = new ArrayList<String>(Arrays.asList("EmpID","LeaveID","TypeOfLeave","fromdate","toDate"));
			List<Map<String, Object>> LeaveMapList = new ArrayList<>();
			Random random = new Random();
	//		String filepath = "C:\\Users\\sakthi_n\\eclipse-workspace\\PayrollSystem\\src\\Json\\LeaveAdding.json";
	//		LocalDate fromdate,toDate;
			System.out.println("Adding Leave Details");
			System.out.print("EmployeeID:");
			String EmpID = scanner.next(); 
			String LeaveID = "LID" + random.nextInt(900);
			String TypeOfLeave = null;
			System.out.print("Type  of Leave : 1.CL 2.SL 3.LOP");
			int LeaveChoice = scanner.nextInt();
			if(LeaveChoice == 1) {
				TypeOfLeave = "CL";
			}
			else if (LeaveChoice == 2) {
				TypeOfLeave = "SL";
			}
			else if (LeaveChoice == 3) {
				TypeOfLeave = "LOP";
			}
			else {
				System.out.println("Enter a valid option");
			}
			System.out.print("From Date (YYYY-MM-DD) : ");
			String Date1 = scanner.next();
			LocalDate fromdate = LocalDate.parse(Date1);
			System.out.print("To Date (YYYY-MM-DD): ");
			String Date2 = scanner.next();
			LocalDate toDate = LocalDate.parse(Date2);
			
			leaveMap.put("EmpID", EmpID);
			leaveMap.put("LeaveID", LeaveID);
			leaveMap.put("TypeOfLeave", TypeOfLeave);
			leaveMap.put("FromDate", fromdate);
			leaveMap.put("ToDate", toDate);
			
	//		for(String key : keyValue ) {
	//			leaveMap.put(key,key);
	//		}
			LeaveMapList.add(leaveMap);
	//		System.out.println(LeaveMapList);
			
	//		jsonFileCreation.createJsonFile(filepath, leaveMap);
			WriteDetails.insertData(connection, "LeaveDetail", LeaveMapList);	
			LeavesalaryCalculation(connection,LeaveID, EmpID, fromdate, toDate);
			viewSpecificEmployeeLeave(connection, EmpID);
			return LeaveID;
		}
		
		protected static double LeavesalaryCalculation(Connection connection,String LeaveID,String empId,LocalDate fromdate,LocalDate todate) {
			
			Random random = new Random();
			double LeaveSalary = 10.0;
			long daysDifference = 0;
			String typeofLeave = null;
			String leaveSalaryId = "ELSD" + random.nextInt(900);
			List<Map<String, Object>> leaveDetailList = getFunctions.getEmployeeLeaveDetails(connection, empId, fromdate, todate);
			double basicSalary = getFunctions.getBasicSalary(connection, empId);
			double OneDaySalary = (basicSalary / 30);
			
			for(Map<String, Object> leave : leaveDetailList) {
				LocalDate leaveFromDate = (LocalDate) leave.get("FromDate");
				LocalDate leaveToDate = (LocalDate) leave.get("ToDate");
				typeofLeave = (String) leave.get("TypeOfLeave");
				LeaveID = (String) leave.get("LeaveID");
				daysDifference = ChronoUnit.DAYS.between(leaveFromDate, leaveToDate);
				if (leaveFromDate.getDayOfWeek() == DayOfWeek.SUNDAY || leaveToDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
					daysDifference--;
				}
				if (leaveToDate.getDayOfWeek() == DayOfWeek.SATURDAY|| leaveToDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
					daysDifference--;
				}
				
			}
			LeaveSalary =calculateLeaveSalary(typeofLeave,OneDaySalary , daysDifference);
//			System.out.println(LeaveSalary);
//			System.out.println(typeofLeave);
//			System.out.println(OneDaySalary);
			HashMap<String, Object> leaveSalaryMap = new HashMap<>();
			List<Map<String, Object>> leaveSalaryList = new ArrayList<>();
			
			leaveSalaryMap.put("LeaveSalaryid", leaveSalaryId);
			leaveSalaryMap.put("LeaveID",LeaveID);
			leaveSalaryMap.put("noofDays",daysDifference);
			leaveSalaryMap.put("LeaveSalary", LeaveSalary);
			
			leaveSalaryList.add(leaveSalaryMap);
			
			WriteDetails.insertData(connection, "LeaveSalary", leaveSalaryList);
			
//			System.out.println(leaveSalaryList);
			
			return LeaveSalary;
			
		}
		
		
		protected static double calculateLeaveSalary(String typeofLeave,double onedaySalary,long days) {
			
			double leaveSalary = 0.0;
			if (typeofLeave.equals("LOP")) {
				leaveSalary = days*onedaySalary;
			}
			else if (typeofLeave.equals("CL")) {
//				System.out.println("hi");
				leaveSalary = days * (onedaySalary/2);
			}
			else {
				leaveSalary = 0.0;
			}
//			System.out.println(leaveSalary);
			return leaveSalary;
		}
	
	protected static void addNytShift(Connection connection) {
		
		HashMap<String, Object> nytShiftMap = new HashMap<>();
		List<Map<String, Object>> nytShiftMapList = new ArrayList<>();
		Random random = new Random();
		System.out.println("Adding Night Shift Details");
		String nytShiftId = "NSID" + random.nextInt(900);
		System.out.print("Employee Id:");
		String empId = scanner.next();
		System.out.print("Nytshift Date:");
		LocalDate nytShiftDate = LocalDate.parse(scanner.next());
		System.out.println("Night Shift Amt: ");
		double NytShiftAmt = scanner.nextDouble();
		
		nytShiftMap.put("NytShiftId", nytShiftId);
		nytShiftMap.put("EmpID",empId );
		nytShiftMap.put("NytShiftDate", nytShiftDate);
		nytShiftMap.put("NytShiftAmt", NytShiftAmt);
		
		nytShiftMapList.add(nytShiftMap);
		
		WriteDetails.insertData(connection,"NytShiftDetail",nytShiftMapList);	
		viewSpecificEmpNytShift(connection, empId);
	}
	
	
	protected static void ViewAllSalaryDetails(Connection connection) {
//		System.out.println(ReadDetails.DbToHashMap(connection, "empsalary"));
		
		List<Map<String, Object>> salaryDetail =  ReadDetails.DbToHashMap(connection, "empsalary");
		if (salaryDetail == null || salaryDetail.isEmpty()) {
            System.out.println("No salaryDetail to print.");
            return;
        }

		System.out.println("+----------+------------+------------+------------+----------+----------+-----------+-----------+-----------+--------+--------+");
	    System.out.println("| SalaryID |  EmpId     |  BPAndDA   | Conveyance |   Loan   |  HRA     | Profession|    ESI    | Provident | TDSOrIT|  Month |");
	    System.out.println("|          |            |            |            |          |          |    Tax    |           |    Fund   |        |        |");
	    System.out.println("+----------+------------+------------+------------+----------+----------+-----------+-----------+-----------+--------+--------+");

	    for (Map<String, Object> detail : salaryDetail) {
	        System.out.printf("| %-8s | %-10s | %10.2f | %10.2f | %8.2f | %8.2f | %8.3f  | %8.2f  | %9.2f | %7.2f | %6d |\n",
	                detail.get("SalaryID"),
	                detail.get("EmpID"),
	                detail.get("BPAndDA"),
	                detail.get("Conveyance"),
	                detail.get("Loan"),
	                detail.get("HRA"),
	                detail.get("ProfessionTax"),
	                detail.get("ESI"),
	                detail.get("ProvidentFund"),
	                detail.get("TDSOrIT"),
	                detail.get("Month")
	        );
	    }
	    System.out.println("+----------+------------+------------+------------+----------+----------+-----------+-----------+-----------+--------+--------+");
    }
	
	protected static void viewEmpSalaryDetails(Connection connection,String EmpID) {
		
		List<Map<String, Object>> employeeDetail = ReadDetails.ReadSpecificEmpDetails(connection, "empsalary", EmpID);
		
		if (employeeDetail == null || employeeDetail.isEmpty()) {
            System.out.println("No details to print.");
            return;
        }

        System.out.println("+----------+------------+------------+----------+------------+----------+-----------+-----------+-----------+--------+--------+");
        System.out.println("| SalaryID |  EmpId     |  BPAndDA   | Conveyance |   Loan   |  HRA     | Profession|    ESI    | Provident | TDSOrIT|  Month |");
        System.out.println("|          |            |            |            |          |          |    Tax    |           |    Fund   |        |        |");
        System.out.println("+----------+------------+------------+----------+------------+----------+-----------+-----------+-----------+--------+--------+");

        for (Map<String, Object> detail : employeeDetail) {
            String empId = (String) detail.get("EmpID");
            if (empId.equals(EmpID)) {
                System.out.printf("| %-8s | %-10s | %10.2f | %10.2f | %8.2f | %8.2f | %8.2f | %8.2f  | %9.2f | %7.2f | %6d |\n",
                        detail.get("SalaryID"),
                        empId,
                        detail.get("BPAndDA"),
                        detail.get("Conveyance"),
                        detail.get("Loan"),
                        detail.get("HRA"),
                        detail.get("ProfessionTax"),
                        detail.get("ESI"),
                        detail.get("ProvidentFund"),
                        detail.get("TDSOrIT"),
                        detail.get("Month")
                );
            }
        }
        System.out.println("+----------+------------+------------+----------+------------+----------+-----------+-----------+-----------+--------+--------+");
	}
	
	protected static void viewSpecificMonthSalary(Connection connection,int Month,int year) {
		
		List<Map<String, Object>> employeeDetail = ReadDetails.ReadSpecificMonthSalaryDetails(connection, "empsalary", Month, year);
		
		if (employeeDetail == null || employeeDetail.isEmpty()) {
            System.out.println("No details to print.");
            return;
        }

        System.out.println("+----------+------------+------------+----------+------------+----------+-----------+-----------+-----------+--------+--------+");
        System.out.println("| SalaryID |  EmpId     |  BPAndDA   | Conveyance |   Loan   |  HRA     | Profession|    ESI    | Provident | TDSOrIT|  Month |");
        System.out.println("|          |            |            |            |          |          |    Tax    |           |    Fund   |        |        |");
        System.out.println("+----------+------------+------------+----------+------------+----------+-----------+-----------+-----------+--------+--------+");

        for (Map<String, Object> detail : employeeDetail) {
	        System.out.printf("| %-8s | %-10s | %10.2f | %10.2f | %8.2f | %8.2f | %8.3f  | %8.2f  | %9.2f | %7.2f | %6d |\n",
	                detail.get("SalaryID"),
	                detail.get("EmpId"),
	                detail.get("BPAndDA"),
	                detail.get("Conveyance"),
	                detail.get("Loan"),
	                detail.get("HRA"),
	                detail.get("ProfessionTax"),
	                detail.get("ESI"),
	                detail.get("ProvidentFund"),
	                detail.get("TDSOrIT"),
	                detail.get("Month")
	        );
	    }
	    System.out.println("+----------+------------+------------+------------+----------+----------+-----------+-----------+-----------+--------+--------+");
	}
	
	protected static void viewNytShiftDetails(Connection connection){
//		System.out.println(ReadDetails.DbToHashMap(connection, "nytshiftdetail"));
		
		List<Map<String, Object>> nytshiftDetails =ReadDetails.DbToHashMap(connection, "nytshiftdetail");
		
		if (nytshiftDetails == null || nytshiftDetails.isEmpty()) {
            System.out.println("No details to print.");
            return;
        }

        System.out.println("+-----------+-------+----------------+-------------+");
        System.out.println("| NytShiftId| EmpID | NytShiftDate   | NytShiftAmt |");
        System.out.println("+-----------+-------+----------------+-------------+");

        for (Map<String, Object> detail : nytshiftDetails) {
            System.out.printf("| %-10s| %-6s| %-15s| %10.2f  |\n",
                    detail.get("NytShiftId"),
                    detail.get("EmpID"),
                    detail.get("NytShiftDate"),
                    detail.get("NytShiftAmt"));
        }

        System.out.println("+-----------+-------+----------------+-------------+");
		
	}
	
	protected static void viewSpecificEmpNytShift(Connection connection,String EmpID) {
		List<Map<String, Object>> nytshiftDetails =ReadDetails.ReadSpecificEmpDetails(connection, "nytshiftdetail", EmpID);
		
		if (nytshiftDetails == null || nytshiftDetails.isEmpty()) {
            System.out.println("No details to print.");
            return;
        }

        System.out.println("+-----------+-------+----------------+-------------+");
        System.out.println("| NytShiftId| EmpID | NytShiftDate   | NytShiftAmt |");
        System.out.println("+-----------+-------+----------------+-------------+");

        for (Map<String, Object> detail : nytshiftDetails) {
        	String empId = (String) detail.get("EmpID");
        	if (empId.equals(EmpID)) {
        		
        		System.out.printf("| %-10s| %-6s| %-15s| %10.2f  |\n",
        				detail.get("NytShiftId"),
        				detail.get("EmpID"),
        				detail.get("NytShiftDate"),
        				detail.get("NytShiftAmt"));
        	}
        }

        System.out.println("+-----------+-------+----------------+-------------+");
		
	}
	
	protected static void viewSpecificPeriodNytShift(Connection connection,LocalDate fromDate,LocalDate toDate) {
		List<Map<String, Object>> nytshiftDetails =ReadDetails.ReadSpecificPeriodDetails(connection, "nytshiftdetail", fromDate, toDate);
		if (nytshiftDetails == null || nytshiftDetails.isEmpty()) {
            System.out.println("No details to print.");
            return;
        }

        System.out.println("+-----------+-------+----------------+-------------+");
        System.out.println("| NytShiftId| EmpID | NytShiftDate   | NytShiftAmt |");
        System.out.println("+-----------+-------+----------------+-------------+");

        for (Map<String, Object> detail : nytshiftDetails) {
            System.out.printf("| %-10s| %-6s| %-15s| %10.2f  |\n",
                    detail.get("NytShiftId"),
                    detail.get("EmpID"),
                    detail.get("NytShiftDate"),
                    detail.get("NytShiftAmt"));
        }

        System.out.println("+-----------+-------+----------------+-------------+");
	}
	
	protected static void ViewAllLeaveDetails(Connection connection) {
		List<Map<String, Object>> leaveDetails =ReadDetails.DbToHashMap(connection, "leavedetail");
		
		 if (leaveDetails == null || leaveDetails.isEmpty()) {
	            System.out.println("No leave details to print.");
	            return;
	        }

	        System.out.println("+----------+-------------+------------+-------------+-------+");
	        System.out.println("| LeaveID  | TypeOfLeave |  FromDate  |  ToDate     | EmpID |");
	        System.out.println("+----------+-------------+------------+-------------+-------+");

	        for (Map<String, Object> leave : leaveDetails) {
	        	
	            System.out.printf("| %-8s | %-11s | %-10s | %-8s | %-5s |\n",
	                    leave.get("LeaveID"),
	                    leave.get("TypeOfLeave"),
	                    leave.get("FromDate"),
	                    leave.get("ToDate"),
	                    leave.get("EmpID")
	            );
	        }

	        System.out.println("+----------+-------------+------------+-------------+-------+");
	}
	
	protected static void viewSpecificEmployeeLeave(Connection connection,String Empid) {
		
		List<Map<String, Object>> leaveDetails =ReadDetails.ReadSpecificEmpDetails(connection, "leavedetail",Empid);
		if (leaveDetails == null || leaveDetails.isEmpty()) {
            System.out.println("No leave details to print.");
            return;
        }

        System.out.println("+----------+-------------+------------+-------------+-------+");
        System.out.println("| LeaveID  | TypeOfLeave |  FromDate  |  ToDate     | EmpID |");
        System.out.println("+----------+-------------+------------+-------------+-------+");

        for (Map<String, Object> leave : leaveDetails) {
        	String empId = (String) leave.get("EmpID");
        	if (empId.equals(Empid)) {
            System.out.printf("| %-8s | %-11s | %-10s | %-8s | %-5s |\n",
                    leave.get("LeaveID"),
                    leave.get("TypeOfLeave"),
                    leave.get("FromDate"),
                    leave.get("ToDate"),
                    leave.get("EmpID")
            );
        	}
        }

        System.out.println("+----------+-------------+------------+-------------+-------+");
		
	}
	
	
	protected static void viewSpecificPeriodLeave(Connection connection,LocalDate fromDate,LocalDate toDate) {
		List<Map<String, Object>> leaveDetails =ReadDetails.ReadSpecificLeavePeriodDetails(connection, "leavedetail", fromDate, toDate);
		
		 if (leaveDetails == null || leaveDetails.isEmpty()) {
	            System.out.println("No leave details to print.");
	            return;
	        }

	        System.out.println("+----------+-------------+------------+-------------+-------+");
	        System.out.println("| LeaveID  | TypeOfLeave |  FromDate  |  ToDate     | EmpID |");
	        System.out.println("+----------+-------------+------------+-------------+-------+");

	        for (Map<String, Object> leave : leaveDetails) {
	        	
	            System.out.printf("| %-8s | %-11s | %-10s | %-8s | %-5s |\n",
	                    leave.get("LeaveID"),
	                    leave.get("TypeOfLeave"),
	                    leave.get("FromDate"),
	                    leave.get("ToDate"),
	                    leave.get("EmpID")
	            );
	        }

	        System.out.println("+----------+-------------+------------+-------------+-------+");
	}
	
	
	
	
	
	
	
}






	
	
//	protected static long totalnoofWeekDays(LocalDate fromDate,LocalDate toDate) {
//		
//		long noofDaysBt = ChronoUnit.DAYS.between(fromDate, toDate) + 1;
//		long weekends = noofDaysBt / 7 *2 ;
//		long noofExtraDays = Math.min(2, noofDaysBt%7);
//		
//		
//		if (fromDate.getDayOfWeek() == DayOfWeek.SUNDAY || toDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
//			noofExtraDays--;
//		}
//		if (fromDate.getDayOfWeek() == DayOfWeek.SATURDAY|| toDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
//			noofExtraDays--;
//		}
//		long noofWorkingDays = noofDaysBt - weekends - noofExtraDays;
//		return noofWorkingDays;
//	}
	
//}
