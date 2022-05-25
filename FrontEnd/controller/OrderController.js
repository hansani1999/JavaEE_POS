function loadAllOrders() {
    $("#tblOrder>tbody").empty();
    $.ajax({
        url: "http://localhost:8080/pos/orders?option=GET_ALL_ORDERS",
        method:"GET",
        // dataType:"json", // please convert the response into JSON
        success: function (resp) {
            console.log(typeof resp);
            for (const order of resp.data) {
                let row=`<tr><td>${order.orderId}</td><td>${order.cusId}</td><td>${order.orderDate}</td><td>${order.cost}</td></tr>`;
                $("#tblOrder").append(row);
            }
            //getCustomerToForm();
        }
    });
}

function loadCustIds(){
    $("#txtCid").empty();
    $.ajax({
        url: "http://localhost:8080/pos/orders?option=GET_CUS_IDS",
        method:"GET",
        success: function (resp) {
            console.log(typeof resp);
            for (const customer of resp.data) {
                var option1 = `<option>${customer.id}</option>`;
                $("#txtCid").append(option1);
            }
            $("#txtCid").val("");
            $("#txtCid").off();
            $("#txtCid").bind('change',function () {
                var cusId= $("#txtCid").val();
                getCustomer(cusId);
            });
        }
    });
}

function loadItemIds() {
    $("#code").empty();
    $.ajax({
        url: "http://localhost:8080/pos/orders?option=GET_ITEM_IDS",
        method:"GET",
        success: function (resp) {
            for (var item of resp.data){
                var option = `<option>${item.code}</option>`;
                $("#code").append(option);
            }
            $("#code").val("");
            $("#code").bind('change',function () {
                var itemCode = $("#code").val();
                getItem(itemCode);
            });
        }
    });

}

function getItem(itemCode) {
    $.ajax({
        url: "http://localhost:8080/pos/item?option=SEARCH&itemCode="+itemCode,
        method:"GET",
        success: function (resp) {
            if (resp.status==200){
                for (const item of resp.data) {
                    $("#itemName").val(item.description);
                    $("#txtPrice").val(item.qtyOnHand);
                    $("#qty").val(item.unitPrice);
                }
            }else {
                alert(resp.message);
            }
        }
    });
}

function addCartItem(cartItem){
    cart.push(cartItem);
}

function removeCartItem(itemCode){
    for (var cartItem of cart){
        if (itemCode==cartItem.getItemCode()){
            var index = cart.indexOf(cartItem);
            cart.splice(index,1);
        }
    }
}

function ifExistsItem(itemCode) {
    for (var cartItem of cart){
        if (itemCode==cartItem.getItemCode()){
            return cartItem;
        }
    }
}

function loadCartItems() {
    for (var cartItem of cart){
        let row = `<tr><td>${cartItem.getItemCode()}</td><td>${cartItem.getDescription()}</td><td>${cartItem.getPrice()}</td><td>${cartItem.getQty()}</td><td>${cartItem.getTotal()}</td></tr>`;
        $("#tblCart").append(row);
    }
}

function addOrder(orderDTO) {
    orderDB.push(orderDTO);
}

function clearCartTable() {
    $("#tblCart>tbody>tr").empty();
}

function clearForms(){
    $("#oid").val("");
    $("#txtCid").val("");
    $("#code").val("");
    $("#name").val("");
    $("#address").val("");
    $("#salary").val("");
    $("#itemName").val("");
    $("#txtPrice").val("");
    $("#qty").val("");
    $("#oQty").val("");
    $("#txtCash").val("");
    $("#txtBalance").val("");
    $("#txtDiscount").val("");
    $("#lblTotal").text("");
    $("#lblSubTotal").text("");
}

function searchOrder(orderId) {
    for (var order of orderDB){
        if (order.getOrderId()==orderId){
            console.log(order.getOrderId(),order.getCusId(),order.getCost(),order.getDate());
            return order;
        }
    }
}

function getCustomer(cusId){
    $.ajax({
        url: "http://localhost:8080/pos/customer?option=SEARCH&cusID="+cusId,
        method:"GET",
        // dataType:"json", // please convert the response into JSON
        success: function (resp) {
            if (resp.status==200){
                for (const customer of resp.data) {
                    $("#name").val(customer.name);
                    $("#address").val(customer.address);
                    $("#salary").val(customer.salary);
                }
            }else {
                alert(resp.message);
            }
        }
    });
}

function saveOrder() {
    let orderId = $("#oid").val();
    let orderDate = $("#txtDate").val();
    let cusId = $("#txtCid").val();
    let cost = $("#lblSubTotal").text();
    let discount = $("#txtDiscount").val();
    var orderDetailList = [];

    let costValue = cost.split("/")[0];

    console.log(cart)

    for (let cItem of cart){
        let detail={};
            detail.itemCode=cItem.getItemCode();
            detail.qty=cItem.getQty();
            detail.uPrice = cItem.getPrice();
            orderDetailList.push(detail);
        console.log("OrderQty : "+cItem.getQty());
    }
    for (var detail of orderDetailList){
        console.log(detail.itemCode,detail.qty,detail.uPrice)
    }
    //var orderDetailList = new Array();

    let dto = {
        orderId:orderId,
        cusId:cusId,
        orderDate:orderDate,
        cost:costValue,
        itemList:orderDetailList
    }
    let orderDetailJson = JSON.stringify(dto);
   /* for (var i of array){
        console.log(i.item)
    }*/
    console.log(orderDetailJson);

    $.ajax({
        url:"http://localhost:8080/pos/orders",
        method:"POST",
        data:orderDetailJson,
        // if we send data with the request
        beforeSend:function(){
            return confirm("Are you sure you want to add this order?");
        },
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
                loadAllOrders();
                clearForms();
                clearCartTable();
                clearCart();
                generateOrderId();
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

function generateOrderId() {
    $.ajax({
        url: "http://localhost:8080/pos/orders?option=GEN_ORDER_ID",
        method:"GET",
        // dataType:"json", // please convert the response into JSON
        success: function (resp) {
            console.log(typeof resp);
            let data = resp.data;
            $("#oid").val(data);
            $("#oid").attr('disabled','disabled');
            //getCustomerToForm();
        }
    });
}
