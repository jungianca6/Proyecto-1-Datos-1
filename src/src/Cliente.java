import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

class VentanaClient extends JFrame{
    public VentanaClient(){
        this.setBounds(500,200,300,400);
        setTitle("Cliente");
        Componentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void Componentes(){
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setBackground(new Color(118,181,197));

        JLabel etiqueta = new JLabel("Hola",SwingConstants.CENTER);
        panel.add(etiqueta);
        etiqueta.setBounds(50,10,50,30);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setBackground(Color.BLACK);
        etiqueta.setFont(new Font("times new roman", Font.PLAIN,20));

        etiqueta.setOpaque(true);
        panel.setLayout(null);


    }
}
public class Cliente {
    public static void main(String[] args) {
        /**instancia de la ventana de servidor
         */
        VentanaClient v1 = new VentanaClient();
        v1.setVisible(true);
    }
}
