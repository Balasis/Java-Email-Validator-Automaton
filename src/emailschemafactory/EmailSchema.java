package emailschemafactory;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmailSchema {
    private final String mainDomain;
    private final List<String> domains;
    private final List<CharRange> usernameInvalidConsecutiveChars;
    private final List<CharRange> usernameInvalidFirstChars;
    private final List<CharRange> usernameInvalidLastChars;
    private final List<CharRange> allowedCharRanges;

    public EmailSchema(String mainDomain) throws InvalidDomainFormException {
        this.mainDomain = formatDomainString(mainDomain);
        this.usernameInvalidConsecutiveChars = new ArrayList<>();
        this.usernameInvalidFirstChars = new ArrayList<>();
        this.usernameInvalidLastChars = new ArrayList<>();
        domains = new ArrayList<>();
        allowedCharRanges = new ArrayList<>();
        domains.add(mainDomain);
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

    public void addAllRegularInvalids(){
        addAllRegularFirstCharInvalids();
        addAllRegularLastCharInvalids();
        addAllRegularNoConsecutiveChar();
    }

    private void addAllRegularFirstCharInvalids(){
        addFirstUsernameInvalidChars(new CharRange('0','9'));
        addFirstUsernameInvalidChars('_');
        addFirstUsernameInvalidChars('.');
        addFirstUsernameInvalidChars('-');
    }

    private void addAllRegularLastCharInvalids(){
        addLastUsernameInvalidChars('_');
        addLastUsernameInvalidChars('.');
        addLastUsernameInvalidChars('-');
    }

    private void addAllRegularNoConsecutiveChar(){
        addUsernameInvalidConsecutiveChars('_');
        addUsernameInvalidConsecutiveChars('.');
        addUsernameInvalidConsecutiveChars('-');
    }


    public void addUsernameInvalidConsecutiveChars(char... character ){
        addCharsToList( usernameInvalidConsecutiveChars, character);
    }

    public void addUsernameInvalidConsecutiveChars(CharRange... charRange){
        addCharsToList(usernameInvalidConsecutiveChars, charRange);
    }

    public void addUsernameInvalidConsecutiveChars(Collection<CharRange> collection){
        addCharsToList(usernameInvalidConsecutiveChars, collection);
    }

    public void addFirstUsernameInvalidChars(char... character ){
        addCharsToList( usernameInvalidFirstChars, character);
    }

    public void addFirstUsernameInvalidChars(CharRange... charRange){
        addCharsToList(usernameInvalidFirstChars, charRange);
    }

    public void addFirstUsernameInvalidChars(Collection<CharRange> collection){
        addCharsToList(usernameInvalidFirstChars, collection);
    }

    public void addLastUsernameInvalidChars(char... character){
        addCharsToList(usernameInvalidLastChars, character);
    }

    public void addLastUsernameInvalidChars(CharRange... charRange){
        addCharsToList(usernameInvalidLastChars, charRange);
    }

    public void addLastUsernameInvalidChars(Collection<CharRange> collection){
        addCharsToList(usernameInvalidLastChars, collection);
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

    private void addCharsToList( List<CharRange> theList, CharRange... charRange){
        List<CharRange> listAffected = theList;
        for (CharRange cR : charRange){
            boolean isFromAndToExist = false;
            for (CharRange existingCr : listAffected) {
                if (cR.from() == existingCr.from() && cR.to() == existingCr.to()) {
                    isFromAndToExist = true;
                    break;
                }
            }
            if (!isFromAndToExist){
                listAffected.add(cR);
            }
        }
    }

    private void addCharsToList(List<CharRange> theList, char... character ){
        for (char c : character){
            addCharsToList( theList, new CharRange(c,c));
        }
    }

    private void addCharsToList(List<CharRange> theList, Collection<CharRange> collection){
        for (CharRange cR : collection){
            addCharsToList(theList, cR);
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

    public List<CharRange> getUsernameInvalidFirstChars() {
        return usernameInvalidFirstChars;
    }

    public List<CharRange> getUsernameInvalidLastChars() {
        return usernameInvalidLastChars;
    }

    public List<CharRange> getUsernameInvalidConsecutiveChars() {
        return usernameInvalidConsecutiveChars;
    }

    public String toString(){
        return mainDomain +" : " + domains + "" + allowedCharRanges;
    }
}