
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

    public void printlist(){
        Node current = this.head;
        while (current!=null){
            System.out.print(current.getData());
            current= current.getNext();
        }
    }

}
public class Servidor {
    public static void main(String[] args) {
        LinkedList lista1 = new LinkedList();
        LinkedList lista2 = new LinkedList();
        LinkedList lista3 = new LinkedList();

        lista1.insert(8);
        lista2.insert(5);
        lista3.insert(4);

        lista1.printlist();


    }
}
