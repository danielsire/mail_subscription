package com.adidas.publicService.client.configuration;



import feign.Client;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
public class FeignCustomClientConfiguration {

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
            String allPassword = "secret";
            SSLContext sslContext = SSLContextBuilder
                    .create()
                    .loadKeyMaterial(ResourceUtils.getFile("classpath:certificates/identity.jks"), allPassword.toCharArray(), allPassword.toCharArray())
                    .loadTrustMaterial(ResourceUtils.getFile("classpath:certificates/identity.truststore.jks"), allPassword.toCharArray())
                    .build();
            return sslContext.getSocketFactory();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
