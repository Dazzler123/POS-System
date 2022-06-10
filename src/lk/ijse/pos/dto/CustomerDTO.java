package lk.ijse.pos.dto;

public class CustomerDTO {

    private String custID;
    private String custTitle;
    private String custName;
    private String custAddress;
    private String custCity;
    private String custProvice;
    private String custPostalCode;

    public CustomerDTO(String custID, String custTitle, String custName, String custAddress, String custCity, String custProvice, String custPostalCode) {
        this.custID = custID;
        this.custTitle = custTitle;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProvice = custProvice;
        this.custPostalCode = custPostalCode;
    }

    public CustomerDTO() {
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCustTitle() {
        return custTitle;
    }

    public void setCustTitle(String custTitle) {
        this.custTitle = custTitle;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProvice() {
        return custProvice;
    }

    public void setCustProvice(String custProvice) {
        this.custProvice = custProvice;
    }

    public String getCustPostalCode() {
        return custPostalCode;
    }

    public void setCustPostalCode(String custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custID='" + custID + '\'' +
                ", custTitle='" + custTitle + '\'' +
                ", custName='" + custName + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custCity='" + custCity + '\'' +
                ", custProvice='" + custProvice + '\'' +
                ", custPostalCode='" + custPostalCode + '\'' +
                '}';
    }
}
