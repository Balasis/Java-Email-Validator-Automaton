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

    public StateType getStateType(){
        return getStateType();
    }

    public Set<State> getStates(char input) {

        return null;
    }

    public boolean doesInputExist(char input){
       if(inputToStates.containsKey(input)){
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
        return stateType+" : " + stateName +" | " + inputToStates;
    }

}