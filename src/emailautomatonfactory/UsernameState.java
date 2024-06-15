package emailautomatonfactory;

import emailschemafactory.CharRange;
import exceptions.InputExistanceInStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UsernameState extends State{

    private char previousInput;
    private boolean isItTheFirstInput;
    private List<CharRange> invalidConsecutiveChars;
    private List<CharRange> invalidFirstChars;
    private List<CharRange> invalidLastChars;

    public UsernameState(String stateName) {
        super(stateName);
        this.isItTheFirstInput =true;
        this.invalidConsecutiveChars =new ArrayList<>();
        this.invalidFirstChars =new ArrayList<>();
        this.invalidLastChars =new ArrayList<>();
    }

    @Override
    public Set<State> getStates(char input) throws InputExistanceInStateException {
        usernameInvalidChecks(input);

        if (input==getStateValue()){
            previousInput=input;
            return Set.of(this);
        }
        if (getInputToStates().containsKey(input)) {
            previousInput=input;
            return getInputToStates().get(input);
        }
        for (Map.Entry<CharRange, Set<State>> entry : getInputRangesToStates().entrySet()) {
            CharRange charRange = entry.getKey();
            if (charRange.contains(input) ) {
                previousInput=input;
                return entry.getValue();
            }
        }
        throw new InputExistanceInStateException("Char "+input +"  doesn't exist in state");
    }

    @Override
    public boolean doesInputExist(char input){
        try {
            usernameInvalidChecks(input);
        } catch (InputExistanceInStateException e) {
            return false;
        }
        if (input==getStateValue()){
            return true;
        }
        return super.doesInputExist(input);
    }


    //Privates -assist
    private void usernameInvalidChecks(char input)  throws InputExistanceInStateException{
        isAnInvalidFirstChar(input);
        isAnInvalidConsecutiveChars(input);
        isAnInvalidLastChar(input);
    }

    private void isAnInvalidFirstChar(char input) throws InputExistanceInStateException {
        if( isItTheFirstInput && isIncludedInRangeList( input, invalidFirstChars )){
            throw new InputExistanceInStateException("Char "+ input +" isn't allowed as first char of username");
        }else{
            isItTheFirstInput = false;
        }
    }

    private void isAnInvalidConsecutiveChars(char input) throws InputExistanceInStateException {
        if (previousInput==input && isIncludedInRangeList( input, invalidConsecutiveChars )){
            throw new InputExistanceInStateException("You can't have two '"+input+"' one after another");
        }
    }

    private void isAnInvalidLastChar(char input) throws InputExistanceInStateException {
        if (isIncludedInRangeList( previousInput, invalidLastChars ) && input=='@'){
            throw new InputExistanceInStateException("Char "+previousInput +" isn't allowed as last char of username(before '@')");
        }
    }

    private boolean isIncludedInRangeList(char input, List<CharRange> theList  ){
        for(CharRange cR : theList){
            if (cR.contains(input) ){
                return true;
            }
        }
        return false;
    }

    //setters
    public void setInvalidConsecutiveChars(List<CharRange> invalidConsecutiveChars) {
        this.invalidConsecutiveChars = invalidConsecutiveChars;
    }

    public void setInvalidFirstChars(List<CharRange> invalidFirstChars) {
        this.invalidFirstChars = invalidFirstChars;
    }

    public void setInvalidLastChars(List<CharRange> invalidLastChars) {
        this.invalidLastChars = invalidLastChars;
    }
}