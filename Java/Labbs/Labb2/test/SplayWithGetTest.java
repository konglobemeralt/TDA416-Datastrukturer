import static org.junit.jupiter.api.Assertions.*;

class SplayWithGetTest {
    @org.junit.jupiter.api.Test
    void get() {
        SplayWithGet<Integer> tree = new SplayWithGet<>();
        tree.add(2);
        tree.add(3);
        tree.add(5);
        tree.add(1);
        tree.add(20);
        tree.add(4);
        System.out.println(tree.toString());
        tree.get(4);
        tree.get(20);
        System.out.println(tree.toString());
    }

}