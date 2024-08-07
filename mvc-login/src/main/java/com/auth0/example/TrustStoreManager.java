package com.auth0.example;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

public class TrustStoreManager {
    public static KeyStore createTrustStore(X509Certificate[] certificates) throws Exception {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(null, null);

        for(int i=0;i<certificates.length;i++){
        trustStore.setCertificateEntry("server-cert-"+i, certificates[i]);
        }
        return trustStore;
    }
}
