package emailautomatonfactory;

import java.util.Set;

public interface State {
   Set<State> getStates(char input);
}
