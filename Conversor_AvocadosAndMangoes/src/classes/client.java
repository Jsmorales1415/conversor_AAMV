/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author diego
 */
public class client {
    private String shippingPhone;
    private String name;
    private String address;
    private String address2;
    private String city;
    private String postalCode;

    public client(String shippingPhone, String name, String address, String address2, String city, String postalCode) {
        this.shippingPhone = shippingPhone;
        this.name = name;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
    }

    public client() {
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
}
