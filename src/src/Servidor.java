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
    private JTextArea areaTextoSV;
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
        colocarAreaTexto();
    }
    private void colocarPanelSV(){
        panelSV = new JPanel();
        panelSV.setLayout(null);
        this.getContentPane().add(panelSV);
    }

    private void colocarAreaTexto(){
        areaTextoSV = new JTextArea();
        areaTextoSV.setBounds(0,100,400,200);
        panelSV.add(areaTextoSV);
        areaTextoSV.setEditable(true);
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

                ObjectInputStream entradaDatos = new ObjectInputStream(misocket.getInputStream());
                paqueteRecibido= (paqueteDatos) entradaDatos.readObject();

                nick = paqueteRecibido.getNick();
                ip = paqueteRecibido.getIp();
                mensaje = paqueteRecibido.getMensaje();

                areaTextoSV.append("\n"+ "("+ ip +") " + nick + ": " + mensaje );

                Socket enviaDestinatario = new Socket(ip,9091);

                ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());

                paqueteReenvio.writeObject(paqueteRecibido);

                enviaDestinatario.close();
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

public class Servidor {
    public static void main(String[] args) throws IOException {
        /**instancia de la ventana de servidor
         */
        VentanaServer v1 = new VentanaServer();
        v1.setVisible(true);

        LinkedList matriz1 = new LinkedList();
        int i;
        int j;
        for (i=0;i<3;i++) {
            LinkedList fila = new LinkedList();
            for (j = 0; j<3; j++) {
                fila.insert(j);
            }
            matriz1.insert(fila);
        }
        matriz1.printMatrix();

        LinkedList matriz2 = new LinkedList();
        for (i=0;i<5;i++) {
            LinkedList fila = new LinkedList();
            for (j = 0; j<5; j++) {
                fila.insert(j);
            }
            matriz2.insert(fila);
        }
        matriz2.printMatrix();

        LinkedList matriz3 = new LinkedList();
        for (i=0;i<10;i++) {
            LinkedList fila = new LinkedList();
            for (j = 0; j<10; j++) {
                fila.insert(j);
            }
            matriz3.insert(fila);
        }
        matriz3.printMatrix();
    }
}