/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

/**
 *
 * @author cesar antonio
 */
public class usuario {
    private String nombre;
    private int edad;
    private int costo;

    public usuario(String nombre,int edad, int costo) {
        this.nombre = nombre;
        this.edad = edad;
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }
    
    public int getCosto() {
        return costo;
    }
    
}
