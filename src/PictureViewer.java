import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class PictureViewer extends JFrame implements ActionListener {

    private JPanel panel;
    private JPanel comboPanel;
    private JPanel fechaPanel;
    private JPanel listaPanel;
    private GridLayout tabla;
    private JComboBox<String> combo;
    private JXDatePicker fecha;
    private JList<String> lista;
    private DefaultListModel<String> modelo;
    private JScrollPane scroll;
    private JLabel imagenLabel;
    private ConexionBD bd;

    public PictureViewer() {

        // ESENCIAL
        super("Fotografía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);

        // PANEL
        panel = new JPanel();
        panel.setBorder(new LineBorder(Color.ORANGE, 2));

        // GRID LAYOUT
        tabla = new GridLayout(2,2);


        // PRIMER AREA DEL GRID LAYOUT
        comboPanel = new JPanel();
        comboPanel.setBorder(new LineBorder(Color.RED, 2));

        combo = new JComboBox<>();

        Fotografos f = new Fotografos();
        f.abrirConexion();
        ArrayList<Fotografos> af = new ArrayList<>(f.selectFotografos());
        for (int i = 0; i < af.size(); i++) {
            combo.addItem(af.get(i).getNombre());
        }
        f.cerrarConexion();

        combo.setSelectedIndex(-1);
        combo.addActionListener(this);

        // SEGUNDO AREA DEL GRID LAYOUT
        fechaPanel = new JPanel();
        fechaPanel.setBorder(new LineBorder(Color.BLUE,2));
        fecha = new JXDatePicker();


        // TERCER AREA DEL GRID LAYOUT
        listaPanel = new JPanel();
        lista = new JList<>();

        scroll = new JScrollPane(lista,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(300, 200));

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


        // CUARTO AREA DEL GRID LAYOUT
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
                }
            }
        });

        // AÑADIR A LOS PANELES
        comboPanel.add(new JLabel("Selecciona un fotógrafo:"));
        comboPanel.add(combo);

        fechaPanel.add(fecha);

        listaPanel.add(scroll);

        panel.setLayout(tabla);
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
                String fotografoSeleccionado = (String) combo.getSelectedItem();
                bd.abrirConexion();
                ArrayList<String> nombresFotos = bd.selectNombreFotosPorFotografo(fotografoSeleccionado);
                bd.cerrarConexion();
                modelo.clear();
                for (int i = 0; i < nombresFotos.size(); i++) {
                    modelo.addElement(nombresFotos.get(i));
                }
                lista.repaint();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new PictureViewer();
    }
}
