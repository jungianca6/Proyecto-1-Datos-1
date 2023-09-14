import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

class VentanaClient extends JFrame{
    private JPanel panel;
    private JLabel etiqueta;
    private JTextField chatTexto;
    private JButton boton;
    private JTextField cajaChat;


    public VentanaClient(){
        this.setBounds(500,200,400,400);
        setTitle("Cliente");

        Componentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void Componentes(){
        colocarPanel();
        colocarEtiqueta();
        colocarCajadeTexto();
        colocarBoton();
        colocarArea();
    }
    private void colocarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
    }
    private void colocarEtiqueta(){
        etiqueta = new JLabel("Cliente",SwingConstants.CENTER);
        panel.add(etiqueta);
        etiqueta.setBounds(140,10,100,25);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setBackground(Color.BLACK);
        etiqueta.setFont(new Font("times new roman", Font.PLAIN,20));
        etiqueta.setOpaque(true);
    }
    private void colocarCajadeTexto(){
        chatTexto = new JTextField();
        chatTexto.setBounds(60,50,250,20);
        panel.add(chatTexto);
    }
    private void colocarBoton(){
        boton = new JButton("Enviar");
        boton.setBounds(140,80,100,30);
        panel.add(boton);
        boton.setEnabled(true);

    private void colocarArea(){
        cajaChat = new JTextField();
        cajaChat.setBounds(0,50,400,100);
        }

        ActionListener enviaTexto = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    /**creacion del socket
                    */
                    Socket miSocket = new Socket("localhost",9090);
                    /**
                     flujo de datos de salida que circula por el socket.
                     *Puente virtual
                     */
                    DataOutputStream flujosalida = new DataOutputStream(miSocket.getOutputStream());
                    /**
                     * en el flujo de datos va a viajar el texto
                     * capturado del campo de texto
                     */
                    flujosalida.writeUTF(chatTexto.getText());
                    /**cierra el flujo de datos*/
                    flujosalida.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        boton.addActionListener(enviaTexto);
    }
}

public class Cliente {
    public static void main(String[] args){
        /**instancia de la ventana de servidor
         */
        VentanaClient v1 = new VentanaClient();
        v1.setVisible(true);
    }
}
