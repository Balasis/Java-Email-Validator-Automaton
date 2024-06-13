import java.util.*;

public class EmailAutomatonProgram {

    Map< EmailType, ArrayList<String> > domainsOfEmailType;

    public EmailAutomatonProgram(){//
        domainsOfEmailType = new HashMap<>();
        domainsOfEmailType.put(EmailType.GMAIL,new ArrayList<>(Arrays.asList("gmail.com","gmail.gr")));

    }
}