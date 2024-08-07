package com.auth0.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		try {

			// Your HTTPS connections will now ignore certificate validation
			//disableCertificateChecking();
			setupTrustStore(args[0]);


		} catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(DemoApplication.class, args);
	}


	/*public static void disableCertificateChecking() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[]{
				new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
					public void checkClientTrusted(X509Certificate[] certs, String authType) {}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {}
				}
		};

		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier allHostsValid = (hostname, session) -> true;
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}*/

	public static void setupTrustStore(String host) throws Exception {

		int port = 443;

		X509Certificate[] certificates = CertificateDownloader.downloadCertificates(host, port);
		KeyStore trustStore = TrustStoreManager.createTrustStore(certificates);
		SSLContext sslContext = SSLContextInitializer.createSSLContext(trustStore);

		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

	}
}
