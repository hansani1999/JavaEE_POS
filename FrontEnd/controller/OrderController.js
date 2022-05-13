function loadCustIds(){
    let custIds = getAllCustomers();
    $("#txtCid").empty();
    for (var id of custIds){
        var option = `<option>${id}</option>`;
        $("#txtCid").append(option);
    }
    $("#txtCid").off();
    $("#txtCid").bind('change',function () {
        var cusId= $("#txtCid").val();
        var customer = searchCustomer(cusId);
        $("#name").val(customer.getCustomerName());
        $("#address").val(customer.getAddress());
        $("#salary").val(customer.getSalary());
    });
}

function loadItemIds() {
    let itemIds = getItemIds();
    $("#code").empty();
    for (var id of itemIds){
        var option = `<option>${id}</option>`;
        $("#code").append(option);
    }

    $("#code").bind('change',function () {
        var itemCode = $("#code").val();
        var item = searchItem(itemCode);
        $("#itemName").val(item.getDescription());
        $("#txtPrice").val(item.getPrice());
        $("#qty").val(item.getQty());
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
