//reviewed at 28/09/2024
import emailschemafactory.CharRange;
import emailschemafactory.EmailSchema;
import exceptions.InvalidDomainFormException;

public class EmailValidatorApp {
    public static void main(String[] args) {
        EmailValidatorProgram validatorProgram = new EmailValidatorProgram();
        try {
            //Create your own, check comments below...

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
        try {
            EmailSchema mySChema = new EmailSchema("@lazy.com");//you may even skip '@'...

            mySChema.addToDomains("@hereSomeMoreDOmains.outlook.com");
            //be aware the first parameter must be smaller in char index than the second. It's a (from -to)
            //you may always use the emailSchema class methods to save time , e.x addAllRegularInvalids or addAllBasicCharRanges
            CharRange usernameCharRanges = new CharRange('A', 'Z');//for example ths will make the username accept all Caps;
            CharRange usernameCharSomeMoreRanges = new CharRange('0', '9');//here we add some numbers...
            mySChema.addAllRegularInvalids();
            validatorProgram.addAutomaton(mySChema, false);//and done... so simple
        } catch (InvalidDomainFormException e) {
            throw new RuntimeException(e);
        }


    }
}