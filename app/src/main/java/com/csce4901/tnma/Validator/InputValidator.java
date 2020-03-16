package com.csce4901.tnma.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum InputValidator
{
    EMAIL("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
    PASSWORD("((?=.*[a-z])(?=.*d)(?=.*[A-Z]).{6,16})");

        /*
        (?=.*[a-z])     : This matches the presence of at least one lowercase letter.
        (?=.*d)         : This matches the presence of at least one digit i.e. 0-9.
        (?=.*[@#$%])    : This matches the presence of at least one special character.
        ((?=.*[A-Z])    : This matches the presence of at least one capital letter.
        {6,16}          : This limits the length of password from minimum 6 letters to maximum 16 letters.
     */

    private String pattern;

    protected String getPattern()
    {
        return this.pattern;
    }

    private InputValidator(String pattern)
    {
        this.pattern = getPattern();
    }

    public boolean validateInput(String patternName, String input){
        boolean validInput = false;
        InputValidator validator = InputValidator.valueOf(patternName);
        Pattern pattern = Pattern.compile(validator.getPattern());
        Matcher matcher = pattern.matcher(input);
        validInput = matcher.matches();
        return validInput;
    }
}
