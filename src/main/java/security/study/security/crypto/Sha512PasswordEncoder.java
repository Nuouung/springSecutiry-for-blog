package security.study.security.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return hasWithSHA512(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        return encodedPassword.equals(hashedPassword);
    }

    private String hasWithSHA512(String target) {

        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] digestedBytes = messageDigest.digest(target.getBytes(StandardCharsets.UTF_8));

            for (byte digestedByte : digestedBytes) {
                sb.append(Integer.toHexString(0xFF & digestedByte));
            }

        } catch (NoSuchAlgorithmException e) {
            
            throw new RuntimeException("Bad Algorithm");
        }

        return sb.toString();
    }
}
