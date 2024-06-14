package emailautomatonfactory;

import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;

import java.util.*;

public class EmailAutomaton {

    private final EmailSchema emailSchema;
    private String statesBaseName;
    private final List<State> allStatesQ;
    private List<State> finalStatesF;
    private State initialStatesI;
    private State theSymbolState;
    private int stateGenerationCounter;


    public EmailAutomaton(EmailSchema emailSchema){
        this.allStatesQ=new ArrayList<>();
        this.finalStatesF=new ArrayList<>();
        this.emailSchema=emailSchema;
        this.statesBaseName=emailSchema.getEmailLabel();
        stateGenerationCounter = 0;
        generateStates();
    }

    private void generateStates(){
        generateInitialStateI();
        populateInitialStateTransitions();
        populateRestOfTheStates();
    }

    private void generateInitialStateI(){
        initialStatesI = new State(statesBaseName + stateGenerationCounter++ , StateType.USERNAME);
        allStatesQ.add(initialStatesI);
    }

    private void populateInitialStateTransitions(){
        State symbolState = new State(statesBaseName + stateGenerationCounter++, StateType.SYMBOL, '@');
        theSymbolState=symbolState;
        allStatesQ.add(theSymbolState);
        Map <CharRange, Set<State>> rangesForInitialState = new HashMap<>();
        for (CharRange cR : emailSchema.getAllowedCharRanges()){
            rangesForInitialState.put(cR,Set.of(symbolState));
        }
        initialStatesI.addInputRangesToStates(rangesForInitialState);
        initialStatesI.addDisallowedChars(emailSchema.getDisallowedChars());
    }

    private void populateRestOfTheStates(){
        ArrayList<String> domainStrings = new ArrayList<>(emailSchema.getDomains());


//        Map<State,Set<State>> statesToSetOfStates = new HashMap<>(); //state to states map,used on checking if newState already exist
//                                                                        //avoid having new states for the same new input

        State[] previousStatesOfListIndexHolder= new State[domainStrings.size()];//state holder
        Arrays.fill(previousStatesOfListIndexHolder, theSymbolState);//fill it with the current symbol state.

        int maxLengthOfDomain=biggestLengthInDomainList(domainStrings);//taking the length of the biggest domain to use it as a loop


        for (int i = 0; i < maxLengthOfDomain; i++) {//domain arraylist is not being edited therefore indexes match
            for (String s : domainStrings){          // with the indexes of the new array we made that hold the prev State
                if (s.length() <= i){//do not get into account the out of length domains...(finished)
                    finalStatesF.add(previousStatesOfListIndexHolder[i]);//if string ends on this index then the state left in array is the final one.

                }else{

                }
            }
        }

    }

    private int biggestLengthInDomainList(ArrayList<String> domainStrings){
        int lengthToBeReturned = 0;
        for (String s : domainStrings){
            lengthToBeReturned = Math.max(s.length(), lengthToBeReturned);
        }
        return lengthToBeReturned;
    }

}