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
    }

    private void populateRestOfTheStates(){
        ArrayList<String> domainStrings = new ArrayList<>(emailSchema.getDomains());
        State[] temporalStateHolder= new State[domainStrings.size()];//state holder
        Arrays.fill(temporalStateHolder, theSymbolState);//fill it with the current symbol state.
        int maxLengthOfDomain=biggestLengthInDomainList(domainStrings);//taking the length of the biggest domain to use it as a loop
        // i = char index in string , j = string index in array | aligns with its current "state" in the arrayStateHolder
        for (int i = 0; i < maxLengthOfDomain; i++) {//rotate over chars
            for (int j = 0; j < domainStrings.size(); j++) { // rotate over strings(domains)

                if (i < domainStrings.get(j).length() ){
                    char currentCharOfDomain = domainStrings.get(j).charAt(i);
                     if (isNewStateNeeded(currentCharOfDomain,temporalStateHolder[j])){
                         State s = new State(statesBaseName + stateGenerationCounter++,StateType.DOMAIN,currentCharOfDomain);
                         allStatesQ.add(s);
                         temporalStateHolder[j].addInputToStates(currentCharOfDomain,Set.of(s));
                         System.out.println(temporalStateHolder[j]);
                         temporalStateHolder[j]=s;
                     }

                    if (domainStrings.get(j).length() - 1 ==i){
                        finalStatesF.add(temporalStateHolder[j]);//if string ends on this index then the state left in array is the final one.
                    }
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

    private boolean isNewStateNeeded(char input, State curState){
        return  isInputDifferentThanStateValue(input, curState) && isInputNewToTheState(input, curState);
    }

    private boolean isInputDifferentThanStateValue(char input, State curState){
        return curState.getStateValue() != input;
    }

    private boolean isInputNewToTheState(char input, State curState){
        return !curState.doesInputExist(input);
    }

    public List<State> getAllStatesQ() {
        return allStatesQ;
    }

    public List<State> getFinalStatesF() {
        return finalStatesF;
    }

    public State getInitialStatesI() {
        return initialStatesI;
    }
}