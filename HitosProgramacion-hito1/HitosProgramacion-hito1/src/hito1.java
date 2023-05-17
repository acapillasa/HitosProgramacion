import javax.swing.*;
import java.awt.*;

public class hito1 {
    public static void main(String[] args) {
        JFrame programa = new JFrame("Try yourself");

        programa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        programa.setSize(800,500);


        JPanel contenedorNorte = new JPanel();
        JPanel contenedorSur = new JPanel();
        JPanel contenedorEste = new JPanel();
        JPanel contenedorCentral = new JPanel();
        programa.getContentPane().add(contenedorNorte,BorderLayout.NORTH);
        programa.getContentPane().add(contenedorSur,BorderLayout.SOUTH);
        programa.getContentPane().add(contenedorEste,BorderLayout.EAST);
        programa.getContentPane().add(contenedorCentral,BorderLayout.CENTER);

        //PARTE NORTE
        contenedorNorte.setLayout(new FlowLayout(FlowLayout.CENTER));

        //checkBox1
        JCheckBox cajita1 = new JCheckBox("Katniss");
        cajita1.setSize(100,50);
        contenedorNorte.add(cajita1);

        //checkBox2
        JCheckBox cajita2 = new JCheckBox("Peeta");
        cajita2.setSize(100,50);
        contenedorNorte.add(cajita2);


        //PARTE ESTE
        Dimension dimension = new Dimension(250,0);
        contenedorEste.setPreferredSize(dimension);
        contenedorEste.setMinimumSize(dimension);
        contenedorEste.setMaximumSize(dimension);

        contenedorEste.setLayout(null);
        contenedorEste.setAlignmentY(Component.CENTER_ALIGNMENT);

        JRadioButton opcion1 = new JRadioButton("OPT 1",true);
        JRadioButton opcion2 = new JRadioButton("OPT 2");
        JRadioButton opcion3 = new JRadioButton("OPT 3");

        int y = 150;
        contenedorEste.add(Box.createVerticalGlue());
        JRadioButton[] arrayBotones = {opcion1,opcion2,opcion3};
        ButtonGroup grupoBotones = new ButtonGroup();
        for (int i = 0; i < arrayBotones.length; i++) {
            grupoBotones.add(arrayBotones[i]);
            contenedorEste.add(arrayBotones[i]);
            arrayBotones[i].setBounds(50, y, 100, 20);
            y = y + 40;
        }


        //PARTE SUR
        contenedorSur.setLayout(new BoxLayout(contenedorSur,BoxLayout.X_AXIS));
        contenedorSur.setPreferredSize(new Dimension(0,50));

        JButton boton1 = new JButton("But 1");
        JButton boton2 = new JButton("But 2");

        contenedorSur.add(boton1);
        contenedorSur.add(boton2);

        //PARTE CENTRAL
        contenedorCentral.setLayout(new GridLayout(2,2));
        ImageIcon imagen = new ImageIcon("imagen.jpg","imagen1");
        Image image = imagen.getImage();
        Image newimage = image.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        imagen = new ImageIcon(newimage);

        JLabel ventanaImagen1 = new JLabel(imagen);
        JLabel ventanaImagen2 = new JLabel(imagen);
        JLabel ventanaImagen3 = new JLabel(imagen);
        JLabel ventanaImagen4 = new JLabel(imagen);

        contenedorCentral.add(ventanaImagen1);
        contenedorCentral.add(ventanaImagen2);
        contenedorCentral.add(ventanaImagen3);
        contenedorCentral.add(ventanaImagen4);


        //Ponerlo siempre abajo para que aparezcan las cosas
        programa.setVisible(true);
    }
}