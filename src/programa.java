import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class programa extends JFrame implements ActionListener {
    private JPanel panel1;
    private JComboBox<String> combo;
    private JLabel labelFotos;
    private ImageIcon foto;
    private JPanel panel2;
    private JCheckBox casilla;
    private JTextField comentario;
    private JPanel panel3;
    private JButton boton;
    private ActionListener eventoImagenes;
    public programa() {


        //String contraseña = JOptionPane.showInputDialog(this,"input password", "entrada", JOptionPane.QUESTION_MESSAGE);
        String contraseña2 = "damocles";
        if (contraseña2 != null && contraseña2.equals("damocles")) {

            this.setTitle("Swing - Example 2");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800,600);


            // INICIALIZO LAS COSAS
            panel1 = new JPanel();
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

            labelFotos = new JLabel(foto);
            combo = new JComboBox<>();

            panel2 = new JPanel();
            panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
            casilla = new JCheckBox("Save your comment");
            comentario = new JTextField(30);

            panel3 = new JPanel();
            panel3.setLayout(new FlowLayout(FlowLayout.CENTER));


            panel1.setBorder(new EmptyBorder(20, 20, 20, 20));
            //panel2.setBorder(BorderFactory.createLineBorder(Color.red));
            //labelFotos.setBorder(BorderFactory.createLineBorder(Color.orange));

            combo.setPreferredSize(new Dimension(300, 30));
            combo.setMaximumSize(new Dimension(300, combo.getPreferredSize().height));

            eventoImagenes = new ComboListener();
            combo.addActionListener(eventoImagenes);

            combo.addItem(load_combo(1));
            combo.addItem(load_combo(2));
            combo.addItem(load_combo(3));

            labelFotos.setHorizontalAlignment(JLabel.LEFT);

            casilla.setSelected(true);

            boton = new JButton("SAVE");
            boton.addActionListener(this);


            panel2.add(casilla);
            panel2.add(comentario);
            panel3.add(boton);
            panel1.add(combo);
            panel1.add(labelFotos);
            panel1.add(panel2);
            panel1.add(panel3);

            add(panel1);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    JOptionPane.showMessageDialog(null,"¡Adios!");
                        System.exit(0);
                }
            });

            setVisible(true);
        } else {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton) {
            String imagenActual = (String) combo.getSelectedItem();
            try {
                FileWriter writer = new FileWriter("src/Comentarios/"+imagenActual+".txt", true);
                BufferedWriter bw = new BufferedWriter(writer);
                if (casilla.isSelected()) {
                    bw.write(imagenActual + ": " + comentario.getText());
                } else {
                    bw.write(imagenActual);
                }
                bw.close();
                writer.close();
            } catch (IOException ex) {
                System.err.println("Error al escribir en el archivo: " + ex.getMessage());
            }
        }
    }
    public class ComboListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == combo) {
                    String opcion = (String) combo.getSelectedItem();
                    String carpeta = "src/Fotos/";
                    switch (opcion) {
                        case "foto1":
                            foto = new ImageIcon(carpeta+"foto1.png");
                            break;
                        case "foto2":
                            foto = new ImageIcon(carpeta+"foto2.jpg");
                            break;
                        case "foto3":
                            foto = new ImageIcon(carpeta+"foto3.png");
                            break;
                    }
                    Image img = foto.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                    foto = new ImageIcon(img);
                    labelFotos.setIcon(foto);
                }
            } catch (Exception ex) {
                JOptionPane.showOptionDialog(combo,"Error. el archivo no existe","ERROR",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,null,null);
            }
        }
    }
    public String load_combo(int n) {
            switch (n) {
                case 1:
                    return "foto1";
                case 2:
                    return "foto2";
                case 3:
                    return "foto3";
            }
        return null;
    }
}
