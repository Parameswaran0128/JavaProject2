

function fillText(button, content) {
    var paragraph = button.querySelector('p');
    paragraph.innerText = content;
}

function reModel(button){
    var paragraph = button.querySelector('p');
    paragraph.innerText = "";
}

function CreateRedirect(){
    window.location.href = "adminCreate.html";
}
function SalaryCalculationRedirect(){
    window.location.href = "SalaryDetails.html";
}


function LeaveDetailRedirect(){
    window.location.href = "leaveDetail.html";
}

function NytShiftDetailRedirect(){
    window.location.href = "nytshiftdetails.html";
}
