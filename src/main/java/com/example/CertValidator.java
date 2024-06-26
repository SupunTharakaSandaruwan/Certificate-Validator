package com.example;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.util.*;

public class CertValidator {
    public static void main(String[] args) {
        Properties config = loadConfig();
        String certFilePath = config.getProperty("cert.location");
        String keystorePath = config.getProperty("keystore.location");
        String keystorePassword = config.getProperty("keystore.password");

        try {
            // Load the certificate from file
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            FileInputStream fis = new FileInputStream(certFilePath);
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(fis);
            fis.close();

            // Load the trusted root certificate from the JKS
            KeyStore keyStore = KeyStore.getInstance("JKS");
            try (FileInputStream ksFis = new FileInputStream(keystorePath)) {
                keyStore.load(ksFis, keystorePassword.toCharArray());
            }
            List<Certificate> list = Arrays.asList(new Certificate[]{certificate});
            CertPath path = null;
            try {
                path = cf.generateCertPath(list);
            } catch (CertificateException e) {
                System.out.println("Error generating certificate path - " + e.getMessage());

            }
            // Validate the certificate
            CertChecker a = new CertChecker();
            if (a.validateCertificate(path, keyStore))
                System.out.println("Certificate is valid.");
        } catch (Exception e) {
            System.err.println("Certificate validation failed: " + e.getMessage());
        }
    }

    private static Properties loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties.", e);
        }
        return properties;
    }


}
