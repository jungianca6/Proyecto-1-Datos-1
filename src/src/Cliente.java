import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

class VentanaClient extends JFrame{
    public VentanaClient(){
        this.setBounds(500,200,400,400);
        setTitle("Cliente");
        Componentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void Componentes(){
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        //panel.setBackground(new Color(118,181,197));

        JLabel etiqueta = new JLabel("Cliente",SwingConstants.CENTER);
        panel.add(etiqueta);
        etiqueta.setBounds(140,10,100,25);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setBackground(Color.BLACK);
        etiqueta.setFont(new Font("times new roman", Font.PLAIN,20));

        JTextField chatTexto = new JTextField();
        chatTexto.setBounds(60,50,250,20);
        panel.add(chatTexto);

        JButton boton = new JButton("Enviar");
        boton.setBounds(140,80,100,30);
        panel.add(boton);

        etiqueta.setOpaque(true);
        panel.setLayout(null);
        boton.setEnabled(true);

        ActionListener enviaTexto = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println(chatTexto.getText());;
            }
        };
        boton.addActionListener(enviaTexto);
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
