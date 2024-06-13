import java.util.Set;

public class EmailAutomatonApp {
    public static void main(String[] args) {
        TransitionFunc tr=new TransitionFunc("q0","a", Set.of("q1","q2"));
        System.out.println(tr);
    }
}