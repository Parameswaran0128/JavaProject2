package com.payrollSystem.java;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Employee {

	protected static void SalaryDetails(Connection connection,String EmpID){
		
		List<Map<String, Object>> netSalaryDetails = ReadDetails.ReadCompleteSalaryDetails(connection,EmpID);
		if (netSalaryDetails == null || netSalaryDetails.isEmpty()) {
            System.out.println("No net salary details to print.");
            return;
        }

        System.out.println("+------------+-----------+------------+------------+----------+------------+-------+--------------+-----------+----------+----------+-----------+");
        System.out.println("| NetSalaryID| SalaryID  | EmpID      | BPAndDA    | HRA      | Conveyance | Loan  | ProfessionTax| Provident | ESI      | TDSOrIT  | netSalary |");
        System.out.println("+------------+-----------+------------+------------+----------+------------+-------+--------------+-----------+----------+----------+-----------+");

        for (Map<String, Object> netSalary : netSalaryDetails) {
            System.out.printf("| %-11s | %-9s | %-10s | %10.2f | %8.2f | %10.2f | %5.2f | %12.2f | %9.2f | %8.2f | %8.2f | %10.2f |\n",
                    netSalary.get("NetSalaryID"),
                    netSalary.get("SalaryID"),
                    EmpID,
                    netSalary.get("BPAndDA"),
                    netSalary.get("HRA"),
                    netSalary.get("Conveyance"),
                    netSalary.get("Loan"),
                    netSalary.get("ProfessionTax"),
                    netSalary.get("ProvidentFund"),
                    netSalary.get("ESI"),
                    netSalary.get("TDSOrIT"),
                    netSalary.get("netSalary")
            );
        }


        System.out.println("+------------+-----------+------------+------------+----------+------------+-------+--------------+-----------+----------+----------+-----------+");
	}
	
	protected static void ViewSpecificMonthSalary(Connection connection,String EmpID,int month,int year) {
		List<Map<String, Object>> netSalaryDetails = ReadDetails.ReadMonthSalaryDetails(connection, EmpID, month, year);
		if (netSalaryDetails == null || netSalaryDetails.isEmpty()) {
            System.out.println("No net salary details to print.");
            return;
        }

        System.out.println("+------------+-----------+------------+------------+----------+------------+-------+--------------+-----------+----------+----------+-----------+");
        System.out.println("| NetSalaryID| SalaryID  | EmpID      | BPAndDA    | HRA      | Conveyance | Loan  | ProfessionTax| Provident | ESI      | TDSOrIT  | netSalary |");
        System.out.println("+------------+-----------+------------+------------+----------+------------+-------+--------------+-----------+----------+----------+-----------+");

        for (Map<String, Object> netSalary : netSalaryDetails) {
            System.out.printf("| %-11s | %-9s | %-10s | %10.2f | %8.2f | %10.2f | %5.2f | %12.2f | %9.2f | %8.2f | %8.2f | %10.2f |\n",
                    netSalary.get("NetSalaryID"),
                    netSalary.get("SalaryID"),
                    EmpID,
                    netSalary.get("BPAndDA"),
                    netSalary.get("HRA"),
                    netSalary.get("Conveyance"),
                    netSalary.get("Loan"),
                    netSalary.get("ProfessionTax"),
                    netSalary.get("ProvidentFund"),
                    netSalary.get("ESI"),
                    netSalary.get("TDSOrIT"),
                    netSalary.get("netSalary")
            );
        }


        System.out.println("+------------+-----------+------------+------------+----------+------------+-------+--------------+-----------+----------+----------+-----------+");
	}
	
	protected static void ViewLeaveDetails(Connection connection,String EmpId) {
		
		List<Map<String, Object>> dataList = ReadDetails.ReadLeaveDetails(connection, EmpId);
		if (dataList == null || dataList.isEmpty()) {
            System.out.println("You have taken no leave");
            return;
        }


        System.out.println("+------------+-------------+-----------+------------+------------+------------+---------------+");
        System.out.println("|  LeaveID   |  EmpID      | TypeOfLeave|  FromDate  |   ToDate   | LeaveSalary| LeaveSalaryid |");
        System.out.println("+------------+-------------+-----------+------------+------------+------------+---------------+");


        for (Map<String, Object> data : dataList) {
            System.out.printf("| %-10s | %-11s | %-10s | %-10s | %-10s | %-10s | %-13s |\n",
                    data.get("LeaveID"),
                    data.get("EmpID"),
                    data.get("TypeOfLeave"),
                    data.get("FromDate"),
                    data.get("ToDate"),
                    data.get("LeaveSalary"),
                    data.get("LeaveSalaryid")
            );
        }
        System.out.println("+------------+-------------+-----------+------------+------------+------------+---------------+");
	}
	
	
	protected static void viewnytShiftDetail(Connection connection,String EmpID){
		List<Map<String, Object>> dataList = ReadDetails.ReadSpecificEmpDetails(connection, "nytshiftdetail", EmpID);
		if (dataList == null || dataList.isEmpty()) {
            System.out.println("No data to print.");
            return;
        }

        System.out.println("+------------+-------------+---------------+-------------+");
        System.out.println("| NytShiftId |   EmpID     | NytShiftDate  | NytShiftAmt |");
        System.out.println("+------------+-------------+---------------+-------------+");

        for (Map<String, Object> data : dataList) {
            System.out.printf("| %-10s | %-11s | %-13s | %-11s |\n",
                    data.get("NytShiftId"),
                    data.get("EmpID"),
                    data.get("NytShiftDate"),
                    data.get("NytShiftAmt")
            );
        }


        System.out.println("+------------+-------------+---------------+-------------+");
		
	}
}
