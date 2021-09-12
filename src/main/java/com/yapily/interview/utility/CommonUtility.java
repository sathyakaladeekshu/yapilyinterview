package com.yapily.interview.utility;

import com.yapily.interview.entity.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommonUtility {
    static Logger logger = LoggerFactory.getLogger(CommonUtility.class);

    static public String generateMD5Hash(String uniqueId, Optional<Key> key) throws NoSuchAlgorithmException {
        logger.info("Generating the hash key");
        String input = uniqueId+key.get().getPrivate_key()+key.get().getPublic_key();
        MessageDigest md = MessageDigest.getInstance("MD5");

        // digest() method is called to calculate message digest
        //  of an input digest() return array of byte
        byte[] messageDigest = md.digest(input.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
    return hashtext;
    }

    public String generateUUIDAsString()
{
    return UUID.randomUUID().toString();
}
}
