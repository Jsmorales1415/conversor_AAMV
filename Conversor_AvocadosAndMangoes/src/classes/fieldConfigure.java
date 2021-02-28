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
public class fieldConfigure {
    private String shoppingName;
    private String dbName;

    public fieldConfigure() {
    }

    public fieldConfigure(String shoppingName, String dbName) {
        this.shoppingName = shoppingName;
        this.dbName = dbName;
    }

    /**
     * @return the shoppingName
     */
    public String getShoppingName() {
        return shoppingName;
    }

    /**
     * @param shoppingName the shoppingName to set
     */
    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    
}
