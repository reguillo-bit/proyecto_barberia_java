/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

/**
 *
 * @author cesar antonio
 */
public class infoconexion {
    String url = "jdbc:mysql://localhost:3306/BARBERIA";
    String user = "root";
    String password = "MyNewPass1";

    public infoconexion() {

    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
    
    
}
