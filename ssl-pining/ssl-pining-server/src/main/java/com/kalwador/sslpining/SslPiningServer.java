package com.kalwador.sslpining;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

@SpringBootApplication
public class SslPiningServer {
    public static void main(String[] args) {
        SpringApplication.run(SslPiningServer.class, args)
                .getBean(SslPiningServer.class)
                .invoke();
    }


    public void invoke() {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test() throws Exception{
        // create custom trust manager to ignore trust paths
        TrustManager trm = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { trm }, null);
        SSLSocketFactory factory =sc.getSocketFactory();
        SSLSocket socket =(SSLSocket)factory.createSocket("127.0.0.1", 8443);
        socket.startHandshake();
        SSLSession session = socket.getSession();
        java.security.cert.Certificate[] servercerts = session.getPeerCertificates();
        for (int i = 0; i < servercerts.length; i++) {
            System.out.print("-----BEGIN CERTIFICATE-----\n");
            System.out.print(Base64.encodeBase64String(servercerts[i].getEncoded()));
            System.out.print("\n-----END CERTIFICATE-----\n");
        }

        socket.close();
    }

}
