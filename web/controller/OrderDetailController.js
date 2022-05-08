function addOrderDetail(orderDetailList) {
    for (var orderDetail of orderDetailList){
        orderDetailDB.push(orderDetail);
    }
}

function searchOrderDetail(orderId) {
    var orderItemList = new Array();
    for (var orderDetail of orderDetailDB){
        if (orderDetail.getOrderId()==orderId){
            orderItemList.push(orderDetail);
        }
    }
    return orderItemList;
}