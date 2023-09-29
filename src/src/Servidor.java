import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;


/**
 * Implementando Runnable hace
 * que siempre esté a la escucha
 */
class VentanaServer extends JFrame implements Runnable{
    private JPanel panelSV;
    private JLabel serverLabel;
    public VentanaServer(){
        this.setBounds(500,200,400,500);
        setTitle("Servidor");

        componentesServer();

        /**
         * construimos el hilo para que siempre escuche
         */
        Thread mihilo = new Thread(this);
        mihilo.start();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void componentesServer(){
        colocarPanelSV();
        colocarEtiquetaServer();
    }
    private void colocarPanelSV(){
        panelSV = new JPanel();
        panelSV.setLayout(null);
        this.getContentPane().add(panelSV);
    }

    private void colocarEtiquetaServer(){
        serverLabel = new JLabel("Servidor",SwingConstants.CENTER);
        panelSV.add(serverLabel);
        serverLabel.setBounds(140,20,100,25);
        serverLabel.setForeground(Color.WHITE);
        serverLabel.setBackground(Color.BLACK);
        serverLabel.setFont(new Font("times new roman", Font.PLAIN,20));
        serverLabel.setOpaque(true);
    }


    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(9090);
            String nick, ip, mensaje;
            paqueteDatos paqueteRecibido;

            while(true) {

                /**
                 *que acepte las conexiones del exterior
                 */
                Socket misocket = servidor.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(misocket.getInputStream()));
                String jsonDatos = in.readLine();
                        /**tengo que revisar esto*/
                paqueteDatos datosCliente = paqueteDatos.fromJson(jsonDatos);
                nick = datosCliente.getNick();
                ip = datosCliente.getIp();
                mensaje = datosCliente.getMensaje();



                ObjectInputStream entradaDatos = new ObjectInputStream(misocket.getInputStream());
                paqueteRecibido= (paqueteDatos) entradaDatos.readObject();

                ip = paqueteRecibido.getIp();

                /**reenvio de datos*/

                int port;
                port = 9091;
                for (int i = port; i<9100;i++){
                    try{
                        Socket enviaDestinatario = new Socket(ip,i);

                        ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());

                        paqueteReenvio.writeObject(paqueteRecibido);

                        enviaDestinatario.close();
                    }
                    catch (IOException e) {}
                }
                /**cierra el flujo de datos*/
                misocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

class Node {
    private Object data;
    protected Node next;

    public Node(Object data){
        this.next = null;
        this.data = data;
    }
    public Object getData(){
        return this.data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return this.next;
    }
    public void setNext(Node next) {
        this.next = next;
    }
}

class LinkedList{
    private Node head;
    private int size;
    private Node tail;

    public LinkedList(){
        this.head=null;
        this.size=0;
        this.tail=null;
    }
    public int size() {
        return this.size;
    }
    public void insert(Object data){
        if(head==null){
            head=tail= new Node(data);
        }else{
            tail=tail.next = new Node(data);
        }
    }

    public void printMatrix(){
        Node current = this.head;
        while (current!=null) {
            LinkedList currentList = (LinkedList) current.getData();
            currentList.printList();
            System.out.println();
            current = current.getNext();
        }
    }
    public void printList(){
        Node current = this.head;
        while (current !=null) {
            System.out.print(current.getData());
            current = current.getNext();
        }
    }
}

class Matriz{
    private int n;
    private LinkedList matriz;
    private Node head;

    public Matriz(int n) {
        this.n = n;
        matriz = new LinkedList();
        formMatriz();
        this.head=null;
    }

    public void formMatriz(){
        int i;
        int j;
        for (i=0;i<n;i++) {
            LinkedList fila = new LinkedList();
            for (j = 0; j<n; j++) {
                fila.insert(j);
            }
            matriz.insert(fila);
        } matriz.printMatrix();
    }
}
public class Servidor {
    public static void main(String[] args) {
        /**instancia de la ventana de servidor
         */
        VentanaServer servidor = new VentanaServer();
        servidor.setVisible(true);

        Matriz matrizJuego = new Matriz(10);
    }
}