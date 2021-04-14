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
public class Client {
    private String shippingPhone;
    private String shopifyCode;
    private String name;
    private String address;
    private String address2;
    private String city;
    private String postalCode;
    private String classe;
    private int subscribe;

    public Client(String shippingPhone, String shopifyCode, String name, String address, String address2, String city, String postalCode, int subscribe) {
        this.shippingPhone = shippingPhone;
        this.shopifyCode = shopifyCode;
        this.name = name;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.subscribe = subscribe;
    }

    public Client() {
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

    /**
     * @return the shopifyCode
     */
    public String getShopifyCode() {
        return shopifyCode;
    }

    /**
     * @param shopifyCode the shopifyCode to set
     */
    public void setShopifyCode(String shopifyCode) {
        this.shopifyCode = shopifyCode;
    }

    /**
     * @return the classe
     */
    public String getClasse() {
        return classe;
    }

    /**
     * @param classe the classe to set
     */
    public void setClasse(String classe) {
        this.classe = classe;
    }

    /**
     * @return the subscribe
     */
    public int getSubscribe() {
        return subscribe;
    }

    /**
     * @param subscribe the subscribe to set
     */
    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }
}
