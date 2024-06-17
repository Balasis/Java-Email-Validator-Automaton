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
        this.caseSensitiveDomain = emailAutomaton.get(0).getCaseSensitiveDomain();
        this.finalStatesF = new ArrayList<>();
        this.initialStatesI = new ArrayList<>();
        populateAllStateLists();
    }

    //API
    public boolean isItAValidEmail(String email) {
        String theEmail = caseSensitiveDomain ? email : turnDomainToLower(email);
        List<State> currentStates = new ArrayList<>(initialStatesI);

        for (char currentChar : theEmail.toCharArray()) {
            List<State> nextStates = new ArrayList<>();
            for (State state : currentStates) {
                if (state.doesInputExist(currentChar)) {
                    try {
                        Set<State> returnedStates = state.getStates(currentChar);
                        nextStates.addAll(returnedStates);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            currentStates = nextStates;
            if (currentStates.isEmpty()) {
                return false;
            }
        }

        for (State state : currentStates) {
            if (finalStatesF.contains(state)) {
                return true;
            }
        }

        return false;
    }


    //Privates -assist
    private String turnDomainToLower(String email) {
        String[] splittedString = email.split("@");
        return splittedString[0] + '@' + splittedString[1].toLowerCase();
    }

    private void isCaseSensAmongSchemasConsist() throws EmailAutCaseMergeException {
        boolean referenceCaseSensitivity = emailAutomaton.get(0).getCaseSensitiveDomain();
        for (EmailAutomaton eA : emailAutomaton) {
            if (eA.getCaseSensitiveDomain() != referenceCaseSensitivity) {
                throw new EmailAutCaseMergeException("Domain Case sensitive should be the same among merged automatons");
            }
        }
    }

    private void populateAllStateLists() {
        for (EmailAutomaton eA : emailAutomaton) {
            finalStatesF.addAll(eA.getFinalStatesF());
            initialStatesI.add(eA.getInitialStateI());
        }
    }

}