package com.payrollSystem.java;

import java.lang.invoke.StringConcatFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        int attempts = 3;
        try (Scanner scanner = new Scanner(System.in)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem", "root", "password");
            
//            List<Map<String, Object>> leaveList = ReadJson.JsonFileReader("C:\\Users\\sakthi_n\\eclipse-workspace\\PayrollSystem\\src\\Json\\loan.json", "LoanDetails");
//            List<Map<String, Object>> salaryList = ReadJson.JsonFileReader("C:\\Users\\sakthi_n\\eclipse-workspace\\PayrollSystem\\src\\Json\\salaryDetails.json", "SalaryDetails");
//            WriteDetails.insertData(conn, "loandetail", leaveList);/
//            WriteDetails.insertData(conn, "SalaryDetails", salaryList);
            
//            LocalDate fromDate = LocalDate.parse("2024-03-01");
//            LocalDate toDate = LocalDate.parse("2024-03-31");
//            
//            Admin.LeavesalaryCalculation(conn, "Mic521", fromDate, toDate);4
            
//            ReadDetails.ReadCompleteSalaryDetails(conn, "Sol123");
//            Employee.SalaryDetails(conn, "Sol123");
//            Employee.ViewLeaveDetails(conn, "Sol123");
//            Admin.calculateLeaveSalary("CL", 400.00, 2);
//            LocalDate fromDate = LocalDate.of(2024, 4, 1);
//            LocalDate toDate = LocalDate.of(2024, 4, 10);
//            Admin.LeavesalaryCalculation(conn, "LID366","Goo457" , fromDate, toDate);
//            getFunctions.getBasicSalary(conn,"Goo457" );
//            Admin.viewNytShiftDetails(conn);
//            ReadDetails.DbToHashMap(conn, "nytshiftdetail");
//            System.out.println(ReadDetails.DbToHashMap(conn, "nytshiftdetail"));
            
            
//            Admin.ViewAllLeaveDetails(conn);
            
//            Employee.ViewSpecificMonthSalary(conn, "Mic789", 3, 2024);
//            pdfGenerator.generatePDF(conn, "Sol123");
            EmailSend.MailSending("sakthi_n@solartis.com");

            System.out.println("\t\t\t\t\t\t--------------PAYROLL SYSTEM----------------");
            System.out.println("\t\t\t\t\t\t|--\t         1.Login\t\t---|");
            System.out.println("\t\t\t\t\t\t|--\t         2.Exit \t\t---|");
            System.out.println("\t\t\t\t\t\t--------------------------------------------");

            while (attempts > 0) {
                System.out.print("Enter your Choice : ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    boolean tryAgain = true;
                    while (tryAgain) {
                        System.out.println("\t\t\t\t\t\t   --------------------------------------");
                        System.out.println("\t\t\t\t\t\t   |             LOGIN PAGE             |");
                        System.out.println("\t\t\t\t\t\t   --------------------------------------");
                        System.out.print("\t\t\t\t\t\t    User Name:");
                        String userName = scanner.next();
                        System.out.print("\t\t\t\t\t\t    Password:");
                        String password = scanner.next();
                        System.out.println("\t\t\t\t\t\t    Designation:    \n      \t\t\t\t\t\t\t1.Admin  2.Employee");
                        System.out.print("\t\t\t\t\t\t    Enter your Designation:");
                        int DesignationChoice = scanner.nextInt();
                        boolean AdminCheck = (DesignationChoice == 1);
                        System.out.println("\t\t\t\t\t\t   --------------------------------------");
                        boolean loginCredential = CheckCredential(conn, userName, password, AdminCheck);

                        if (loginCredential) {
                        	tryAgain = false;
                        	List<String> EmployeeName = getFunctions.getEmployeeName(conn, userName);
                            System.out.println("Login successful.\n Welcome, " + EmployeeName.get(0) + "!");
                            if (AdminCheck) {
                            	System.out.println("Login as 1.Admin 2.Employee");
                            	int AdminChoice = scanner.nextInt();
                            	if (AdminChoice == 1) {
                            		while(true) {
                            			System.out.println("Enter your choice: ");
                            			System.out.println("1.Add \n2.View \n3.SendEmail \n3.exit");
                            			int Adminoptions = scanner.nextInt();
                            			if (Adminoptions == 1 ) {	
                            				System.out.println("1.Add Salary Calculation \n2.Add Night Shift Details \n3.Add LeaveDetails \n4.exit");
                            				int adminFunctionChoice = scanner.nextInt();
                            				if(adminFunctionChoice == 1) {
                            					Admin.salaryCalculation(conn);
                            				}
                            				else if(adminFunctionChoice == 2) {
                            					Admin.addNytShift(conn);
                            				}
                            				else if (adminFunctionChoice == 3) {
                            					Admin.AddLeave(conn);
                            				}
                            				else {
												System.exit(0);
											}
                            				
                            			}
                            			else if (Adminoptions == 2){
                            				System.out.println("1.View Salary details \n2.View Night Shift Details \n3.View LeaveDetails \n4.Exit");
                            				int viewChoice = scanner.nextInt();
                            				if (viewChoice == 1) {
                            					System.out.println("1.ViewALL \n2.View Specific EmpID \n3.View by Month & Year");
                            					System.out.print("Enter your choice");
                            					int salaryviewChoice = scanner.nextInt();
                            					if(salaryviewChoice == 1) {
                            						Admin.ViewAllSalaryDetails(conn);
                            					}
                            					else if (salaryviewChoice == 2) {
                            						System.out.print("Employee ID : ");
                            						String EmpId = scanner.next();
													Admin.viewEmpSalaryDetails(conn, EmpId);
												}
                            					else if (salaryviewChoice == 3) {
													System.out.print("Month(1-12):");
													int month = scanner.nextInt();
													System.out.print("Year:");
													int year = scanner.nextInt();
													Admin.viewSpecificMonthSalary(conn, month, year);
												}
                            					else {
                            						System.exit(0);
                            					}
											}
                            				else if(viewChoice == 2) {
                            					System.out.println("1.View All \n2.View Specific EmpID \n3.View by Specific period \n4.Exit");
                            					System.out.print("Enter your choice");
                            					int NytShiftviewchoice = scanner.nextInt();
                            					if(NytShiftviewchoice == 1) {
                            						Admin.viewNytShiftDetails(conn);
                            					}
                            					else if (NytShiftviewchoice == 2) {
                            						System.out.print("Employee ID : ");
                            						String EmpId = scanner.next();
													Admin.viewSpecificEmpNytShift(conn, EmpId);
												}
                            					else if (NytShiftviewchoice == 3) {
                            						System.out.print("Fromdate: ");
                            				        LocalDate date1 = LocalDate.parse(scanner.next());
                            				        System.out.println("ToDate: ");
                            				        LocalDate date2 = LocalDate.parse(scanner.next());
                            				        Admin.viewSpecificPeriodNytShift(conn, date1, date2);
												}
                            					else {
                            						System.exit(0);
                            					}
                            				}
                            				else if (viewChoice == 3) {
                              					System.out.println("1.View All \n2.View Specific EmpID \n3.View by Specific period");
                              					System.out.print("Enter your choice");
												int LeaveChoice = scanner.nextInt();
												if(LeaveChoice == 1) {
                            						Admin.ViewAllLeaveDetails(conn);
                            					}
                            					else if (LeaveChoice == 2) {
                            						System.out.print("Employee ID : ");
                            						String EmpId = scanner.next();
													Admin.viewSpecificEmployeeLeave(conn, EmpId);
												}
                            					else if (LeaveChoice == 3) {
                            						System.out.print("Fromdate: ");
                            				        LocalDate date1 = LocalDate.parse(scanner.next());
                            				        System.out.println("ToDate: ");
                            				        LocalDate date2 = LocalDate.parse(scanner.next());
                            				        Admin.viewSpecificPeriodLeave(conn, date1, date2);
												}			
											}
                            			}
                            			else if (Adminoptions == 3) {
											System.out.println("Enter the Employee Mail Id: ");
											String userMailId = scanner.next();
											EmailSend.MailSending(userMailId);
										}
                            			else {
                            				System.exit(0);
                            			}
                            		}
                            		}
                            	else if (AdminChoice == 2) {
                            		String empid = userName;
//                                  System.out.println(empid);
                                  while(true) {                                	
                                  	System.out.println("1.Salary Details \n2.Leave Details  \n3.Night Shift Details \n4.Generate PaySlip \n5.Exit");
                                  	int EmployeeChoice = scanner.nextInt();
                                  	if (EmployeeChoice == 1) {
                                  		System.out.println("1.View Complete Salary \n2.View Salary for Specific month");
                                  		System.out.print("Enter your choice");
                                  		int EmployeeSalaryChoice = scanner.nextInt();
                                  		if (EmployeeSalaryChoice == 1) {
                                  			Employee.SalaryDetails(conn, empid);
                                  		}
                                  		else if (EmployeeSalaryChoice == 2) {
                                  			System.out.print("Month(1-12):");
  											int month = scanner.nextInt();
  											System.out.print("Year:");
  											int year = scanner.nextInt();
  											Employee.ViewSpecificMonthSalary(conn, empid, month, year);
  										}
                                  	}
                                  	else if (EmployeeChoice == 2) {
  										Employee.ViewLeaveDetails(conn, empid);
  									}
                                  	else if (EmployeeChoice == 3) {
  										Employee.viewnytShiftDetail(conn, empid);
  									}
                                  	else if (EmployeeChoice == 4) {
										pdfGenerator.generatePDF(conn, empid);
									}
                                  	else {
  										System.exit(0);
  									}
                                  }
								}
                            	else {
                            		System.exit(0);
                            	}
                            }
                            else {
                                String empid = userName;
//                                System.out.println(empid);
                                while(true) {                                	
                                	System.out.println("1.Salary Details \n2.Leave Details  \n3.Night Shift Details \n4.Generate PaySlip \n5.Exit");
                                	int EmployeeChoice = scanner.nextInt();
                                	if (EmployeeChoice == 1) {
                                		System.out.println("1.View Complete Salary \n2.View Salary for Specific month");
                                		System.out.print("Enter your choice");
                                		int EmployeeSalaryChoice = scanner.nextInt();
                                		if (EmployeeSalaryChoice == 1) {
                                			Employee.SalaryDetails(conn, empid);
                                		}
                                		else if (EmployeeSalaryChoice == 2) {
                                			System.out.print("Month(1-12):");
											int month = scanner.nextInt();
											System.out.print("Year:");
											int year = scanner.nextInt();
											Employee.ViewSpecificMonthSalary(conn, empid, month, year);
										}
                                	}
                                	else if (EmployeeChoice == 2) {
										Employee.ViewLeaveDetails(conn, empid);
									}
                                	else if (EmployeeChoice == 3) {
										Employee.viewnytShiftDetail(conn, empid);
									}
                                	else if (EmployeeChoice == 4) {
										pdfGenerator.generatePDF(conn, empid);
									}
                                	else {
										System.exit(0);
									}
                                }
                            }
                            break;
                        } else {
                            attempts--;
                            System.out.println("Invalid username or password or Designation. Please try again.");
                            if (attempts > 0) {
                                System.out.println("You have " + attempts + " more attempts");
                                System.out.println("Options: 1. Try Again 2. Exit");
                                System.out.print("Enter your choice: ");
                                int option = scanner.nextInt();
                                if (option == 2) {
                                    System.exit(0);
                                    scanner.close();
                                }
                            } else {
                                System.out.println("All attempts are over");
                                tryAgain = false;
                            }
                        }
                    }
                } else {
                    System.exit(0);
                    scanner.close();
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Boolean CheckCredential(Connection connection, String UserName, String Password, boolean adminChoice) {
        List<Map<String, Object>> credentials = ReadDetails.DbToHashMap(connection, "LoginData");
        for (Map<String, Object> login : credentials) {
            String un = (String) login.get("EmployeeID");
            String pw = (String) login.get("EmpPassword");
            Integer intAdmin = (Integer) login.get("isAdmin");
            boolean isAdmin = intAdmin == 1;
            if (UserName.equals(un) && Password.equals(pw) && isAdmin == adminChoice) {
                return true;
            }
        }
        return false;
    }
  
}
