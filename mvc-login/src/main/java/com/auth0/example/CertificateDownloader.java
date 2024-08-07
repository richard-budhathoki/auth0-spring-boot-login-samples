package com.auth0.example;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

public class CertificateDownloader {
    public static X509Certificate[] downloadCertificates(String host, int port) throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }}, new java.security.SecureRandom());

        SSLSocketFactory factory = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
        socket.startHandshake();

        X509Certificate[] serverCerts = (X509Certificate[])  socket.getSession().getPeerCertificates();



        for (X509Certificate cert : serverCerts) {
            System.out.println("Subject: " + cert.getSubjectDN());
            System.out.println("Issuer: " + cert.getIssuerDN());
            // You can save the certificate to a file or keystore here
        }
        return serverCerts;
    }
}
