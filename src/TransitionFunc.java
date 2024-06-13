import java.util.Set;

public class TransitionFunc {
    String state;
    String input;
    Set<String> rStates;

    public TransitionFunc(String state, String input, Set<String> rStates) {
        this.state = state;
        this.input = input;
        this.rStates = rStates;
    }

    public String getState() {
        return state;
    }

    public String getInput() {
        return input;
    }

    public Set<String> getrStates() {
        return rStates;
    }

    @Override
    public String toString() {
        return "{ "+state+", "+input+" } -> "+rStates;
    }
}