package com.example;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;

public class CertChecker {
    public  boolean validateCertificate(CertPath path, KeyStore trustStore)
            throws CertificateException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
        PKIXParameters params = null;
        try {

            params = new PKIXParameters(trustStore);
            params.setRevocationEnabled(false);
            certPathValidator.validate(path, params);
        } catch (CertPathValidatorException e) {
            System.out.println("Error validating certificate - " + e.getMessage());
            return false;
        } catch (InvalidAlgorithmParameterException e) {
            System.out.println("Error validating certificate - " + e.getMessage());
            return false;
        }catch (KeyStoreException e) {
            System.out.println("Error validating certificate - " + e.getMessage());
            return false;
        }
        return true;
    }
}
