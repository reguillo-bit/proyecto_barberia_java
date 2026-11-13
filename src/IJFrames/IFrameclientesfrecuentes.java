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
 * Informe de clientes frecuentes: clientes con mayor numero de visitas
 * durante un periodo (rango de fechas).
 */
public class IFrameclientesfrecuentes extends javax.swing.JInternalFrame {

    infoconexion conexion;

    public IFrameclientesfrecuentes() {
        initComponents();
        this.setTitle("Clientes frecuentes");
        conexion = new infoconexion();
        llenarcombos();
        llenartbla();
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

    public void llenartbla()
    {
        if(cmbdesde.getSelectedItem() == null || cmbhasta.getSelectedItem() == null)
        {
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
            PreparedStatement pstm = con.prepareCall("CALL clientes_frecuentes(?,?)");
            pstm.setString(1, desde);
            pstm.setString(2, hasta);
            ResultSet rs = pstm.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
            for(int i =1; i<= rsmt.getColumnCount();i++)
            {
                modelo.addColumn(rsmt.getColumnLabel(i));
            }
            while(rs.next())
            {
                String NOMBRE = rs.getString("NOMBRE");
                int EDAD = rs.getInt("EDAD");
                int VISITAS = rs.getInt("VISITAS");
                int TOTAL = rs.getInt("TOTAL");
                String ULTIMA = rs.getString("ULTIMA_VISITA");
                modelo.addRow(new Object[]{NOMBRE, EDAD, VISITAS, TOTAL, ULTIMA});
            }
            tblfrecuentes.setModel(modelo);
            lblcantidad.setText(String.valueOf(modelo.getRowCount()));
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        }
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel("Desde:");
        jLabel2 = new javax.swing.JLabel("Hasta:");
        jLabel3 = new javax.swing.JLabel("Clientes listados:");
        lblcantidad = new javax.swing.JLabel("0");
        cmbdesde = new javax.swing.JComboBox<>();
        cmbhasta = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton("Consultar");
        tblfrecuentes = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane(tblfrecuentes);

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
        sur.add(lblcantidad);

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
    private javax.swing.JLabel lblcantidad;
    private javax.swing.JTable tblfrecuentes;
}
