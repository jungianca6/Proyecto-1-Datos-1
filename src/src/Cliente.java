import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;




class VentanaClient extends JFrame implements Runnable{
    private JPanel panel;
    private JLabel etiqueta;
    private JTextField chatTexto,nick,ip;
    private JButton boton;
    private JTextArea cajaChat;


    public VentanaClient(){
        this.setBounds(500,200,400,500);
        setTitle("Cliente");

        ComponentesCliente();

        Thread mihilo = new Thread(this);
        mihilo.start();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void ComponentesCliente(){
        colocarPanel();
        colocarEtiqueta();
        colocarCajadeTexto();
        colocarBoton();
        colocarAreaText();
    }

    private void colocarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
    }
    private void colocarEtiqueta(){
        etiqueta = new JLabel("Cliente",SwingConstants.CENTER);
        panel.add(etiqueta);
        etiqueta.setBounds(140,20,100,25);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setBackground(Color.BLACK);
        etiqueta.setFont(new Font("times new roman", Font.PLAIN,20));
        etiqueta.setOpaque(true);
    }
    private void colocarCajadeTexto(){
        chatTexto = new JTextField();
        chatTexto.setBounds(60,430,250,20);
        panel.add(chatTexto);

        nick = new JTextField();
        nick.setBounds(30,50,100,25);
        ip = new JTextField();
        ip.setBounds(270,50,100,25);

        panel.add(nick);
        panel.add(ip);
    }
    private void colocarAreaText(){
        cajaChat = new JTextArea();
        cajaChat.setBounds(0,130,400,250);
        panel.add(cajaChat);
        cajaChat.setEditable(true);
    }
    private void colocarBoton(){
        boton = new JButton("Enviar");
        boton.setBounds(140,390,100,30);
        panel.add(boton);
        boton.setEnabled(true);

        ActionListener enviaTexto = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    /**creacion del socket
                    */
                    Socket miSocket = new Socket("localhost",9090);

                    paqueteDatos datos = new paqueteDatos();

                    datos.setNick(nick.getText());
                    datos.setIp(ip.getText());
                    datos.setMensaje(chatTexto.getText());

                    ObjectOutputStream salidaDatos = new ObjectOutputStream(miSocket.getOutputStream());
                    salidaDatos.writeObject(datos);

                    // Convierte los datos a JSON
                    String jsonDatos = datos.toJson();

                    // Envía el JSON al servidor
                    PrintWriter out = new PrintWriter(miSocket.getOutputStream(), true);
                    out.println(jsonDatos);

                    miSocket.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        boton.addActionListener(enviaTexto);
    }

    /**
     * hilo que permite que este siempre a la escucha
     */
    @Override
    public void run() {
        int port;
        port = 9091;
        for (int i = port; i<9100;i++){
            try {
                ServerSocket servidorcliente = new ServerSocket(i);
                Socket cliente;
                paqueteDatos packRecibido;

                while(true){
                    cliente = servidorcliente.accept();
                    ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());
                    packRecibido = (paqueteDatos) flujoentrada.readObject();
                    cajaChat.append("\n"+ "(" + packRecibido.getIp() + ") " + packRecibido.getNick() + ": " + packRecibido.getMensaje());
                }
            } catch (IOException | ClassNotFoundException e) {
                //throw new RuntimeException(e);
            }
        }
    }
}

/**
 * Permite almacenar información a estos atributos
 * obtener esa información.
 * El Serializable indica que todas las instancias que
   pertecenen a esta clase pueden convertirse en una serie de bytes
 */
class paqueteDatos implements Serializable{
    private String nick, ip, mensaje;
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    // Métodos para convertir a JSON y desde JSON
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static paqueteDatos fromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, paqueteDatos.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
public class Cliente {
    public static void main(String[] args){
        /**instancia de la ventana de servidor
         */
        VentanaClient cliente1 = new VentanaClient();
        cliente1.setVisible(true);
    }
}
