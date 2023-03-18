import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.ExceptionListener;
import java.io.*;

public class programa extends JFrame implements ActionListener {
    private JComboBox<String> combo;
    private JPanel panel;
    private JPanel panelDoble;
    private JPanel panelderecha;
    private JButton boton1;
    private JButton boton2;
    private JTextArea texto;
    private ActionListener borrarListener;
    private JScrollPane pScroll;
    public programa() {
        // LO ESENCIAL
        this.setTitle("Test Events: File");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,400);

        texto = new JTextArea();
        combo = new JComboBox<>();

        // JPANEL IZQUIERDA: COMBOBOX y BOTON
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.red));

        panelDoble = new JPanel();
        panelDoble.setBorder(BorderFactory.createLineBorder(Color.orange));

                // COMBOBOX
        combo.setPreferredSize(new Dimension(300,30));
        combo.addActionListener(this);


        combo.addItem("");
        combo.addItem("python.txt");
        combo.addItem("c.txt");
        combo.addItem("java.txt");
                // BOTON BORRAR
        boton1 = new JButton("Clear");

        borrarListener = new botonBorrarListener();
        boton1.addActionListener(borrarListener);

            // AÑADIR AL PANEL
        panelDoble.add(combo);
        panelDoble.add(boton1);
        panel.add(panelDoble);


        //JPANEL DERECHA: SUPERTEXTO y BOTON
        panelderecha = new JPanel();
        panelderecha.setLayout(new BoxLayout(panelderecha, BoxLayout.Y_AXIS));
        panelderecha.setBorder(BorderFactory.createLineBorder(Color.green));

                // SUPER TEXTO CON SCROLLPANE

        pScroll = new JScrollPane(texto, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pScroll.setPreferredSize(new Dimension(300,300));
        texto.setLineWrap(true);
        texto.setEditable(false);

        panelderecha.add(pScroll);

                // BOTON CERRAR
        boton2 = new JButton("Cerrar");
        boton2.addActionListener(this);

        panelderecha.add(boton2);

        panel.add(panelderecha);


        // AÑADIR_TODO AL FRAME
        add(panel,BorderLayout.CENTER);


        // ESENCIAL TAMBIEN
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == combo) {
                String opcion = (String) combo.getSelectedItem();
                switch (opcion) {
                    case "":
                        texto.setText("");
                        break;
                    case "python.txt":
                        String[] arrayPython = cargarOpcionesDesdeArchivo("src/python.tx");
                        String todoPython = "";
                        for (int i = 0; i < arrayPython.length; i++) {
                            todoPython = todoPython + arrayPython[i] + "\n";
                        }
                        texto.setText(todoPython);
                        break;
                    case "c.txt":
                        String[] arrayC = cargarOpcionesDesdeArchivo("src/c.txt");
                        String todoC = "";
                        for (int i = 0; i < arrayC.length; i++) {
                            todoC = todoC + arrayC[i] + "\n";
                        }
                        texto.setText(todoC);
                        break;
                    case "java.txt":
                        String[] arrayJava = cargarOpcionesDesdeArchivo("src/java.txt");
                        String todoJava = "";
                        for (int i = 0; i < arrayJava.length; i++) {
                            todoJava = todoJava + arrayJava[i] + "\n";
                        }
                        texto.setText(todoJava);
                        break;
                }
            } else {
                System.exit(0);
            }
        } catch (Exception ex) {
            JOptionPane.showOptionDialog(combo,"Error. el archivo no existe","ERROR",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,null,null);

        }
    }

    public String[] cargarOpcionesDesdeArchivo(String rutaArchivo) {
        try {
            // CREO UN BUFFEREDREADER PARA LEER
            BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));

            // CUENTO EL NUMERO DE LINEAS DEL ARCHIVO
            int numLineas = 0;
            while (reader.readLine() != null) {
                numLineas++;
            }
            reader.close();

            // CREO UN ARRAY DEL TAMAÑO DE LAS LINEAS
            String[] opciones = new String[numLineas];

            // VOY LEYENDO LAS LINEAS Y METIENDOLAS EN EL ARRAY
            reader = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
            int i = 0;
            while ((linea = reader.readLine()) != null) {
                opciones[i] = linea;
                i++;
            }
            reader.close();

            // RETURN EL ARRAY CON LAS LINEAS
            return opciones;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public class botonBorrarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            texto.setText("");
        }
    }

}
