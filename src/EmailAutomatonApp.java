import java.util.Set;

public class EmailAutomatonApp {
    public static void main(String[] args) {
        State tr=new State("q0","a", Set.of("q1","q2"));
        System.out.println(tr);
    }
}