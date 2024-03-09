package ru.effectivemobile.boperations.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

/**
 * Сервис чтения и записи ключей шифрования
 * <p>
 * <a href="https://www.baeldung.com/java-read-pem-file-keys">How to Read PEM File to Get Public and Private Keys</a><br>
 * <a href="http://www.java2s.com/example/java/security/gen-rsa-key-pair-and-save-to-file.html">gen RSA Key Pair And Save To File - Java Security</a><br>
 * <a href="https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file">Load RSA public key from file</a><br>
 * <a href="https://stackoverflow.com/questions/59228957/es256-jwt-validation-signatureexception-invalid-encoding-for-signature-java">ES256 JWT validation - SignatureException: invalid encoding</a><br>
 * </p>
 */
@Component
public class SecurityKeyService {
    public static final List<String> HEADERS = List.of("-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----",
            "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----");
    public static final String HEADERS_DELIMITER = "\r\n";

    public boolean saveKeyPair(KeyPair keyPair, File privateFile, File publicFile) {
        try {
            String privateData = String.join(HEADERS_DELIMITER, HEADERS.get(0),
                    getEncodedKeyString(keyPair.getPrivate()), HEADERS.get(1));
            saveToFile(privateData, privateFile);

            String publicData = String.join(HEADERS_DELIMITER, HEADERS.get(2),
                    getEncodedKeyString(keyPair.getPublic()), HEADERS.get(3));
            saveToFile(publicData, publicFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return true;
    }

    public KeyPair loadFromFile(String algorithm, File privateFile, File publicFile) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

            byte[] encoded = decodeKeyString(cleanPkcs8Headers(readFile(privateFile)));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            byte[] encodedPub = decodeKeyString(cleanPkcs8Headers(readFile(publicFile)));
            X509EncodedKeySpec keySpecPub = new X509EncodedKeySpec(encodedPub);
            PublicKey publicKey = keyFactory.generatePublic(keySpecPub);

            return new KeyPair(publicKey, privateKey);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getEncodedKeyString(Key secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public byte[] decodeKeyString(String data) {
        return Base64.getDecoder().decode(data);
    }

    public void saveToFile(String data, File dest) throws IOException {
        Files.write(dest.toPath(), data.getBytes());
    }

    public String readFile(File dest) throws IOException {
        return new String(Files.readAllBytes(dest.toPath()));
    }

    public String cleanPkcs8Headers(String data) {
        String cleaned = data.replaceAll("\\r\\n", "");
        for (String header : HEADERS) {
            cleaned = cleaned.replace(header, "");
        }
        return cleaned;
    }
}
