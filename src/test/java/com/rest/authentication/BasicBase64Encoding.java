package com.rest.authentication;

import java.util.Base64;


public class BasicBase64Encoding {

    public static void main(String[] args) {
        String usernameColonPassword = "myUsername:myPassword";

        String bas64Encoded = Base64.getEncoder().encodeToString(usernameColonPassword.getBytes());
        System.out.println(bas64Encoded);
        byte[] decode = Base64.getDecoder().decode(bas64Encoded);
        System.out.println(new String(decode));

    }
}
