package com.adidas.publicService.client.configuration;

import feign.Client;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
public class FeignCustomClientConfiguration {

    private static final char[] KEYSTORE_PASSWORD = "secret".toCharArray();

    @Bean
    public Client feignClient() {
        Client trustSSLSockets = new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
        return trustSSLSockets;
    }

    private SSLSocketFactory getSSLSocketFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    //Do your validations
                    return true;
                }
            };
            KeyStore keyStore = loadKeyStore(getClass().getClassLoader().getResourceAsStream("certificates/identity.jks"));
            KeyStore trustStore = loadKeyStore(getClass().getClassLoader().getResourceAsStream("certificates/identity.truststore.jks"));

            SSLContext sslContext = SSLContextBuilder
                    .create()
                    .loadKeyMaterial(keyStore, KEYSTORE_PASSWORD)
                    .loadTrustMaterial(trustStore, acceptingTrustStrategy)
                    .build();
            return sslContext.getSocketFactory();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static KeyStore loadKeyStore(InputStream inputStream) throws IOException {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, KEYSTORE_PASSWORD);
            return keyStore;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            inputStream.close();
        }
    }
}
