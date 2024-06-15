package emailautomatonfactory;

import emailschemafactory.CharRange;

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

        if(isItTheFirstInput && isAnInvalidFirstChar(input)){
            throw new InputExistanceInStateException("Char "+ input +" isn't allowed as first char of username");
        }else{
            isItTheFirstInput = false;
        }


        if (previousInput==input && isAnInvalidConsecutiveChars(input)){
            throw new InputExistanceInStateException("You can't have two '"+input+"' one after another");
        }

        if (isAnInvalidLastChar(input)){
            throw new InputExistanceInStateException("Char "+previousInput +" isn't allowed as last char of username(before '@')");
        }

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
            if (charRange.from() <= input && charRange.to() >= input) {
                previousInput=input;
                return entry.getValue();
            }
        }
        throw new InputExistanceInStateException("Char "+input +"  doesn't exist in state");
    }



    private boolean isAnInvalidFirstChar(char input){
        return  isIncludedInRangeList( input, invalidFirstChars );
    }

    private boolean isAnInvalidLastChar(char input){
        return  isIncludedInRangeList( previousInput, invalidLastChars ) && input=='@';
    }

    private boolean isAnInvalidConsecutiveChars(char input){
        return  isIncludedInRangeList( input, invalidConsecutiveChars );
    }

    private boolean isIncludedInRangeList(char input, List<CharRange> theList  ){
        for(CharRange cR : theList){
            if ((cR.from() <= input) && (cR.to()>= input) ){
                return true;
            }
        }
        return false;
    }

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