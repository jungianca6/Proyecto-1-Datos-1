import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

class VentanaClient extends JFrame{
    public VentanaClient(){
        this.setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Cliente");
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
