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
        this.statesBaseName=emailSchema.getMainDomain();
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

    private void populateRestOfTheStates() {
        ArrayList<String> domainStrings = new ArrayList<>(emailSchema.getDomains());
        State[] temporalStateHolder = initializeStateHolder(domainStrings.size());
        int maxLengthOfDomain = biggestLengthInDomainList(domainStrings);
        generateDomainStates(domainStrings, temporalStateHolder, maxLengthOfDomain);
    }

    private State[] initializeStateHolder(int size) {
        State[] temporalStateHolder = new State[size];
        Arrays.fill(temporalStateHolder, theSymbolState);
        return temporalStateHolder;
    }
    //Also populates the Symbols addInputToStates
    private void generateDomainStates(ArrayList<String> domainStrings, State[] temporalStateHolder, int maxLengthOfDomain) {
        for (int i = 0; i < maxLengthOfDomain; i++) {
            for (int j = 0; j < domainStrings.size(); j++) {
                if (i < domainStrings.get(j).length()) {
                    char currentCharOfDomain = domainStrings.get(j).charAt(i);
                    generateNewState(currentCharOfDomain, temporalStateHolder, j);
                    if (domainStrings.get(j).length() - 1 == i) {
                        finalStatesF.add(temporalStateHolder[j]);
                    }
                }
            }
        }
    }

    private void generateNewState(char currentCharOfDomain, State[] temporalStateHolder, int domainIndex) {
        if (isNewStateNeeded(currentCharOfDomain, temporalStateHolder[domainIndex])) {
            State newState = createNewState(currentCharOfDomain);
            temporalStateHolder[domainIndex].addInputToStates(currentCharOfDomain, Set.of(newState));
            temporalStateHolder[domainIndex] = newState;
        }
    }

    private State createNewState(char currentCharOfDomain) {
        State newState = new State(statesBaseName + stateGenerationCounter++, StateType.DOMAIN, currentCharOfDomain);
        allStatesQ.add(newState);
        return newState;
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