function addNytShift() {
    var empId = document.getElementById("empId").value;
    var nytShiftDate = document.getElementById("nytShiftDate").value;
    var nytShiftAmt = document.getElementById("nytShiftAmt").value;

    var nytShiftDetails = {
        nytShiftId: generateNytShiftId(),
        empId: empId,
        nytShiftDate: nytShiftDate,
        nytShiftAmt: nytShiftAmt
    };
    displayNytShiftDetails(nytShiftDetails);
}

function generateNytShiftId() {
    return "NSID" + Math.floor(Math.random() * 900);
}

function displayNytShiftDetails(nytShiftDetails) {
    var nytShiftDetailsTable = document.getElementById("nytShiftDetail");

    var newRow = nytShiftDetailsTable.insertRow();
    newRow.insertCell(0).innerHTML = nytShiftDetails.nytShiftId;
    newRow.insertCell(1).innerHTML = nytShiftDetails.empId;
    newRow.insertCell(2).innerHTML = nytShiftDetails.nytShiftDate;
    newRow.insertCell(3).innerHTML = nytShiftDetails.nytShiftAmt;
}
