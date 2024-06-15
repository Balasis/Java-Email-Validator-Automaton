package emailautomatonfactory;

import emailschemafactory.CharRange;
import exceptions.InputExistanceInStateException;

import java.util.*;

public class State{
    private String stateName;
    private char stateValue;
    private Map<Character,Set<State>> inputToStates;
    private Map<CharRange,Set<State>> inputRangesToStates;

    public State(String stateName){
        this.inputToStates = new HashMap<>();
        this.inputRangesToStates = new HashMap<>();
        this.stateName = stateName;
    }

    public State(String stateName, char stateValue){
        this.inputToStates = new HashMap<>();
        this.inputRangesToStates = new HashMap<>();
        this.stateName = stateName;
        this.stateValue = stateValue;
    }

    public void addInputToStates(Character input, Set<State> states){
        inputToStates.put(input, states);
    }

    public void addInputRangesToStates(Map<CharRange,Set<State>> inputToStates){
        this.inputRangesToStates.putAll(inputToStates);
    }

    public char getStateValue() {
        return stateValue;
    }

    public Set<State> getStates(char input) throws InputExistanceInStateException {
        if (inputToStates.containsKey(input)) {//if input exist in single input list-> give state
            return inputToStates.get(input);
        }
        for (Map.Entry<CharRange, Set<State>> entry : inputRangesToStates.entrySet()) {
            CharRange charRange = entry.getKey();//if input exist in the range of input list ->give state
            if ( charRange.contains(input) ) {
                return entry.getValue();
            }
        }
        throw new InputExistanceInStateException("Char "+input +"  doesn't exist in state");
    }

    public boolean doesInputExist(char input){
        if (inputToStates.containsKey(input)) {
            return true;
        }
        for (Map.Entry<CharRange, Set<State>> entry : inputRangesToStates.entrySet()) {
            CharRange charRange = entry.getKey();
            if ( charRange.contains(input) ) {
                return true;
            }
        }
        return false;
    }

    public Map<Character, Set<State>> getInputToStates() {
        return inputToStates;
    }

    public Map<CharRange, Set<State>> getInputRangesToStates() {
        return inputRangesToStates;
    }

    public String toString(){
            return " : " + stateName +" | " + inputToStates.keySet();
    }

}