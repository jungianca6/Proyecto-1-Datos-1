import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * Esta clase crea la ventana de la interfaz gráfica del servidor,
 * así como sus componentes.
 * Implementando Runnable hace
 * que siempre esté a la escucha
 * Atributos: JPanel panelSV y JLabel serverLabel
 * Metodos:
 * componentesServer(): void
 * colocarPanelSV(): void
 * Botones(): void
 * colocarEtiquetaServer(): void
 */
class VentanaServer extends JFrame implements Runnable{
    private JPanel panelSV;
    private JLabel serverLabel;

    /**
     * constructor de la ventana
     */

    public VentanaServer(){
        this.setBounds(500,200,700,700);
        setTitle("Servidor");

        componentesServer();

        /**
         * construimos el hilo para que siempre escuche
         */
        Thread mihilo = new Thread(this);
        mihilo.start();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    /**
     * Constructor de los componentes de la ventana
     */
    private void componentesServer(){
        colocarPanelSV();
        colocarEtiquetaServer();
        Botones();
    }
    /**
     * Constructor del panel de la interfaz
     */
    private void colocarPanelSV(){
        panelSV = new JPanel();
        panelSV.setLayout(null);
        this.getContentPane().add(panelSV);
    }

    /**
     * Constructor de los botones de la ventana
     */
    public void Botones(){
        int i;
        int j;
        int tamaño =10;
        int padding = 50;
        for (i=0;i<10;i++) {
            LinkedList fila = new LinkedList();
            for (j = 0; j<10; j++) {
                fila.insert(j);
                JButton puntos = new JButton();
                int x = i * (tamaño + padding) + padding;
                int y = j * (tamaño + padding) + padding;
                puntos.setBounds(x,y,tamaño,tamaño);
                panelSV.add(puntos);
            }
        }
    }

    /**
     * Constructor de la etiqueta de la ventana
     */
    private void colocarEtiquetaServer(){
        serverLabel = new JLabel("Servidor",SwingConstants.CENTER);
        panelSV.add(serverLabel);
        serverLabel.setBounds(300,15,100,25);
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
            String jsonRecibido;
            paqueteDatos paqueteRecibido;


            while(true) {
                /**
                 *Permite que acepte las conexiones del exterior
                 */
                Socket misocket = servidor.accept();


                ObjectInputStream entradaDatos = new ObjectInputStream(misocket.getInputStream());
                jsonRecibido = (String) entradaDatos.readObject();
                paqueteRecibido= paqueteDatos.fromJson(jsonRecibido);

                nick = paqueteRecibido.getNick();
                ip = paqueteRecibido.getIp();
                mensaje = paqueteRecibido.getMensaje();


                /**reenvio de datos*/

                int port;
                port = 9091;
                for (int i = port; i<9100;i++){
                    try{
                        Socket enviaDestinatario = new Socket(ip,i);

                        ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
                        final String jsonAEnviar = paqueteRecibido.toJson();
                        paqueteReenvio.writeObject(jsonAEnviar);

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
/**
 * La clase Node define la estructura de los nodos,
 * así como los datos que pueden guardar
 * Atributos:
 * Object data;
 * Node next;
 * Métodos:
 * Object getData();
 * void setData(Object data);
 * Node getNext();
 * void setNext(Node next);
 */
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

/**
 * La clase LinkedList utiliza la clase Node como referencia,
 * donde la clase guarda los datos asignados en nodos unidos por punteros,
 * formando la lista enlazada.
 * Atributos:
 * head: Node
 * size: int
 * tail: Node
 * Métodos:
 * LinkedList()
 * size: int
 * insert: void
 * printMatriz: void
 * printList: void
 */
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

/**
 * La clase playerList es una clase que permite
 * crear una lista tipo cola, el cual funciona como
 * la lista de jugadores.
 * Atributos:
 * players: LinkedList
 * head: Node
 * tail: Node
 * Métodos:
 * printList: void
 * enqueue(Object data): void
 * dequeue(): Object
 */
class playerList{
    private LinkedList players;
    private Node head;
    private Node tail;

    public playerList(){
        players = new LinkedList();
        this.head=null;
        this.tail=null;
    }

    public void enqueue(Object data){
        if (head==null){
            head=tail= new Node(data);
        }else{
            tail=tail.next = new Node(data);
        }
    }

    public void printList(){
        Node current = this.head;
        while (current !=null) {
            System.out.print(current.getData());
            current = current.getNext();
        }
    }

    public Object dequeue() {
        if (head != null) {
            Node temp = head;
            head = head.next;
            return temp;
        } else {
            return null;
        }
    }
}

/**
 * La clase Matriz crea una matriz, por medio de una
 * lista de listas enlazadas.
 * Atributos:
 * head: Node
 * tail: Node
 * n: int
 * players: LinkedList
 * Métodos:
 * formMatriz: void
 */
class Matriz{
    private int n;
    private LinkedList matriz;
    private Node head;
    private Node tail;

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
        /**
         * instancia de la ventana de servidor
         */
        VentanaServer servidor = new VentanaServer();
        servidor.setVisible(true);

        Matriz matrizJuego = new Matriz(10);
    }
}