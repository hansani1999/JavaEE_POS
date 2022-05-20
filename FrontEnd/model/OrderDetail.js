function OrderDetail(itemCode,price,oQty) {
    var _itemCode = itemCode;
    var _price = price;
    var _oQty = oQty;


    this.setItemCode = function (code) {
        _itemCode = code;
    }
    this.setPrice = function (price) {
        _price = price;
    }
    this.setOQty = function (oQty) {
        _oQty=oQty;
    }

    this.getItemCode = function () {
        return _itemCode;
    }
    this.getPrice = function () {
        return _price;
    }
    this.getOrderQty = function () {
        return _oQty;
    }
}