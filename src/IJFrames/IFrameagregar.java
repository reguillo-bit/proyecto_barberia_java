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
import java.sql.ResultSetMetaData;
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
    int ids;
    int idCliente;
    JFrameprincipal principal;
    /**
     * Creates new form NewJInternalFrame
     */
    public IFrameagregar(JFrameprincipal principal) {
        initComponents();
        this.setTitle("agregar clientes");
        conexion = new infoconexion();
        this.principal = principal;
        llenarcombo();
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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
    // Validar que los campos no estén vacíos antes de procesar
    if(!txtnombre.getText().isEmpty() && !txtedad.getText().isEmpty() 
            && !txtservicio.getText().isEmpty() && !txtprecio.getText().isEmpty())
    {
        try(Connection con = DriverManager.getConnection(conexion.getUrl(), conexion.getUser(), conexion.getPassword()))
        {
            // Capturar datos de las cajas de texto de forma segura
            capturardatos();
            
            // Validar que se haya seleccionado un barbero válido del ComboBox
            int indice = cmbbarbero.getSelectedIndex();
            if(indice <= 0)
            {
                JOptionPane.showMessageDialog(rootPane, "Seleccione un barbero válido.");
                return;
            }
            
            // 1. Insertar el cliente (Usando .execute() en lugar de executeQuery())
            PreparedStatement pstm = con.prepareCall("call agregarclientes(?,?)");
            pstm.setString(1, nombre);
            pstm.setInt(2, edad);
            pstm.execute(); 
            pstm.close();

            // 2. Obtener el ID del cliente recién creado
            Statement stm1 = con.createStatement();
            ResultSet rs1 = stm1.executeQuery("call maxid()");
            if(rs1.next()){
                idCliente = rs1.getInt("ID");
            }
            rs1.close();
            stm1.close();
            
            // 3. Insertar el servicio (Exactamente 5 parámetros: turno, precio, fecha, id_cliente, id_barbero)
            int idbarbero = b.get(indice - 1).getId();
            PreparedStatement pstm2 = con.prepareCall("call agregarservicio(?,?,CURDATE(),?,?)");
            pstm2.setInt(1, n);                  // Turno
            pstm2.setDouble(2, costo);           // Precio del servicio
            pstm2.setInt(3, idCliente);          // ID del Cliente obtenido
            pstm2.setInt(4, idbarbero);          // ID del Barbero real
            pstm2.execute();
            pstm2.close();
            
            // Nota: El TRIGGER 'pagoss' en la base de datos se encarga automáticamente 
            // de insertar el registro en la tabla PAGOS al insertarse el servicio.
            
            principal.actualizarCuenta();
            JOptionPane.showMessageDialog(rootPane, "¡Guardado con éxito!");
            n += 1; // Incrementar el turno para el próximo registro
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(rootPane, "Error de BD: " + e.toString());
        }
    }
    else
    {
        JOptionPane.showMessageDialog(rootPane, "Por favor complete todos los campos.");
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
                                .addComponent(txtservicio, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(93, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbbarbero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btmguardar)
                        .addGap(62, 62, 62))))
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
