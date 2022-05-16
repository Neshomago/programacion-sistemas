/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Conexionbd {
    private final String bd="progsistemas";
    private final String url="jdbc:mysql://localhost:3306/";
    protected String user="root";
    protected String password="";
    String driver="com.mysql.cj.jdbc.Driver";
    Connection cx;
    static PreparedStatement query;
    static ResultSet resultado;
    int loggeo;

    public Connection conectar(){
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url+bd,user,password);
            System.out.println("Conectado a la base");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Problemas al conectarse");
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void desconectar(){
        try {
            cx.close();
            System.out.println("Desconectado de la  base");
        } catch (SQLException ex) {
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int loginUser(String userLogin, String passwordLogin){
        try {
            conectar();
            query = cx.prepareStatement("SELECT * FROM `usuarios` WHERE user = '"+userLogin+"' AND password = '"+passwordLogin+"';");
            resultado = query.executeQuery();
            System.out.println("user hacia la base: "+userLogin+", y pass: "+passwordLogin);
            /*System.out.print("Nombre:"+resultado.getString("nombre"));*/
            while(resultado.next()){
                String nombreUsuario = resultado.getString("nombre");
                String userSistema = resultado.getString("user");
                String passSistema = resultado.getString("password");
                if(userLogin.equals(userSistema) &&  passwordLogin.equals(passSistema)){
                System.out.println(nombreUsuario + "user de sistema: " + userSistema + " y pass: "+passSistema);
                JOptionPane.showMessageDialog(null, "Bienvenido "+ nombreUsuario);
                loggeo = 1;
                System.out.println("log correcto: " + loggeo);
                } else {
                    loggeo = 0;
                    System.out.println("log incorrecto: " + loggeo);
                }
            }
            /*
            SELECT * FROM `usuarios` WHERE user = 'admin' AND password = 'admin';
            */
        } catch (SQLException ex) {
            Logger.getLogger(Conexionbd.class.getName()).log(Level.SEVERE, null, ex);
            loggeo = 0;
            System.out.println("log incorrecto" + loggeo);
            desconectar();
        }

        return loggeo;
    }
    
    public void crearUser(String name, String email, String user, String password, String phone, String role){
        /*
        INSERT INTO `usuarios` (`id_usuario`, `nombre`, `correo`, `user`, `password`, `telefono`, `rol`, `fecha_creacion`)
        VALUES (NULL, 'Andres Torres', 'magoyde@gmail.com', 'admin', 'admin', '0963509104', 'admin', current_timestamp());
        */
    }
}
