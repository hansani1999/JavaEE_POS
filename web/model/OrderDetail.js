function OrderDetail(orderId,itemCode,description,price,oQty) {
    var _orderId = orderId;
    var _itemCode = itemCode;
    var _description = description;
    var _price = price;
    var _oQty = oQty;

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
    this.setOQty = function (oQty) {
        _oQty=oQty;
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
    this.getOrderQty = function () {
        return _oQty;
    }
}