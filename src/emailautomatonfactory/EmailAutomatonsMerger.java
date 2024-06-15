package emailautomatonfactory;

import java.util.ArrayList;
import java.util.List;

public class EmailAutomatonsMerger {
    private final Boolean caseSensitiveDomain;
    private List<EmailAutomaton> emailAutomaton;
    private List<State> allStatesQ;
    private List<State> finalStatesF;
    private List<UsernameState> initialStatesI;

    public EmailAutomatonsMerger(List<EmailAutomaton> emailAutomaton) throws EmailAutCaseMergeException {
        this.emailAutomaton = emailAutomaton;
        isCaseSensAmongSchemasConsist();
        this.caseSensitiveDomain=emailAutomaton.get(0).getCaseSensitiveDomain();
        this.allStatesQ= new ArrayList<>();
        this.finalStatesF= new ArrayList<>();
        this.initialStatesI= new ArrayList<>();
        populateAllStateLists();
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
            allStatesQ.addAll(eA.getAllStatesQ());
            finalStatesF.addAll(eA.getFinalStatesF());
            initialStatesI.add(eA.getInitialStateI());
        }
    }


}
