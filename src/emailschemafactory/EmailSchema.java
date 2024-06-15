package emailschemafactory;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmailSchema {
    private final String mainDomain;
    private final List<String> domains; // Allowed domains
    private final List<CharRange> usernameInvalidFirstChars;
    private final List<CharRange> usernameInvalidLastChars;
    private final List<CharRange> allowedCharRanges; // Ranges of allowed characters for the username

    public EmailSchema(String mainDomain) throws InvalidDomainFormException {//TODO: create method to check if valid domain
        this.mainDomain = formatDomainString(mainDomain);
        this.usernameInvalidFirstChars = new ArrayList<>();
        this.usernameInvalidLastChars = new ArrayList<>();
        domains = new ArrayList<>();
        allowedCharRanges = new ArrayList<>();
        domains.add(mainDomain);
        addFirstUsernameInvalidChars('_','-','@','.');
        addLastUsernameInvalidChars('_','-','@','.');
    }

    public void addToDomains(String domainStringNoAT) throws InvalidDomainFormException {
        domains.add( formatDomainString(domainStringNoAT) );
    }

    public void addAllowedCharRanges(CharRange range){
        allowedCharRanges.add(range);
    }

    public void addAllBasicCharRanges(){
        addAllowedCharRanges(new CharRange('A','Z'));
        addAllowedCharRanges(new CharRange('a','z'));
        addAllowedCharRanges(new CharRange('0','9'));
        addAllowedCharRanges(new CharRange('_','_'));
        addAllowedCharRanges(new CharRange('.','.'));
        addAllowedCharRanges(new CharRange('-','-'));
    }

    public void addFirstUsernameInvalidChars(char... character ){
        addInvalidChars(true, character);
    }

    public void addFirstUsernameInvalidChars(CharRange... charRange){
        addInvalidChars(true, charRange);
    }

    public void addFirstUsernameInvalidChars(Collection<CharRange> collection){
        addInvalidChars(true, collection);
    }

    public void addLastUsernameInvalidChars(char... character){
        addInvalidChars(false, character);
    }

    public void addLastUsernameInvalidChars(CharRange... charRange){
        addInvalidChars(false, charRange);
    }

    public void addLastUsernameInvalidChars(Collection<CharRange> collection){
        addInvalidChars(false, collection);
    }

    private void addInvalidChars(boolean isItFirst,CharRange... charRange){
        for (CharRange cR : charRange){
            boolean isFromAndToExist = false;
            for (CharRange existingCr : usernameInvalidFirstChars) {
                if (cR.from() == existingCr.from() && cR.to() == existingCr.to()) {
                    isFromAndToExist = true;
                    System.out.println(cR.from() + " | "+cR.to() + " exists");
                    break;
                }
            }
            if (!isFromAndToExist){
                usernameInvalidFirstChars.add(cR);
            }
        }
    }

    private void addInvalidChars(boolean isItFirst, char... character ){
        for (char c : character){
            addInvalidChars( isItFirst, new CharRange(c,c));
        }
    }

    private void addInvalidChars(boolean isItFirst,Collection<CharRange> collection){
        for (CharRange cR : collection){
            addInvalidChars(isItFirst, cR);
        }
    }

    private String formatDomainString(String domainString) throws InvalidDomainFormException {
        String startedATremovedIfExist=removeTheATSymbolIfExist(domainString);
        checkBasicDomainStructure(startedATremovedIfExist);
        return startedATremovedIfExist;
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

    public String getMainDomain() {
        return mainDomain;
    }

    public List<String> getDomains() {
        return domains;
    }

    public List<CharRange> getAllowedCharRanges() {
        return allowedCharRanges;
    }

    public String toString(){
        return mainDomain +" : " + domains + "" + allowedCharRanges;
    }
}