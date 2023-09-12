import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

class VentanaServer extends JFrame{
    public VentanaServer(){
        this.setBounds(500,200,300,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Servidor");
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

        /*ServerSocket server = new ServerSocket(9090);
        Socket listenSocket = server.accept();
        BufferedReader clientinput = new BufferedReader(new InputStreamReader(listenSocket.getInputStream()));
        String client_str;
        client_str = clientinput.readLine();
        System.out.println("Cliente conectado");
        System.out.println(client_str);
        */

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