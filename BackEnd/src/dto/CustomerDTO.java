package dto;

public class CustomerDTO {
    private String cusId;
    private String cusName;
    private String cusAddress;
    private double salary;

    public CustomerDTO() {
    }

    public CustomerDTO(String cusId, String cusName, String cusAddress, double salary) {
        this.setCusId(cusId);
        this.setCusName(cusName);
        this.setCusAddress(cusAddress);
        this.setSalary(salary);
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
