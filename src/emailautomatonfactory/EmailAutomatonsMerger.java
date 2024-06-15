package emailautomatonfactory;

import exceptions.EmailAutCaseMergeException;

import java.util.*;

public class EmailAutomatonsMerger {
    private final Boolean caseSensitiveDomain;
    private final List<EmailAutomaton> emailAutomaton;
    private final List<State> finalStatesF;
    private final List<UsernameState> initialStatesI;

    public EmailAutomatonsMerger(List<EmailAutomaton> emailAutomaton) throws EmailAutCaseMergeException {
        this.emailAutomaton = emailAutomaton;
        isCaseSensAmongSchemasConsist();
        this.caseSensitiveDomain=emailAutomaton.get(0).getCaseSensitiveDomain();
        this.finalStatesF= new ArrayList<>();
        this.initialStatesI= new ArrayList<>();
        populateAllStateLists();
    }

    public boolean isItAValidEmail(String email){
        String theEmail= caseSensitiveDomain ? email : turnDomainToLower(email) ;
        ArrayList<State> curState = new ArrayList<>(initialStatesI);
        for (int i = 0; i < theEmail.length(); i++) {
            char curChar = theEmail.charAt(i);
            ArrayList<State> rotateStateList = new ArrayList<>();
            for (int j = 0; j < curState.size();j++) {
                    if(curState.get(j).doesInputExist(curChar)){
                        try {
                            Set<State> returStates= curState.get(j).getStates(curChar);
                            rotateStateList.addAll(returStates);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
            }
            curState = rotateStateList;
            if (curState.isEmpty()){
                return false;
            }
        }
        for (State state : curState) {
            if (finalStatesF.contains(state)) {
                return true;
            }
        }
        return false;
    }

    private String turnDomainToLower(String email){
        String[] splittedString=email.split("@");
        return  splittedString[0] + '@' + splittedString[1].toLowerCase();
    }

    private void isCaseSensAmongSchemasConsist() throws EmailAutCaseMergeException {
        boolean referenceCaseSensitivity = emailAutomaton.get(0).getCaseSensitiveDomain();
        for (EmailAutomaton eA : emailAutomaton) {
            if (eA.getCaseSensitiveDomain() != referenceCaseSensitivity) {
                throw new EmailAutCaseMergeException("Domain Case sensitive should be the same among merged automatons");
            }
        }
    }

    private void populateAllStateLists(){
        for (EmailAutomaton eA : emailAutomaton) {
            finalStatesF.addAll(eA.getFinalStatesF());
            initialStatesI.add(eA.getInitialStateI());
        }
    }

}