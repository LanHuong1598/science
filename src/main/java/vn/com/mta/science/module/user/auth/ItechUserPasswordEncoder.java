package vn.com.mta.science.module.user.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ItechUserPasswordEncoder {

    // Create an object
    private static ItechUserPasswordEncoder instance = new ItechUserPasswordEncoder();

    // Make the constructor private so that this class cannot be instantiated
    private ItechUserPasswordEncoder(){}

    // Get the only object available
    public static ItechUserPasswordEncoder getInstance(){
        return instance;
    }

    private PasswordEncoder encoder = new BCryptPasswordEncoder(8);

    public PasswordEncoder getEncoder() {return encoder;}

    public String encode(String raw) {return encoder.encode(raw);}

    public boolean matches(String raw, String encoded) {return encoder.matches(raw, encoded);}
}

