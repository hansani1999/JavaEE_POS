function OrderDTO(orderId,cusId,cost,date,discount) {
    var _oderId = orderId;
    var _cusId = cusId;
    var _cost = cost;
    var _date = date;
    var _discount = discount;

    this.setOrderId = function (orderId) {
        _oderId =orderId;
    }
    this.setCusId = function (cusId) {
        _cusId = cusId;
    }
    this.setCost = function (cost) {
        _cost = cost;
    }
    this.setDiscount = function (discount) {
        _discount = discount;
    }
    this.setDate = function (date) {
        _date = date;
    }
    this.getOrderId = function () {
        return _oderId;
    }
    this.getCusId = function () {
        return _cusId;
    }
    this.getCost =  function () {
        return _cost;
    }
    this.getDiscount =  function () {
        return _discount;
    }
    this.getDate = function () {
        return _date;
    }
}