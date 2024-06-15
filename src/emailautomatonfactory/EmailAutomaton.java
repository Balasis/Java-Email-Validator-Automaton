package emailautomatonfactory;

import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;

import java.util.*;

public class EmailAutomaton {

    private final EmailSchema emailSchema;
    private final String statesBaseNameToLowerCase;
    private final List<State> allStatesQ;
    private final List<State> finalStatesF;
    private State initialStatesI;
    private State theSymbolState;
    private int stateGenerationCounter;


    public EmailAutomaton(EmailSchema emailSchema){
        this.allStatesQ=new ArrayList<>();
        this.finalStatesF=new ArrayList<>();
        this.emailSchema=emailSchema;
        this.statesBaseNameToLowerCase =emailSchema.getMainDomain().toLowerCase();
        stateGenerationCounter = 0;
        generateStates();
    }

    public boolean isItAValidEmail(String email){
        String lowerCaseEmail=email.toLowerCase();
        ArrayList<State> curState = new ArrayList<>(Collections.singletonList(initialStatesI));
        for (int i = 0; i < lowerCaseEmail.length(); i++) {
            char curChar = lowerCaseEmail.charAt(i);
            ArrayList<State> rotateStateList = new ArrayList<>();
            for (int j = 0; j < curState.size();j++) {
                try {
                    Set<State> returStates= curState.get(j).getStates(curChar);
                    rotateStateList.addAll(returStates);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            curState = rotateStateList;
            System.out.println(curState);
            if (curState.isEmpty()){
                return false;
            }
        }
        return new HashSet<>(finalStatesF).containsAll(curState);
    }


    private void generateStates(){
        generateInitialStateI();
        populateInitialStateTransitions();
        populateRestOfTheStates();
    }

    private void generateInitialStateI(){
        initialStatesI = new State(statesBaseNameToLowerCase + stateGenerationCounter++ , StateType.USERNAME);
        allStatesQ.add(initialStatesI);
    }

    private void populateInitialStateTransitions(){
        State symbolState = new State(statesBaseNameToLowerCase + stateGenerationCounter++, StateType.SYMBOL, '@');
        theSymbolState=symbolState;
        allStatesQ.add(theSymbolState);
        Map <CharRange, Set<State>> rangesForInitialState = new HashMap<>();
        for (CharRange cR : emailSchema.getAllowedCharRanges()){
            rangesForInitialState.put(cR,Set.of(initialStatesI));
        }
        initialStatesI.addInputRangesToStates(rangesForInitialState);
        initialStatesI.addInputToStates('@', Set.of(symbolState) );
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
                if (i < domainStrings.get(j).length()) {//be aware for the lowerCase,Othewise we make it case-sensitive;
                    char currentCharOfDomain = domainStrings.get(j).toLowerCase().charAt(i);//could make it optional
                    generateNewState(currentCharOfDomain, temporalStateHolder, j);          //but email domains are never case-sens
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
        State newState = new State(statesBaseNameToLowerCase + stateGenerationCounter++, StateType.DOMAIN, currentCharOfDomain);
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