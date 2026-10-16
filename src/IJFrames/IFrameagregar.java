/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package IJFrames;

import JFrames.JFrameprincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import objetos.barbero;
import objetos.infoconexion;
import objetos.usuario;

/**
 *
 * @author cesar antonio
 */
public class IFrameagregar extends javax.swing.JInternalFrame {
    String nombre;
    int edad;
    String servicio;
    ArrayList<barbero> b = new ArrayList();
    infoconexion conexion;
    int costo;
    int n = 1;
    JFrameprincipal principal;
    /**
     * Creates new form NewJInternalFrame
     */
    public IFrameagregar(JFrameprincipal principal) {
        initComponents();
        conexion = new infoconexion();
        this.principal = principal;
        llenarcombo();
        txtnombre.setText("");
        txtnombre.setColumns(7);
        txtedad.setText("");
        txtedad.setColumns(7);
        txtservicio.setText("");
        txtservicio.setColumns(6);
        txtprecio.setText("");
        txtprecio.setColumns(6);
    }


    public void capturardatos(){
        try{
            nombre = txtnombre.getText();
            edad = Integer.valueOf(txtedad.getText());
            servicio = txtservicio.getText();
            
            costo = Integer.valueOf(txtprecio.getText());
        }
        catch(Exception c)
        {
            JOptionPane.showMessageDialog(rootPane, "error de datos");
        }
    }
    public void llenarcombo(){
        cmbbarbero.removeAllItems();
        cmbbarbero.addItem("seleccione...");
        try(Connection con = DriverManager.getConnection(conexion.getUrl(),conexion.getUser(),conexion.getPassword()))
        {        
            Statement stm1 = con.createStatement();
            String query1 = "SELECT * FROM verbarbero";
            ResultSet rs = stm1.executeQuery(query1);

            while(rs.next()){
                int idbarbero = rs.getInt("ID");
                String nombrebarbero = rs.getString("NOMBRE");
                int edadbarbaero = rs.getInt("EDAD");
                

                b.add(new barbero(idbarbero,nombrebarbero,edadbarbaero));
                cmbbarbero.addItem(nombrebarbero);
            }  
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        }
    
    }
    
    public void guardar(){
        if(!txtnombre.getText().isEmpty() || !txtedad.getText().isEmpty() 
                || !txtservicio.getText().isEmpty() || !txtprecio.getText().isEmpty())
        {
            capturardatos();
            try(Connection con = DriverManager.getConnection(conexion.getUrl(),conexion.getUser(),conexion.getPassword()))
            {
                
                
                if(nombre.equals(txtnombre.getText()) && edad == Integer.valueOf(txtedad.getText()) || costo == Integer.valueOf(txtprecio.getText()))
                {
                    
                    usuario u = new usuario(nombre,edad,costo);
                    
                    if(!u.getNombre().isEmpty()){
                        
                        // 1. insertar cliente
                        PreparedStatement pstm = con.prepareCall("call agregarclientes(?,?)");
                        pstm.setString(1, u.getNombre());
                        pstm.setInt(2, u.getEdad());
                        pstm.executeQuery();

                        // 2. insertar barbero

                        // 3. ahora sí, pedir los IDs (ya existen)
                        Statement stm = con.createStatement();
                        ResultSet rs1 = stm.executeQuery("call maxids()");
                        rs1.next();
                        int idCliente = rs1.getInt(1);


                        // 4. insertar el servicio con los IDs correctos
                        int indice = cmbbarbero.getSelectedIndex();
                        PreparedStatement pstm2 = con.prepareCall("call agregarservicio(?,?,now(),?,?)");
                        pstm2.setInt(1, n);
                        pstm2.setInt(2, u.getCosto());
                        pstm2.setInt(3, idCliente);
                        pstm2.setInt(4, indice );
                        pstm2.executeQuery();

                        JOptionPane.showMessageDialog(rootPane, "Guardado con exito");
                        n+=1;
                        principal.actualizarCuenta();
                    }
                    else
                    {
                        n -=1;
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(rootPane, "error de datos");
                }
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(rootPane, e.toString());
            }
        }

    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtedad = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtservicio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btmguardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtprecio = new javax.swing.JTextField();
        cmbbarbero = new javax.swing.JComboBox<>();

        setClosable(true);

        jLabel1.setText("Nombre:");

        txtnombre.setText("jTextField1");

        txtedad.setText("jTextField2");

        jLabel2.setText("Edad:");

        txtservicio.setText("jTextField3");

        jLabel3.setText("Servicio:");

        btmguardar.setText("Agregar");
        btmguardar.addActionListener(this::btmguardarActionPerformed);

        jLabel4.setText("Barbero:");

        jLabel5.setText("Precio:");

        txtprecio.setText("jTextField1");

        cmbbarbero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtedad, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                .addGap(17, 17, 17))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtservicio, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btmguardar))
                                .addContainerGap(93, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbbarbero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtservicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbbarbero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btmguardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btmguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmguardarActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_btmguardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmguardar;
    private javax.swing.JComboBox<String> cmbbarbero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtedad;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtprecio;
    private javax.swing.JTextField txtservicio;
    // End of variables declaration//GEN-END:variables
}
