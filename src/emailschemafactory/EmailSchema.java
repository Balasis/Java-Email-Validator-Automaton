package emailschemafactory;

import java.util.ArrayList;
import java.util.List;

public class EmailSchema {
    private String mainDomain;
    private List<String> domains; // Allowed domains
    private List<CharRange> allowedCharRanges; // Ranges of allowed characters for the username

    public EmailSchema(String mainDomain) throws InvalidDomainFormException {//TODO: create method to check if valid domain
        this.mainDomain = formatDomainString(mainDomain);
        domains = new ArrayList<>();
        allowedCharRanges = new ArrayList<>();
        domains.add(mainDomain);
    }

    public void addToDomains(String domainStringNoAT){
        domains.add(domainStringNoAT);
    }

    public void allowedCharRanges(CharRange range){
        allowedCharRanges.add(range);
    }

    public String getMainDomain() {
        return mainDomain;
    }

    public List<String> getDomains() {
        return domains;
    }

    public List<CharRange> getAllowedCharRanges() {
        return allowedCharRanges;
    }


    private String formatDomainString(String domainString) throws InvalidDomainFormException {
        String startedATremovedIfExist=removeTheATSymbolIfExist(domainString);
        checkBasicDomainStructure(startedATremovedIfExist);
        return toLowerCase(startedATremovedIfExist);
    }

    private String removeTheATSymbolIfExist(String domainString) {
        if (domainString.charAt(0) == '@') {
            return domainString.substring(1);
        }
        return domainString;
    }

    private void checkBasicDomainStructure(String domainString) throws InvalidDomainFormException {
        if (domainString.indexOf('@') != -1){
            throw new InvalidDomainFormException("'@' is skippable(auto added) but not allowed elsewhere than first char");
        }
        if(domainString.indexOf('.') == -1){
            throw new InvalidDomainFormException("'.' required at least once in your domain");
        }
    }

    private String toLowerCase(String domainString) {
       return domainString.toLowerCase();
    }



    public String toString(){
        return mainDomain +" : " + domains + "" + allowedCharRanges;
    }
}
