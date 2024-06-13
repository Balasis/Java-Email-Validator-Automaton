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
        State symbolState = new State(statesBaseName + stateGenerationCounter++, StateType.SYMBOL);
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
        int maxLengthOfDomain=biggestLengthInDomainList(domainStrings);

        for (int i = 0; i < maxLengthOfDomain; i++) {
            for (String s : domainStrings){
                if (s.length() <= i){//TODO:create map which keeps the state
                    //TODO: and the according indexes of domain strings, therefore you continue in the path
                    //TODO:of the next ;..and start new state at the first char of domain even if its not needed

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