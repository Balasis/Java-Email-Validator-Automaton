import java.util.Map;
import java.util.Set;

public class State {
    Map<String,Set<State>> inputToStatesMap;

    public void addIputToState(String input,Set<State> states){
        inputToStatesMap.put(input,states);
    }

    public Set<State> getStatesOfInputs(String input){
        return inputToStatesMap.get(input);
    }

}