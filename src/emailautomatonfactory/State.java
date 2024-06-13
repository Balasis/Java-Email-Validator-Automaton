package emailautomatonfactory;

import emailschemafactory.CharRange;

import javax.print.attribute.standard.PageRanges;
import java.util.*;

public class State{
    private String stateName;
    private StateType stateType;
    private List<Character> disallowedChars;
    private Map<Character,Set<State>> inputToStates;
    private Map<CharRange,Set<State>> inputRangesToStates;

    public State(String stateName){
        this.disallowedChars = new ArrayList<>();
        this.inputToStates = new HashMap<>();
        this.inputRangesToStates = new HashMap<>();
    }

    public State(String stateName,StateType stateType){
        this.disallowedChars = new ArrayList<>();
        this.inputToStates = new HashMap<>();
        this.inputRangesToStates = new HashMap<>();
    }

    public void addDisallowedChars(List<Character> theChars){
        disallowedChars.addAll(theChars);
    }

    public void addDisallowedChars(Character theChar){
        disallowedChars.add(theChar);
    }

    public void addInputToStates(Character input, Set<State> states){
        inputToStates.put(input, states);
    }

    public void addInputToStates(Map<Character,Set<State>> inputToStates){
        this.inputToStates.putAll(inputToStates);
    }

    public void addInputRangesToStates(CharRange input, Set<State> states){
        inputRangesToStates.put(input, states);
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

//    private boolean isItAValidChar(char input){
//        return isLowerCaseEnglishLetter(input) ||
//                isUpperCaseEnglishLetter(input) ||
//                isNumeric(input) || isItAnAccessibleSymbol(input);
//
//    }
//
//    private boolean isItAnAccessibleSymbol(char input){
//        return input=='-' || input=='_' || input=='.';
//    }
//
//    private boolean isLowerCaseEnglishLetter(char input){
//        return (('a'<=input) && ('z'>=input));
//    }
//
//    private boolean isUpperCaseEnglishLetter(char input){
//        return (('A'<=input) && ('Z'>=input));
//    }
//
//    private boolean isNumeric(char input){
//        return input>= '0' && input <= '9';
//    }
}
