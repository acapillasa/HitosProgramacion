import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PictureViewer extends JFrame implements ActionListener {

    private JPanel panel;
    private JPanel comboPanel;
    private JPanel fechaPanel;
    private JPanel listaPanel;
    private GridLayout tabla;
    private Fotografos f;
    private JComboBox<String> combo;
    private JXDatePicker fecha;
    private JList<String> lista;
    private DefaultListModel<String> modelo;
    private JScrollPane scroll;
    private JLabel imagenLabel;
    private ConexionBD bd;
    private Date guardarFecha;
    private SimpleDateFormat fechaTransformacion;
    private String fechaFormateada;
    private JButton award;
    private JButton remove;

    public PictureViewer() {

        // ESENCIAL
        super("Fotografía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,700);

        // PANEL
        panel = new JPanel();
        panel.setBorder(new LineBorder(Color.ORANGE, 2));

        // GRID LAYOUT
        tabla = new GridLayout(3,2);


        // PRIMER AREA DEL GRID LAYOUT
        award = new JButton("AWARD");
        award.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon icono = new ImageIcon("src/imagenes/Logo_mugiwara.png");
                String visitasMinimasPremiadas = (String) JOptionPane.showInputDialog(null, "Número mínimo de visitas para conseguir un premio:", "$Premiar$", JOptionPane.INFORMATION_MESSAGE, icono, null, null);
                int visitasMinimasPremiadasNumero = Integer.parseInt(visitasMinimasPremiadas);
                f.abrirConexion();

                for (int id : f.createVisitasMap().keySet()) {

                    if (visitasMinimasPremiadasNumero < f.createVisitasMap().get(id)) {
                        String sql = "UPDATE fotografos SET galardonado = 1 WHERE fotografoId = "+id;
                        try {
                            f.setPs(f.getCon().prepareStatement(sql));
                            f.getPs().executeUpdate();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                f.cerrarConexion();
            }
        });

        // SEGUNDO AREA DEL GRID LAYOUT
        remove = new JButton("REMOVE");

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bd.abrirConexion();
                Map<Integer,String> mapaNoPremiados = new HashMap<>(bd.fotosNo(bd.listaNoPremiados()));

                for (Map.Entry<Integer,String> entry : mapaNoPremiados.entrySet()) {
                    Integer key = entry.getKey();
                    String value = entry.getValue();

                    int option = JOptionPane.showOptionDialog(null, "La foto " + value + " no tiene ninguna visita. ¿Deseas eliminarla?", "Eliminar fotos", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Sí", "No"}, "No");
                    if (option == JOptionPane.YES_OPTION) {
                        int idfotogr = bd.idFotografoConIdFoto(key);
                        bd.eliminarFoto(key);
                        JOptionPane.showMessageDialog(null, "La foto " + value + " ha sido eliminada correctamente.", "Foto eliminada", JOptionPane.INFORMATION_MESSAGE);

                            if (bd.fotografoSinFotos(idfotogr) == 0) {
                                bd.eliminarFotografo(idfotogr);
                                JOptionPane.showMessageDialog(null, "el fotografo con id " + idfotogr + " no tiene ninguna foto y ha sido eliminado.", "Fotografo Eliminado", JOptionPane.INFORMATION_MESSAGE);
                            }
                    }


                }
                bd.cerrarConexion();
            }
        });


        // TERCER AREA DEL GRID LAYOUT
        comboPanel = new JPanel();
        comboPanel.setBorder(new LineBorder(Color.RED, 2));

        combo = new JComboBox<>();

        f = new Fotografos();
        f.abrirConexion();
        List<Fotografos> af = new ArrayList<>(f.selectFotografos());
        for (int i = 0; i < af.size(); i++) {
            combo.addItem(af.get(i).getNombre());
        }
        f.cerrarConexion();

        combo.setSelectedIndex(-1);
        combo.addActionListener(this);

        // CUARTO AREA DEL GRID LAYOUT
        fechaPanel = new JPanel();
        fechaPanel.setBorder(new LineBorder(Color.BLUE,2));
        fecha = new JXDatePicker();
        fecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFecha = fecha.getDate();
                fechaTransformacion = new SimpleDateFormat("yyyy-MM-dd");
                fechaFormateada = fechaTransformacion.format(guardarFecha);
            }
        });


        // QUINTO AREA DEL GRID LAYOUT
        listaPanel = new JPanel();
        lista = new JList<>();

        scroll = new JScrollPane(lista,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()/4));



        modelo = new DefaultListModel<>();
        try {
            bd = new ConexionBD();
            bd.abrirConexion();
            for (int i = 0; i < bd.selectNombreFotos().size(); i++) {
                modelo.addElement(bd.selectNombreFotos().get(i));
            }
            bd.cerrarConexion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelo.clear();
        lista.setModel(modelo);


        // SEXTO AREA DEL GRID LAYOUT
        imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);

        lista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String nombreFoto = lista.getSelectedValue();
                    String rutaImagen = "src/imagenes/" + nombreFoto + ".jpg";
                    ImageIcon imagen = new ImageIcon(rutaImagen);
                    Image imagenEscalada = imagen.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
                    imagen = new ImageIcon(imagenEscalada);
                    imagenLabel.setIcon(imagen);
                    bd.abrirConexion();
                    bd.icrementarVisitas(nombreFoto);
                    bd.cerrarConexion();
                }
            }
        });

        // AÑADIR A LOS PANELES
        comboPanel.add(new JLabel("Selecciona un fotógrafo:"));
        comboPanel.add(combo);

        fechaPanel.add(fecha);

        listaPanel.add(scroll);

        panel.setLayout(tabla);
        panel.add(award);
        panel.add(remove);
        panel.add(comboPanel);
        panel.add(fechaPanel);
        panel.add(listaPanel);
        panel.add(imagenLabel);
        add(panel);


        // ESENCIAL
        setLocationRelativeTo(null); // Centrar ventana en pantalla
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == combo) {
                if (fechaFormateada == null) {
                    String fotografoSeleccionado = (String) combo.getSelectedItem();
                    bd.abrirConexion();
                    List<String> nombresFotos = bd.selectNombreFotosPorFotografo(fotografoSeleccionado);
                    bd.cerrarConexion();
                    modelo.clear();
                    for (int i = 0; i < nombresFotos.size(); i++) {
                        modelo.addElement(nombresFotos.get(i));
                    }
                    lista.repaint();
                }
                else {
                    String fotografoSeleccionado = (String) combo.getSelectedItem();
                    bd.abrirConexion();
                    List<String> nombresFotos = bd.selectNombreFotosPorFotografo(fotografoSeleccionado,fechaFormateada);
                    bd.cerrarConexion();
                    modelo.clear();
                    for (int i = 0; i < nombresFotos.size(); i++) {
                        modelo.addElement(nombresFotos.get(i));
                    }
                    lista.repaint();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new PictureViewer();
    }
}
