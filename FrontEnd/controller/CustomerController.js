function saveCustomer() {
    var data= $("#customerForm").serialize();
    console.log(data);

    $.ajax({
        url:"http://localhost:8080/pos/customer",
        method:"POST",
        data:data,// if we send data with the request
        beforeSend:function(){
            return confirm("Are you sure you want to add this customer?");
        },
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
                loadAllCustomers();
            } else {
                alert(res.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
        }
    });
}

function updateCustomer(){
    var cusObject = {
        id:$("#cid").val(),
        name:$("#newCustName").val(),
        address:$("#newCustAddress").val(),
        salary:$("#newCustSalary").val()
    }
    $.ajax({
        url: "http://localhost:8080/pos/customer",
        method: "PUT",
        contentType:"application/json",
        data:JSON.stringify(cusObject),
        beforeSend: function(){
          return confirm("Are you sure you want to Update this Customer?");
        },
        success: function (res) {
            if (res.status==200) {
                alert(res.message);
                loadAllCustomers();
            }else if(res.salary==400){
                alert(res.data)
            }else {
                alert(res.data);
            }

        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
        }
    });
}

function loadAllCustomers() {
    $("#tblCust>tbody").empty();
    $.ajax({
        url: "http://localhost:8080/pos/customer?option=GETALL",
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
    });
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
    let customerID = $("#cid").val();

    $.ajax({
        url: "http://localhost:8080/pos/customer?CusID=" + customerID,
        method: "DELETE",
        //data:data,// application/x-www-form-urlencoded
        beforeSend:function(){
            return confirm("Are you sure you want to DELETE this customer?");
        },
        success: function (res) {
            if (res.status==200) {
                console.log(typeof res)
                alert(res.message);
                loadAllCustomers();
            }else if (res.status==400){
                alert(res.data);
            }else {
                alert(res.data);
            }

        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
        }
    });
}

function searchCustomer(cusId) {

    $.ajax({
        url: "http://localhost:8080/pos/customer?option=SEARCH&cusID="+cusId,
        method:"GET",
        // dataType:"json", // please convert the response into JSON
        success: function (resp) {
            if (resp.status==200){
                for (const customer of resp.data) {
                    console.log(customer.id ,customer.name)
                    $("#cid").val(customer.id);
                    $("#newCustName").val(customer.name);
                    $("#newCustAddress").val(customer.address);
                    $("#newCustSalary").val(customer.salary);
                }
            }else {
             alert(resp.message);
            }
        }
    });
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
