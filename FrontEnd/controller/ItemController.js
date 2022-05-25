function saveItem() {
    var data= $("#itemForm").serialize();
    console.log(data);

    $.ajax({
        url:"http://localhost:8080/pos/item",
        method:"POST",
        data:data,// if we send data with the request
        beforeSend:function(){
            return confirm("Are you sure you want to add this item?");
        },
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
                loadAllItems();
                clearItemForm();
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

function updateItem() {
    var itemDTO = {
        code:$("#itemCode").val(),
        description:$("#newItemName").val(),
        qtyOnHand:$("#quantity").val(),
        unitPrice:$("#price").val()
    }
    $.ajax({
        url: "http://localhost:8080/pos/item",
        method: "PUT",
        contentType:"application/json",
        data:JSON.stringify(itemDTO),
        beforeSend: function(){
            return confirm("Are you sure you want to Update this Item?");
        },
        success: function (res) {
            if (res.status==200) {
                alert(res.message);
                loadAllItems();
                clearItemForm();
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

function deleteItem(itemCode) {
    $.ajax({
        url: "http://localhost:8080/pos/item?itemCode=" + itemCode,
        method: "DELETE",
        //data:data,// application/x-www-form-urlencoded
        beforeSend:function(){
            return confirm("Are you sure you want to DELETE this item?");
        },
        success: function (res) {
            if (res.status==200) {
                console.log(typeof res)
                alert(res.message);
                loadAllItems();
                clearItemForm();
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

function searchItem(itemCode) {
    $.ajax({
        url: "http://localhost:8080/pos/item?option=SEARCH&itemCode="+itemCode,
        method:"GET",
        success: function (resp) {
            if (resp.status==200){
                for (const item of resp.data) {
                    console.log(item.id ,item.name)
                    $("#itemCode").val(item.code);
                    $("#newItemName").val(item.description);
                    $("#price").val(item.qtyOnHand);
                    $("#quantity").val(item.unitPrice);
                }
            }else {
                alert(resp.message);
            }
        }
    });
}

function bindItemTableEvents() {
    /*Get Item to Form*/
    $("#tblItem>tbody>tr").click(function () {
        let selectedRow = $(this);
        let itemCode = $(this).children(":nth-child(1)").text();
        let itemName = $(this).children(":nth-child(2)").text();
        let itemQty = $(this).children(":nth-child(3)").text();
        let itemPrice = $(this).children(":nth-child(4)").text();

        $("#itemCode").val(itemCode);
        $("#newItemName").val(itemName);
        $("#quantity").val(itemQty);
        $("#price").val(itemPrice);
    });

    /*Item Delete starts*/
    /*$("#tblItem>tbody>tr").dblclick(function () {
        let itemCode = $(this).children(":nth-child(1)").text();
        let itemName = $(this).children(":nth-child(2)").text();
        let itemPrice = $(this).children(":nth-child(3)").text();
        let itemQty = $(this).children(":nth-child(4)").text();

        $("#itemCode").val(itemCode);
        $("#newItemName").val(itemName);
        $("#price").val(itemPrice);
        $("#quantity").val(itemQty);

        let response = confirm("Are you sure you want to delete this Item?");
        if (response) {
            clearItemForm();
            deleteItem(itemCode);
            $(this).remove();
            loadItemIds();
        } else {

        }
    });*/
    /*Item Delete ends*/
}

function loadAllItems() {
    $("#tblItem>tbody").empty();
    $.ajax({
        url: "http://localhost:8080/pos/item?option=GETALL",
        method:"GET",
        // dataType:"json", // please convert the response into JSON
        success: function (resp) {
            console.log(typeof resp);
            for (const item of resp.data) {
                console.log(item.code ,item.description)
                let row=`<tr><td>${item.code}</td><td>${item.description}</td><td>${item.qtyOnHand}</td><td>${item.unitPrice}</td></tr>`;
                $("#tblItem").append(row);
            }
            bindItemTableEvents();
        }
    });
}

/*function getItemIds() {
    var itemIds = new Array();
    for (var item of itemDB) {
        itemIds.push(item.getItemCode());
    }
    return itemIds;
}*/


function clearItemForm(){
    $("#itemCode").val("");
    $("#newItemName").val("");
    $("#price").val("");
    $("#quantity").val("");
}

function clearItemTable() {
    $("#tblItem>tbody>tr").empty();
}