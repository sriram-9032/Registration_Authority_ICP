/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ug.daes.ra.dto.ApiResponses;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utility class for RA application operations.
 */
public class AppUtil {

    private static final Logger logger = LoggerFactory.getLogger(AppUtil.class);

    private static final String UPPER_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ENCRYPTION_PASSWORD_ENV = "APP_ENCRYPTION_PASSWORD";

    private static final int IV_LENGTH = 12;        // GCM recommended
    private static final int SALT_LENGTH = 16;
    private static final int TAG_LENGTH = 128;      // in bits
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;


    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    public static final String PRIVILEGE_USER = "user";

    /**
     * Generate PKI key id using SecureRandom.
     * @return 10-character random string
     */
    public static String generatePKIKeyId() {
        return genRandomNumber(UPPER_ALPHABET);
    }

    private static String genRandomNumber(String alphaNumeric) {
        StringBuilder sb = new StringBuilder();
        int length = 10;
        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(alphaNumeric.length());
            sb.append(alphaNumeric.charAt(index));
        }
        return sb.toString();
    }

    public static boolean checkPassword(String pass, String confPass) {
        return Objects.equals(pass, confPass);
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static boolean getExpiryDate(Date date) {
        return date != null && date.before(getCurrentDate());
    }

    public static Date setExpiryDate() {
        return new Date(new Date().getTime() + 1440000 * 7);
    }

    public static ApiResponses createApiResponse(boolean success, String msg, Object object) {
        ApiResponses apiResponse = new ApiResponses();
        apiResponse.setMessage(msg);
        apiResponse.setResult(object);
        apiResponse.setSuccess(success);
        return apiResponse;
    }

    public static Date getTimeStamp() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(new Timestamp(System.currentTimeMillis()).toString());
    }

    public static Date getTimeStamp(String date) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.parse(date);
    }

    public static String getUnameFromDecryptedRefreshToken(String token) {
        if (token == null) return null;
        String[] parts = token.split("#");
        return parts.length > 1 ? parts[1] : null;
    }

    /**
     * Generates a hash code using SHA-256 (Stronger than MD5).
     */
    public static String getHashCode(Object o) {
        if (o == null) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(o.toString().getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error occurred at getHashCode", e);
            return "";
        }
    }

    public static String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

    public static String formatFilterDate(Date startDate, Date endDate) {
        String startDateStr = formatDate(startDate);
        String endDateStr = formatDate(endDate);
        if (startDateStr.equals(endDateStr)) {
            return startDateStr;
        } else {
            return startDateStr + " to " + endDateStr;
        }
    }

    public static String getZipPathJson(List<String> paths) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(paths);
        } catch (JsonProcessingException e) {
            logger.error("JSON processing error", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<String> getListFromPathJson(String zipPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (zipPath != null && !zipPath.isEmpty()) {
                return objectMapper.readValue(zipPath, List.class);
            }
        } catch (Exception e) {
            logger.error("JSON parsing error", e);
        }
        return new ArrayList<>();
    }

    public static String generateRefrenceNo(String packageRefNumber) {
        if (packageRefNumber != null) {
            return UUID.nameUUIDFromBytes(packageRefNumber.getBytes(StandardCharsets.UTF_8)).toString().toUpperCase();
        } else {
            return UUID.randomUUID().toString().toUpperCase();
        }
    }

    public static Map<String, String> getCustomerDetailsHM(String customerDtlsJson) throws IOException {
        try {
            return new ObjectMapper().readValue(customerDtlsJson, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            logger.error("Error mapping customer details", e);
            return Collections.emptyMap();
        }
    }

    /**
     * Encrypts plaintext using AES-GCM with a secret from Environment Variables.
     * Fixed: Credentials are no longer hard-coded. Memory is zeroed out.
     */
    public static String encrypt(String plainText) {
        if (plainText == null) return null;

        String envPassword = System.getenv(ENCRYPTION_PASSWORD_ENV);
        if (envPassword == null || envPassword.isEmpty()) {
            throw new IllegalStateException("Environment variable " + ENCRYPTION_PASSWORD_ENV + " is not set.");
        }

        char[] passwordChars = envPassword.toCharArray();

        try {
            byte[] salt = new byte[SALT_LENGTH];
            SECURE_RANDOM.nextBytes(salt);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(passwordChars, salt, ITERATIONS, KEY_LENGTH);

            byte[] keyBytes = factory.generateSecret(spec).getEncoded();
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            byte[] iv = new byte[IV_LENGTH];
            SECURE_RANDOM.nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);

            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            ByteBuffer byteBuffer = ByteBuffer.allocate(salt.length + iv.length + cipherText.length);
            byteBuffer.put(salt).put(iv).put(cipherText);

            return Base64.getEncoder().encodeToString(byteBuffer.array());

        } catch (Exception e) {
            logger.error("Encryption failed", e);
            throw new RuntimeException("Encryption failed", e);
        } finally {
            Arrays.fill(passwordChars, ' ');
        }
    }

    public static String getDate() {
        SimpleDateFormat smpdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return smpdate.format(new Date());
    }

    public static String getRaDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ");
        return formatter.format(new Date());
    }

    public static String getUUId() {
        return UUID.randomUUID().toString();
    }
}