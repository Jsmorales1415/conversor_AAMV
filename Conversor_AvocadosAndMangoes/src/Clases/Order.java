/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author diego
 */
public class Order {
    private int stop;
    private String shippingPhone;
    private String shippingName;
    private String address;
    private String address2;
    private String city;
    private String postalCode;
    private String itemName;
    private int cant;
    private double value;
    private double total;
    private String payment;
    private String comments;

    public Order(int stop, String shippingPhone, String shippingName, String address, String address2, String city, String postalCode, String itemName, int cant, double value, double total, String payment, String comments) {
        this.stop = stop;
        this.shippingPhone = shippingPhone;
        this.shippingName = shippingName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.itemName = itemName;
        this.cant = cant;
        this.value = value;
        this.total = total;
        this.payment = payment;
        this.comments = comments;
    }

    public Order() {
    }
    /**
     * @return the stop
     */
    public int getStop() {
        return stop;
    }

    /**
     * @param stop the stop to set
     */
    public void setStop(int stop) {
        this.stop = stop;
    }

    /**
     * @return the shippingPhone
     */
    public String getShippingPhone() {
        return shippingPhone;
    }

    /**
     * @param shippingPhone the shippingPhone to set
     */
    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    /**
     * @return the shippingName
     */
    public String getShippingName() {
        return shippingName;
    }

    /**
     * @param shippingName the shippingName to set
     */
    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the cant
     */
    public int getCant() {
        return cant;
    }

    /**
     * @param cant the cant to set
     */
    public void setCant(int cant) {
        this.cant = cant;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the payment
     */
    public String getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
}
