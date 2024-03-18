package ru.s1uad_dw.OpenFurnAuthService.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientDataValidator {
    public static boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        return pattern.matcher(email).matches();
    }
    public static boolean isValidPhone(String email){
        Pattern pattern = Pattern.compile("^(8|\\\\+7)\\\\d{10}$");
        return pattern.matcher(email).matches();
    }

    public static boolean isValidUsername(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9!'\\(\\)*+\\-.\\/<=>?@\\[\\]^_`{|}~]{3,31}$");
        return pattern.matcher(email).matches();
    }
}
