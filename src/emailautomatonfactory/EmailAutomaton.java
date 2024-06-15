package emailautomatonfactory;

import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;

import java.util.*;

public class EmailAutomaton {

    private final Boolean caseSensitiveDomain;
    private final EmailSchema emailSchema;
    private final String statesBaseNameToLowerCase;
    private final List<State> allStatesQ;
    private final List<State> finalStatesF;
    private UsernameState initialStateI;
    private State theSymbolState;
    private int stateGenerationCounter;


    public EmailAutomaton(EmailSchema emailSchema){
        this.caseSensitiveDomain=false;
        this.allStatesQ=new ArrayList<>();
        this.finalStatesF=new ArrayList<>();
        this.emailSchema=emailSchema;
        this.statesBaseNameToLowerCase =emailSchema.getMainDomain().toLowerCase();
        stateGenerationCounter = 0;
        generateStates();
    }

    public EmailAutomaton(EmailSchema emailSchema, Boolean caseSensitiveDomain){
        this.caseSensitiveDomain =caseSensitiveDomain;
        this.allStatesQ=new ArrayList<>();
        this.finalStatesF=new ArrayList<>();
        this.emailSchema=emailSchema;
        this.statesBaseNameToLowerCase =emailSchema.getMainDomain().toLowerCase();
        stateGenerationCounter = 0;
        generateStates();
    }


    public boolean isItAValidEmail(String email){
        String theEmail= caseSensitiveDomain ? email : turnDomainToLower(email) ;
        ArrayList<State> curState = new ArrayList<>(Collections.singletonList(initialStateI));
        for (int i = 0; i < theEmail.length(); i++) {
            char curChar = theEmail.charAt(i);
            System.out.println(theEmail.charAt(i));
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
            if (curState.isEmpty()){
                return false;
            }
        }
        return new HashSet<>(finalStatesF).containsAll(curState);
    }

    private String turnDomainToLower(String email){
        String[] splittedString=email.split("@");
        return  splittedString[0] + '@' + splittedString[1].toLowerCase();
    }


    private void generateStates(){
        generateInitialStateI();
        populateInitialStateTransitions();
        populateRestOfTheStates();
    }

    private void generateInitialStateI(){
        initialStateI = new UsernameState(statesBaseNameToLowerCase + stateGenerationCounter++);
        initialStateI.setInvalidFirstChars(emailSchema.getUsernameInvalidFirstChars());
        initialStateI.setInvalidLastChars(emailSchema.getUsernameInvalidLastChars());
        initialStateI.setInvalidConsecutiveChars(emailSchema.getUsernameInvalidConsecutiveChars());
        allStatesQ.add(initialStateI);
    }

    private void populateInitialStateTransitions(){
        State symbolState = new State(statesBaseNameToLowerCase + stateGenerationCounter++);
        theSymbolState=symbolState;
        allStatesQ.add(theSymbolState);
        Map <CharRange, Set<State>> rangesForInitialState = new HashMap<>();
        for (CharRange cR : emailSchema.getAllowedCharRanges()){
            rangesForInitialState.put(cR,Set.of(initialStateI));
        }
        initialStateI.addInputRangesToStates(rangesForInitialState);
        initialStateI.addInputToStates('@', Set.of(symbolState) );
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

    private int biggestLengthInDomainList(ArrayList<String> domainStrings){
        int lengthToBeReturned = 0;
        for (String s : domainStrings){
            lengthToBeReturned = Math.max(s.length(), lengthToBeReturned);
        }
        return lengthToBeReturned;
    }

    //Also populates the Symbols addInputToStates
    private void generateDomainStates(ArrayList<String> domainStrings, State[] temporalStateHolder, int maxLengthOfDomain) {
        for (int i = 0; i < maxLengthOfDomain; i++) {
            for (int j = 0; j < domainStrings.size(); j++) {
                if (i < domainStrings.get(j).length()) {

                    char currentCharOfDomain;
                    if (caseSensitiveDomain){
                        currentCharOfDomain= domainStrings.get(j).charAt(i);
                    }else{
                        currentCharOfDomain= domainStrings.get(j).toLowerCase().charAt(i);
                    }
                    generateNewState(currentCharOfDomain, temporalStateHolder, j);
                    if (domainStrings.get(j).length() - 1 == i) {
                        finalStatesF.add(temporalStateHolder[j]);
                    }

                }
            }
        }
    }

    private void generateNewState(char currentCharOfDomain, State[] temporalStateHolder, int domainIndex) {
            State newState = createNewState(currentCharOfDomain);
            temporalStateHolder[domainIndex].addInputToStates(currentCharOfDomain, Set.of(newState));
            temporalStateHolder[domainIndex] = newState;
    }

    private State createNewState(char currentCharOfDomain) {
        State newState = new State(statesBaseNameToLowerCase + stateGenerationCounter++, currentCharOfDomain);
        allStatesQ.add(newState);
        return newState;
    }

//    private boolean isInputDifferentThanStateValue(char input, State curState){
//        return curState.getStateValue() != input;
//    }
//
//    private boolean isInputNewToTheState(char input, State curState){
//        return !curState.doesInputExist(input);
//    }



    public List<State> getAllStatesQ() {
        return allStatesQ;
    }

    public List<State> getFinalStatesF() {
        return finalStatesF;
    }

    public UsernameState getInitialStateI() {
        return initialStateI;
    }

    public Boolean getCaseSensitiveDomain() {
        return caseSensitiveDomain;
    }

    public State getTheSymbolState() {
        return theSymbolState;
    }
}