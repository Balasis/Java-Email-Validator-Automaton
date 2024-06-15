package emailautomatonfactory;

import emailschemafactory.CharRange;
import java.util.*;

public class State{
    private String stateName;
    private StateType stateType;
    private char stateValue;
    private Map<Character,Set<State>> inputToStates;
    private Map<CharRange,Set<State>> inputRangesToStates;

    public State(String stateName,StateType stateType){
        this.inputToStates = new HashMap<>();
        this.inputRangesToStates = new HashMap<>();
        this.stateName = stateName;
        this.stateType = stateType;
    }

    public State(String stateName,StateType stateType, char stateValue){
        this.inputToStates = new HashMap<>();
        this.inputRangesToStates = new HashMap<>();
        this.stateName = stateName;
        this.stateType = stateType;
        this.stateValue = stateValue;
    }

    public void addInputToStates(Character input, Set<State> states){
        inputToStates.put(input, states);
    }

    public void addInputRangesToStates(Map<CharRange,Set<State>> inputToStates){
        this.inputRangesToStates.putAll(inputToStates);
    }

    public String getStateName() {
        return stateName;
    }

    public char getStateValue() {
        return stateValue;
    }

    public StateType getStateType(){
        return getStateType();
    }

    public Set<State> getStates(char input) throws InputExistanceInStateException {
        if (input==stateValue){
            return Set.of(this);
        }
        if (inputToStates.containsKey(input)) {
            return inputToStates.get(input);
        }
        for (Map.Entry<CharRange, Set<State>> entry : inputRangesToStates.entrySet()) {
            CharRange charRange = entry.getKey();
            if (charRange.from() <= input && charRange.to() >= input) {
                return entry.getValue();
            }
        }
        throw new InputExistanceInStateException("Char "+input +"  doesn't exist in state");
    }

    public boolean doesInputExist(char input){
       if(inputToStates.containsKey(input) && (input==stateValue)){
           return true;
       }
       for(CharRange cR : inputRangesToStates.keySet()){
           if ((cR.from() <= input) && (cR.to()>= input) ){
               return true;
           }
       }
        return false;
    }

    public String toString(){
        if (stateType == StateType.USERNAME){
            return stateType+" : " + stateName +" | " + inputRangesToStates.keySet() + " ||||";
        }
        return stateType+" : " + stateName +" | " + inputToStates.keySet();
    }

}