
function CustomerDTO(id,name,address,salary) {
    var _id = id;
    var _name = name;
    var _address = address;
    var _salary = salary;

    this.setCustomerId = function (cid) {
        _id=cid;
    }
    this.setCustomerName = function (cusName) {
        _name = cusName;
    }
    this.setAddress = function (cusAddress) {
        _address = cusAddress;
    }
    this.setSalary = function (cusSalary) {
        _salary = cusSalary;
    }
    this.getCustomerId = function () {
        return _id;
    }
    this.getCustomerName = function () {
        return _name;
    }
    this.getAddress = function () {
        return _address;
    }
    this.getSalary = function () {
        return _salary;
    }

    this.printCustomerDTO = function () {
        console.log(_id ,_name ,_address ,_salary);
    }
}