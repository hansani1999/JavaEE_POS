function ItemDTO(code,description,price,qty){
    var _code = code;
    var _description = description;
    var _price = price;
    var _qty = qty;

    this.setItemCode = function (code) {
        _code = code;
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
    this.getItemCode = function () {
        return _code;
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
}