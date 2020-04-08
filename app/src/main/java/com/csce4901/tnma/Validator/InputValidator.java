package com.csce4901.tnma.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.csce4901.tnma.Constants.UserConstant.EVENT_DATE_FORMAT;

public enum InputValidator
{
    EMAIL("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
    PASSWORD("((?=.*[a-z]).{6,40})");

    private String pattern;
        /*
        (?=.*[a-z])     : This matches the presence of at least one lowercase letter.
        (?=.*d)         : This matches the presence of at least one digit i.e. 0-9.
        (?=.*[@#$%])    : This matches the presence of at least one special character.
        ((?=.*[A-Z])    : This matches the presence of at least one capital letter.
        {6,16}          : This limits the length of password from minimum 6 letters to maximum 16 letters.
     */

    InputValidator(final String inPattern) {
        this.pattern = inPattern;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean validateInput(String patternName, String input){
        boolean validInput = false;
        String pattern1 = InputValidator.valueOf(patternName).getPattern();
        Pattern pattern = Pattern.compile(pattern1);
        Matcher matcher = pattern.matcher(input);
        validInput = matcher.matches();
        return validInput;
    }

    public boolean validateEventDate(String input){
        boolean isValid = true;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EVENT_DATE_FORMAT);
        try {
            Date date = simpleDateFormat.parse(input);
            if(date == null){
                isValid = false;
            }
        } catch (ParseException e) {
            isValid = false;
        }
        return isValid;
    }
}