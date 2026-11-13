package IJFrames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import objetos.infoconexion;

/**
 * Informe de servicios por barbero: cantidad de servicios realizados por cada
 * barbero y el valor que generaron.
 */
public class IFrameserviciosbarbero extends javax.swing.JInternalFrame {

    infoconexion conexion;

    public IFrameserviciosbarbero() {
        initComponents();
        this.setTitle("Servicios por barbero");
        conexion = new infoconexion();
        llenartbla();
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
    }

    public void llenartbla()
    {
        long cantidad = 0;
        long total = 0;
        try(Connection con = DriverManager.getConnection(conexion.getUrl()
                ,conexion.getUser(),conexion.getPassword()))
        {
            DefaultTableModel modelo = new DefaultTableModel();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("call servicios_por_barbero()");
            ResultSetMetaData rsmt = rs.getMetaData();
            for(int i =1; i<= rsmt.getColumnCount();i++)
            {
                modelo.addColumn(rsmt.getColumnLabel(i));
            }
            while(rs.next())
            {
                String BARBERO = rs.getString("BARBERO");
                int SERVICIOS = rs.getInt("SERVICIOS");
                int VALOR = rs.getInt("VALOR");
                cantidad += SERVICIOS;
                total += VALOR;
                modelo.addRow(new Object[]{BARBERO, SERVICIOS, VALOR});
            }
            tblbarberos.setModel(modelo);
            lblcantidad.setText(String.valueOf(cantidad));
            lbltotal.setText(String.valueOf(total));
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        }
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel("Total servicios:");
        jLabel2 = new javax.swing.JLabel("Valor generado:");
        lblcantidad = new javax.swing.JLabel("0");
        lbltotal = new javax.swing.JLabel("0");
        jButton1 = new javax.swing.JButton("Actualizar");
        tblbarberos = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane(tblbarberos);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.JPanel norte = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        norte.add(jButton1);
        javax.swing.JPanel sur = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        sur.add(jLabel1);
        sur.add(lblcantidad);
        sur.add(jLabel2);
        sur.add(lbltotal);

        getContentPane().add(norte, java.awt.BorderLayout.NORTH);
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
        getContentPane().add(sur, java.awt.BorderLayout.SOUTH);
        setSize(560, 320);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        llenartbla();
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblcantidad;
    private javax.swing.JLabel lbltotal;
    private javax.swing.JTable tblbarberos;
}
