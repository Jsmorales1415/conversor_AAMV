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
public class Product {
    private String nombre;
    private int cantidad;
    private double purchaseValue;
    private double saleValue;
    

    public Product(String nombre, int cantidad, double purchaseValue, double saleValue) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.purchaseValue = purchaseValue;
        this.saleValue = saleValue;
    }

    public Product() {
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the purchaseValue
     */
    public double getPurchaseValue() {
        return purchaseValue;
    }

    /**
     * @param purchaseValue the purchaseValue to set
     */
    public void setPurchaseValue(double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    /**
     * @return the saleValue
     */
    public double getSaleValue() {
        return saleValue;
    }

    /**
     * @param saleValue the saleValue to set
     */
    public void setSaleValue(double saleValue) {
        this.saleValue = saleValue;
    }

    
}
