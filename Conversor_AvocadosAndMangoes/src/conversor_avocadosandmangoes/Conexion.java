/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversor_avocadosandmangoes;

import Clases.client;
import Clases.order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class Conexion {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String ipServer = "jdbc:mysql://localhost:3306/";
    private String baseDatos = "avocadosandmangoes";
    private String user = "root";
    private String contrasena = "root";
    Connection con;
    public Conexion(){
      
        
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(ipServer+baseDatos,user,contrasena);
        } catch (Exception e) {
            System.err.println("Error:" +e);
        }
    }
    public static void main(String[] args) {
        listarDatos();
        
    }
    
    public static void listarDatos(){
        Conexion cn=new Conexion();
        Statement st;
        ResultSet rs;
        try {
            st=(Statement) cn.con.createStatement();
            //rs = st.executeQuery("insert into products (id, nombre, precio, cantidad) values (3,'cereza', 900,5 )");
            rs=st.executeQuery("select * from products");
            while (rs.next()) {                
                System.out.println(rs.getInt("id")+" " +rs.getString("nombre")+" ");
            }
            cn.con.close();
        } catch (Exception e) {
        }
    }
 
    public static void insertarDatos(Object object){
        Conexion cn=new Conexion();
        
        if(object instanceof client)
        {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into clients (id, shippingPhone, name, address, address2, city, postalCode) values (?,?,?,?,?,?,? )");
                PS.setString(2, ((client) object).getShippingPhone());
                PS.setString(3, ((client) object).getName());
                PS.setString(4, ((client) object).getAddress());
                PS.setString(5, ((client) object).getAddress2());
                PS.setString(6, ((client) object).getCity());
                PS.setString(7, ((client) object).getPostalCode());
                PS.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(object instanceof order)
        {
            try {
                PreparedStatement PS = cn.con.prepareStatement("insert into orders (id, stop, shippingPhone, shippingName, address, address2, city, postalCode, itemName, cant, value, total, payment, comments) values (?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
                PS.setInt(2, ((order) object).getStop());
                PS.setString(2, ((order) object).getShippingPhone());
                PS.setString(3, ((order) object).getShippingName());
                PS.setString(4, ((order) object).getAddress());
                PS.setString(5, ((order) object).getAddress2());
                PS.setString(6, ((order) object).getCity());
                PS.setString(7, ((order) object).getPostalCode());
                PS.setInt(2, ((order) object).getCant());
                PS.setDouble(2, ((order) object).getValue());
                PS.setDouble(2, ((order) object).getTotal());
                PS.setString(7, ((order) object).getPayment());
                PS.setString(7, ((order) object).getComments());
                PS.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
