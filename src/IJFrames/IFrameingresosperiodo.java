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
 * Informe de ingresos por periodo: se elige un rango de fechas y se calculan
 * los ingresos generados (misma logica de IFramereportesfechas, pero con rango).
 */
public class IFrameingresosperiodo extends javax.swing.JInternalFrame {

    infoconexion conexion;
    long total = 0;

    public IFrameingresosperiodo() {
        initComponents();
        this.setTitle("Ingresos por periodo");
        conexion = new infoconexion();
        llenarcombos();
        nombrestabla();
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        lbltotal.setText(String.valueOf(0));
    }

    // las fechas disponibles son las fechas en las que hubo servicios
    public void llenarcombos()
    {
        cmbdesde.removeAllItems();
        cmbhasta.removeAllItems();
        try(Connection con = DriverManager.getConnection(conexion.getUrl()
                ,conexion.getUser(),conexion.getPassword()))
        {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select * from fechas order by FECHA");
            while(rs.next())
            {
                cmbdesde.addItem(rs.getString("FECHA"));
                cmbhasta.addItem(rs.getString("FECHA"));
            }
            if(cmbhasta.getItemCount() > 0)
            {
                cmbhasta.setSelectedIndex(cmbhasta.getItemCount() - 1);
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        }
    }

    public void nombrestabla()
    {
        try(Connection con = DriverManager.getConnection(conexion.getUrl()
               ,conexion.getUser(),conexion.getPassword()))
        {
            DefaultTableModel modelo = new DefaultTableModel();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("call nombrestabla()");
            ResultSetMetaData rsmt = rs.getMetaData();
            for(int i =2; i<= rsmt.getColumnCount();i++)
            {
                modelo.addColumn(rsmt.getColumnLabel(i));
            }
            tblingresos.setModel(modelo);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        }
    }

    public void llenartbla()
    {
        total = 0;
        if(cmbdesde.getSelectedItem() == null || cmbhasta.getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(rootPane, "No hay servicios registrados");
            return;
        }
        String desde = cmbdesde.getSelectedItem().toString();
        String hasta = cmbhasta.getSelectedItem().toString();
        if(desde.compareTo(hasta) > 0)
        {
            JOptionPane.showMessageDialog(rootPane, "La fecha inicial no puede ser mayor que la final");
            return;
        }
        try(Connection con = DriverManager.getConnection(conexion.getUrl()
                ,conexion.getUser(),conexion.getPassword()))
        {
            DefaultTableModel modelo = new DefaultTableModel();
            PreparedStatement pstm = con.prepareCall("CALL ingresos_por_periodo(?,?)");
            pstm.setString(1, desde);
            pstm.setString(2, hasta);
            ResultSet rs = pstm.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
            for(int i =2; i<= rsmt.getColumnCount();i++)
            {
                modelo.addColumn(rsmt.getColumnLabel(i));
            }
            while(rs.next())
            {
                String NOMBRE = rs.getString("NOMBRE");
                String SERVICIO = rs.getString("SERVICIO");
                int TURNO = rs.getInt("TURNO");
                String BARBERO = rs.getString("BARBERO");
                String FECHA = rs.getString("FECHAS");
                int PRECIO = rs.getInt("PRECIO");
                total += PRECIO;
                modelo.addRow(new Object[]{NOMBRE, SERVICIO, TURNO, BARBERO, FECHA, PRECIO});
            }
            lbltotal.setText(String.valueOf(total));
            tblingresos.setModel(modelo);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        }
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel("Desde:");
        jLabel2 = new javax.swing.JLabel("Hasta:");
        jLabel3 = new javax.swing.JLabel("Total ingresos:");
        lbltotal = new javax.swing.JLabel("0");
        cmbdesde = new javax.swing.JComboBox<>();
        cmbhasta = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton("Consultar");
        tblingresos = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane(tblingresos);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.JPanel norte = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        norte.add(jLabel1);
        norte.add(cmbdesde);
        norte.add(jLabel2);
        norte.add(cmbhasta);
        norte.add(jButton1);
        javax.swing.JPanel sur = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        sur.add(jLabel3);
        sur.add(lbltotal);

        getContentPane().add(norte, java.awt.BorderLayout.NORTH);
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
        getContentPane().add(sur, java.awt.BorderLayout.SOUTH);
        setSize(700, 360);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        llenartbla();
    }

    private javax.swing.JComboBox<String> cmbdesde;
    private javax.swing.JComboBox<String> cmbhasta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbltotal;
    private javax.swing.JTable tblingresos;
}
