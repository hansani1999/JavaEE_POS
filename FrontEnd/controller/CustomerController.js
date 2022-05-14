function saveCustomer(dto) {
    customerDB.push(dto);
}

function updateCustomer(dto){
    for (var customer of customerDB){
        if (customer.getCustomerId()== dto.getCustomerId()){
            customer.setCustomerId(dto.getCustomerId());
            customer.setCustomerName(dto.getCustomerName());
            customer.setAddress(dto.getAddress());
            customer.setSalary(dto.getSalary());
        }
    }
}

function loadAllCustomers() {
    /*for (var customer of customerDB){
        let row = `<tr><td>${customer.getCustomerId()}</td><td>${customer.getCustomerName()}</td><td>${customer.getAddress()}</td><td>${customer.getSalary()}</td></tr>`;
        $("#tblCust").append(row);
    }*/
    $("#tblCust>tbody").empty();
    $.ajax({
        url: "http://localhost:8080/pos/customer",
        method:"GET",
        // dataType:"json", // please convert the response into JSON
        success: function (resp) {
            console.log(typeof resp);
            for (const customer of resp.data) {
                console.log(customer.id ,customer.name)
                let row=`<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`;
                $("#tblCust").append(row);
            }
            getCustomerToForm();
        }
    })
}

function getCustomerToForm(){
    $("#tblCust>tbody>tr").click(function () {
        console.log("Customer selected")
        let cusId = $(this).children(":nth-child(1)").text();
        let cusName = $(this).children(":nth-child(2)").text();
        let cusAddress = $(this).children(":nth-child(3)").text();
        let cusSalary = $(this).children(":nth-child(4)").text();

        $("#cid").val(cusId);
        $("#newCustName").val(cusName);
        $("#newCustAddress").val(cusAddress);
        $("#newCustSalary").val(cusSalary);
    });
}

function bindTableEvents(){
    /*get Customer to Form*/
    $("#tblCust>tbody>tr").click(function () {
        let selectedRow =$(this);
        let cusId = $(this).children(":nth-child(1)").text();
        let cusName = $(this).children(":nth-child(2)").text();
        let cusAddress = $(this).children(":nth-child(3)").text();
        let cusSalary = $(this).children(":nth-child(4)").text();

        //console.log(cusId ,cusName ,cusAddress ,cusSalary);

        $("#cid").val(cusId);
        $("#newCustName").val(cusName);
        $("#newCustAddress").val(cusAddress);
        $("#newCustSalary").val(cusSalary);
    });

    /*Delete Customer Starts*/
    $("#tblCust>tbody>tr").dblclick(function () {
        var selctedRow = $(this);
        let cusId = $(this).children(":nth-child(1)").text();
        let cusName = $(this).children(":nth-child(2)").text();
        let cusAddress = $(this).children(":nth-child(3)").text();
        let cusSalary = $(this).children(":nth-child(4)").text();

        $("#cid").val(cusId);
        $("#newCustName").val(cusName);
        $("#newCustAddress").val(cusAddress);
        $("#newCustSalary").val(cusSalary);

        let response = confirm("Are you sure you want to Delete this customer?");
        if (response){
            deleteCustomer(cusId);
            $(this).remove();
            clearCusForm();
            loadCustIds();
        }else {

        }
    });
    /*Delete Customer Ends*/
}

function deleteCustomer(cusId) {
    for (var customer of customerDB){
        if (customer.getCustomerId()==cusId){
            var index = customerDB.indexOf(customer);
            customerDB.splice(index,1);
        }
    }
}

function searchCustomer(cusId) {
    for (var customer of customerDB){
        if (customer.getCustomerId()==cusId){
            return customer;
        }
    }
}



function getAllCustomers() {
    let custIds = new Array();
    for (var customer of customerDB){
        custIds.push(customer.getCustomerId());
    }
    return custIds;
}

function clearTable() {
    $("#tblCust>tbody>tr").empty();
}

function clearCusForm(){
    $("#cid").val("");
    $("#newCustName").val("");
    $("#newCustAddress").val("");
    $("#newCustSalary").val("");
}
