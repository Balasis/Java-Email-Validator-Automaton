import exceptions.InvalidDomainFormException;

public class EmailValidatorApp {
    public static void main(String[] args) {
        try {
                       //Create your own, check comments below...
            EmailValidatorProgram validatorProgram = new EmailValidatorProgram();
            validatorProgram.createGoogleAutomaton();
            validatorProgram.createOutlookAutomaton();

            System.out.println("Is valid email (test@gmail.com): " + validatorProgram.isValidEmail("test@gmail.com"));
            System.out.println("Is valid email (test@outlook.com): " + validatorProgram.isValidEmail("test@outlook.com"));

            validatorProgram.clearAutomatons();

            System.out.println("Is valid email (test@gmail.com) after clear: " + validatorProgram.isValidEmail("test@gmail.com"));
        } catch (InvalidDomainFormException e) {
            System.out.println(e.getMessage());
        }
        //The above is just 2 default ones for fast preview ; ...the value of this app is to create your own schemas

        //Create new EmailSchema ,use CharRange objects or simply the api of EmailSchema to form the structure of your new email
        //then use this new emailSchema to create the automaton. addAutomaton(emailSchema ,false);

        //in the addAutomaton you may either choose to have case Sensitive (addAutomaton(emailSchema ,true))
        // or non case Sensitive automaton          (addAutomaton(emailSchema ,false))

        //NOTE! there is only 1 restriction. You can't mix case Sensitive and non-case-sensitive automatons;
        //Therefore you need to create a new validator OR user clear and remove methods given by the program API;

    }
}