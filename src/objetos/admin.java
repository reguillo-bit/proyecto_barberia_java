/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

/**
 *
 * @author cesar antonio
 */
public class admin {
    private int idrole;
    private String user;
    private String password;

    public admin(int idrole, String user, String password) {
        this.idrole = idrole;
        this.user = user;
        this.password = password;
    }

    public int getIdrole() {
        return idrole;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
    
    
    
}
