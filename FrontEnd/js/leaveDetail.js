var employeeList = [
    { empid: "001", loanAmount: 5000, leaveSalary: 1000, nytShiftSalary: 200, basicPay: 45000 },
    { empid: "002", loanAmount: 3000, leaveSalary: 800, nytShiftSalary: 150, basicPay: 38000 },
    { empid: "003", loanAmount: 4000, leaveSalary: 1200, nytShiftSalary: 250, basicPay: 43000 }
];

function calculateLeaveSalary(){
    var empId = document.getElementById("empId").value;
    var fromDate = document.getElementById("fromDate").value;
    var toDate = document.getElementById("toDate").value;
    var leaveType =  document.getElementById("leaveType").value;

    var employee = employeeList.find(emp => emp.empid === empId);
    if (!employee) {
        alert("Employee not found!");
        return;
    }

    var leaveSalary = calculateLeave(employee, fromDate, toDate,leaveType);

    document.getElementById("leavesalary").innerHTML = leaveSalary;
}

function calculateLeave(employee, fromDate, toDate,leaveType) {
    var daysDifference = calculateDaysDifference(fromDate, toDate);
    var oneDaySalary = employee.basicPay / 30;
    var leaveSalary = calculateLeaveSalaryByType(leaveType, oneDaySalary, daysDifference);
     displayLeaveDetails(employee, fromDate, toDate, leaveType, leaveSalary);
    return leaveSalary;
}

function displayLeaveDetails(employee, fromDate, toDate, leaveType, leaveSalary) {
    var oneDaySalary = employee.basicPay / 30;
    var daysDifference = calculateDaysDifference(fromDate, toDate);

    var tableBody = document.getElementById("leaveDetails");
    var newRow = tableBody.insertRow();

    newRow.insertCell().textContent = employee.empid;
    newRow.insertCell().textContent = fromDate;
    newRow.insertCell().textContent = toDate;
    newRow.insertCell().textContent = leaveType;
    newRow.insertCell().textContent = leaveSalary.toFixed(2);
    newRow.insertCell().textContent = daysDifference;
    newRow.insertCell().textContent = oneDaySalary.toFixed(2);
}

function calculateDaysDifference(fromDate, toDate) {
    var oneDay = 24 * 60 * 60 * 1000; 
    var firstDate = new Date(fromDate);
    var secondDate = new Date(toDate);
    var diffDays = Math.round(Math.abs((firstDate - secondDate) / oneDay));
    return diffDays;
}   

function calculateLeaveSalaryByType(leaveType, oneDaySalary, days) {
    var leaveSalary = 0;
    switch (leaveType) {
        case "CL":
            leaveSalary = days * (oneDaySalary / 2);
            console.log(leaveSalary);
            break;
        case "SL":
            leaveSalary = days * oneDaySalary;
            break;
        case "LOP":
            leaveSalary = days * oneDaySalary;
            break;
        default:
            leaveSalary = 0;
            break;
    }
    return leaveSalary;
}