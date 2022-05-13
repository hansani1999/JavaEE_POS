function CartItem(orderId,itemCode,description,price,qty,total) {
    var _orderId = orderId;
    var _itemCode = itemCode;
    var _description = description;
    var _price = price;
    var _qty = qty;
    var _total = total;

    this.setOrderId = function (orderId) {
        _orderId =orderId;
    }
    this.setItemCode = function (code) {
        _itemCode = code;
    }
    this.setDescription = function (desc) {
        _description = desc;
    }
    this.setPrice = function (price) {
        _price = price;
    }
    this.setQty = function (qty) {
        _qty = qty;
    }
    this.setTotal = function (total) {
        _total= total;
    }
    this.getOrderId = function () {
        return _orderId;
    }
    this.getItemCode = function () {
        return _itemCode;
    }
    this.getDescription = function () {
        return _description;
    }
    this.getPrice = function () {
        return _price;
    }
    this.getQty = function () {
        return _qty;
    }
    this.getTotal = function () {
        return _total;
    }
}