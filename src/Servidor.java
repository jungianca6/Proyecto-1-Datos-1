import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;
import java.awt.geom.Ellipse2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 /**Implementando Runnable haceque siempre esté a la escucha*/
class VentanaServer extends JFrame implements Runnable {
    private JPanel panelSV;
    private JLabel serverLabel;
    private Matriz matrizJuego;

    public VentanaServer() {
        this.setBounds(500, 200, 400, 500);
        setTitle("Servidor");

        matrizJuego = new Matriz(10);
        componentesServer();

        Thread mihilo = new Thread(this);
        mihilo.start();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void componentesServer() {
        colocarPanelSV();
        colocarEtiquetaServer();
        agregarBotonesMatriz(); // Agrega los botones de la matriz al panel
    }

    private void colocarPanelSV() {
        panelSV = new JPanel();
        panelSV.setLayout(null);
        this.getContentPane().add(panelSV);
    }

    private void colocarEtiquetaServer() {
        serverLabel = new JLabel("Servidor",SwingConstants.CENTER);
        panelSV.add(serverLabel);
        serverLabel.setBounds(140,20,100,25);
        serverLabel.setForeground(Color.WHITE);
        serverLabel.setBackground(Color.BLACK);
        serverLabel.setFont(new Font("times new roman", Font.PLAIN,20));
        serverLabel.setOpaque(true);
    }
    private void agregarBotonesMatriz() {
        JButton[][] botones = matrizJuego.getMatriz();
        int buttonSize = 5; // Tamaño de los botones
        int distanciaEntreBotones = 30; // Distancia entre los botones
        int posicionY = 85; // Posicion en el eje Y
        int posicionX = 32; // Posicion en el eje X

        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                JButton button = botones[i][j];
                int x = j * (buttonSize + distanciaEntreBotones) + posicionX;
                int y = i * (buttonSize + distanciaEntreBotones) + posicionY;
                button.setBounds(x, y, buttonSize, buttonSize);
                panelSV.add(button);
            }
        }
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
class Matriz {
    private int n;
    private JButton[][] matriz;

    public Matriz(int n) {
        this.n = n;
        matriz = new JButton[n][n];
        formMatriz();
    }
    public void formMatriz() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = new JButton() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        int diameter = Math.min(getWidth(), getHeight());
                        int x = (getWidth() - diameter) / 2;
                        int y = (getHeight() - diameter) / 2;
                        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, diameter, diameter);
                        g2d.setColor(getBackground());
                        g2d.fill(circle);
                    }
                };
                matriz[i][j].setBackground(Color.DARK_GRAY);
                matriz[i][j].setOpaque(false);
                matriz[i][j].setContentAreaFilled(false);
                matriz[i][j].setBorderPainted(false);
            }
        }
    }
    public JButton[][] getMatriz() {
        return matriz;
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

class JuegoDotsAndBoxes {
    private JButton[][] puntos;
    private boolean[][] lineasHorizontales;
    private boolean[][] lineasVerticales;
    private int cuadrosCompletados;

    public JuegoDotsAndBoxes(int n) {
        puntos = new JButton[n + 1][n + 1];
        lineasHorizontales = new boolean[n][n + 1];
        lineasVerticales = new boolean[n + 1][n];
        cuadrosCompletados = 0;

        // Crear puntos y agregar ActionListener
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                puntos[i][j] = new JButton();
                puntos[i][j].setBackground(Color.DARK_GRAY);
                puntos[i][j].setOpaque(true);
                final int x = i, y = j;
                puntos[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onPointClicked(x, y);
                    }
                });
            }
        }
    }

    public JButton[][] getPuntos() {
        return puntos;
    }

    public int getCuadrosCompletados() {
        return cuadrosCompletados;
    }

    private void onPointClicked(int x, int y) {
        // Implementa la lógica de dibujar líneas y verificar cuadros aquí
        // Actualiza cuadrosCompletados cuando se completa un cuadro

        // Por ejemplo, puedes cambiar el color del punto para indicar que se ha hecho clic
        puntos[x][y].setBackground(Color.RED);
    }
}