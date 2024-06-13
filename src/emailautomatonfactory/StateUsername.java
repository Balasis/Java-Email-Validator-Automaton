package emailautomatonfactory;

import emailautomatonfactory.State;

import java.util.List;
import java.util.Set;

public class StateUsername implements State {
    public StateUsername(){

    }

    @Override
    public Set<State> getStates(char input) {
//        if (isItAValidChar(input)){
//            return Set.of(this);
//        } else if (input=='@') {
//            return Set.of();
//        }else{
            return Set.of();//TODO: GetNextStatesFrom Constructor
//        }
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
