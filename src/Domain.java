import java.util.Map;
import java.util.Set;

public class Domain {
    Map<String,Set<Domain>> inputToStatesMap;

    public void addIputToState(String input,Set<Domain> domains){
        inputToStatesMap.put(input, domains);
    }

    public Set<Domain> getStatesOfInputs(String input){
        return inputToStatesMap.get(input);
    }

}