
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

    public void printList(){
        Node current = this.head;
        while (current!=null) {
            LinkedList currentList = (LinkedList) current.getData();
            currentList.printCurrentList();
            System.out.println();
            current = current.getNext();
        }
    }
    public void printCurrentList(){
        Node current = this.head;
        while (current !=null) {
            System.out.print(current.getData());
            current = current.getNext();
        }
    }
}

public class Servidor {
    public static void main(String[] args) {
        LinkedList matriz = new LinkedList();
        LinkedList fila1 = new LinkedList();
        LinkedList fila2 = new LinkedList();
        LinkedList fila3 = new LinkedList();

        fila1.insert(1);
        fila1.insert(2);
        fila1.insert(3);
        matriz.insert(fila1);

        fila2.insert(4);
        fila2.insert(5);
        fila2.insert(6);
        matriz.insert(fila2);

        fila3.insert(7);
        fila3.insert(8);
        fila3.insert(9);
        matriz.insert(fila3);

        matriz.printList();
    }

}

