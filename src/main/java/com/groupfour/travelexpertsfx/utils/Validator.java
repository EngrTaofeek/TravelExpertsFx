package com.groupfour.travelexpertsfx.utils;

import java.util.regex.Pattern;

/**
 * @Author DarylWxc
 * @Date 3/5/2025
 * @Description validator
 */
public class Validator {

    /**
     * check if the string is empty
     *
     * @param str
     */
    public static void isEmpty(String str, String message) {
        if (str.isEmpty() || str == null) {
            throw new RuntimeException(message);
        }
    }

    /**
     * validate firstName,lastName
     *
     * @param name
     */
    public static void validateName(String name) {
        String regex = "[A-Za-z][A-Za-z-' ]{1,49}$";
        if (name.isEmpty()) {
            throw new RuntimeException("please enter a name");
        } else if (!Pattern.matches(regex, name)) {
            throw new RuntimeException("please enter valid name");
        }
    }

    /**
     * validate phoneNumber
     *
     * @param phoneNumber
     */
    public static void validatePhone(String phoneNumber) {
        String regex = "^\\+?[0-9\\s\\-()]{10,15}$";
        if (phoneNumber.isEmpty()) {
            throw new RuntimeException("please enter a phone number");
        } else if (!Pattern.matches(regex, phoneNumber)) {
            throw new RuntimeException("please enter valid phone number");
        }
    }

    /**
     * validate fax
     *
     * @param fax
     */
    public static void validateFax(String fax) {
        String regex = "^\\+?[0-9\\s\\-()]{10,15}$";
        if (fax.isEmpty()) {
            throw new RuntimeException("please enter a fax number");
        } else if (!Pattern.matches(regex, fax)) {
            throw new RuntimeException("please enter valid fax number");
        }
    }

    /**
     * validate middleName
     *
     * @param name
     */
    public static void validateMiddleName(String name) {
        String regex = "^[A-Za-z-' ]{1,50}$";
        if (!name.isEmpty()) {
            if (!Pattern.matches(regex, name)) {
                throw new RuntimeException("please enter valid middle name");
            }
        }
    }

    /**
     * validate email
     *
     * @param email
     */
    public static void validateEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email.isEmpty()) {
            throw new RuntimeException("please enter an email");
        } else if (!Pattern.matches(regex, email)) {
            throw new RuntimeException("please enter valid email");
        }
    }

    /**
     * validate address
     *
     * @param address
     */
    public static void validateAddress(String address) {
        String regex = "^[A-Za-z0-9.,'\\-\\s]{5,100}$";
        if (address.isEmpty()) {
            throw new RuntimeException("please enter an address");
        } else if (!address.matches(regex)) {
            throw new RuntimeException("please enter valid address");
        }
    }

    /**
     * validate city
     *
     * @param city
     */
    public static void validateCity(String city) {
        String regex = "^[A-Za-z-'\\s]{2,50}$";
        if (city.isEmpty()) {
            throw new RuntimeException("please enter a city");
        } else if (!city.matches(regex)) {
            throw new RuntimeException("please enter valid city");
        }

    }

    /**
     * validate postal
     *
     * @param postal
     */
    public static void validatePostal(String postal) {
        String regex = "^(\\d{5}(-\\d{4})?|[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d|[A-Za-z0-9 ]{5,10})$";
        if (postal.isEmpty()) {
            throw new RuntimeException("please enter a postal code");
        } else if (!postal.matches(regex)) {
            throw new RuntimeException("please enter valid postal code");
        }
    }

    /**
     * validate province
     *
     * @param prov
     */
    public static void validateProv(String prov) {
        String regex = "^[A-Za-z-'\\s]{2,50}$";
        if (prov.isEmpty()) {
            throw new RuntimeException("please enter a province");
        } else if (!prov.matches(regex)) {
            throw new RuntimeException("please enter valid province");
        }
    }

    /**
     * validate country
     *
     * @param country
     */
    public static void validateCountry(String country) {
        String regex = "^[A-Za-z-'\\s]{2,50}$";
        if (country.isEmpty()) {
            throw new RuntimeException("please enter a country");
        } else if (!country.matches(regex)) {
            throw new RuntimeException("please enter valid country");
        }
    }

    /**
     * validate price
     *
     * @param price
     * @param fieldName
     */
    public static void validatePrice(String price, String fieldName) {
        String regex = "^\\d+(\\.\\d{1,2})?$";
        if (price.isEmpty()) {
            throw new RuntimeException("please enter a " + fieldName);
        } else if (!price.matches(regex)) {
            throw new RuntimeException("please enter valid " + fieldName);
        }
    }
}
