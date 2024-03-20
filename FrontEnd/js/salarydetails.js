var employeeList = [
    { empid: "001", loanAmount: 5000, leaveSalary: 1000, nytShiftSalary: 200, basicPay: 45000 },
    { empid: "002", loanAmount: 3000, leaveSalary: 800, nytShiftSalary: 150, basicPay: 38000 },
    { empid: "003", loanAmount: 4000, leaveSalary: 1200, nytShiftSalary: 250, basicPay: 43000 }
];

function SalaryCalculation(){
    var empId = document.getElementById("employeeId").value;
    var month = document.getElementById("month").value;
    var year = document.getElementById("year").value;

    var employee = employeeList.find(function(emp) {
        return emp.empid === empId;
    });

    if (employee) {
        // Use employee details to calculate other components
        var HRA = calculateHRA(employee.basicPay);
        var conveyance = 500.00;
        var pf = calculateProvidentFund(employee.basicPay);
        var esi = calculateESI(employee.basicPay);
        var professionTax = calculateProfessionalTax(employee.basicPay);
        var tsdIt = calculateTSDIT(employee.basicPay);

        // Calculate total earnings and deductions
        var totalEarning = employee.basicPay + HRA + conveyance + employee.nytShiftSalary;
        var totalDeduction = pf + esi + employee.loanAmount + professionTax + tsdIt + employee.leaveSalary;

        // Calculate net salary
        var netSalary = totalEarning - totalDeduction;

        // Display the results
        // alert("Net Salary: " + netSalary);
        document.getElementById("NetSalary").innerHTML = netSalary;
        displaySalaryDetails(employee, month, year, HRA, conveyance, pf, esi, professionTax, tsdIt, totalEarning, totalDeduction, netSalary);
    }else {
        alert("Employee not found!");
    }
    
}
function displaySalaryDetails(employee, month, year, HRA, conveyance, pf, esi, professionTax, tsdIt, totalEarning, totalDeduction, netSalary) {
    var tableBody = document.getElementById("salaryDetails");
    var newRow = tableBody.insertRow();

    newRow.insertCell().textContent = employee.empid;
    newRow.insertCell().textContent = month;
    newRow.insertCell().textContent = year;
    newRow.insertCell().textContent = HRA.toFixed(2);
    newRow.insertCell().textContent = conveyance.toFixed(2);
    newRow.insertCell().textContent = pf.toFixed(2);
    newRow.insertCell().textContent = esi.toFixed(2);
    newRow.insertCell().textContent = professionTax.toFixed(2);
    newRow.insertCell().textContent = tsdIt.toFixed(2);
    newRow.insertCell().textContent = totalEarning.toFixed(2);
    newRow.insertCell().textContent = totalDeduction.toFixed(2);
    newRow.insertCell().textContent = netSalary.toFixed(2);
}
function calculateHRA(basicPay) {
    return 0.4 * basicPay;
}
function calculateProvidentFund(basicPay) {
    return (basicPay <= 15000) ? 0.12 * basicPay : 0.0;
}
function calculateESI(basicPay) {
    return (basicPay <= 21000) ? 0.75 * basicPay : 0.0;
}
function calculateProfessionalTax(basicPay) {
    if (basicPay <= 3500) {
        return 0.0;
    } else if (basicPay <= 5000) {
        return 22.5;
    } else if (basicPay <= 7500) {
        return 52.5;
    } else if (basicPay <= 10000) {
        return 115;
    } else if (basicPay <= 12500) {
        return 171;
    } else {
        return 208;
    }
}

function calculateTSDIT(basicPay) {
    var totalTaxableIncome = basicPay * 12;
    if (totalTaxableIncome <= 250000) {
        return 0.0;
    } else if (totalTaxableIncome <= 500000) {
        return (totalTaxableIncome - 250000) * 0.05;
    } else if (totalTaxableIncome <= 1000000) {
        return 12500 + (totalTaxableIncome - 500000) * 0.2;
    } else {
        return 112500 + (totalTaxableIncome - 1000000) * 0.3;
    }
}

