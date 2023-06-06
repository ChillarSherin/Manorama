package com.chillar.manoramaapp.app;

import android.content.Context;

import com.chillar.manoramaapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shanil on 12/4/2017.
 * Basic Api class
 */
public class ApiClient {

//    public static final String BASE_URL1 = "http://dev.chillarcards.com/manorama_new/app_api/api_1_0/";
    //      public static final String BASE_URL1 = "http://10.1.2.220/app_api/api_1_0/";
      public static final String BASE_URL1 = "http://mmcanteen.mm/app_api/api_1_0/";
//    public static final String BASE_URL1  = "http://192.168.0.200/manorama/app_api/api_1_0/";
    public static Retrofit getClient(Context applicationContext, String api) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL1);
        OkHttpClient okHttp = null;
        // okHttp = new OkHttpClient().newBuilder().sslSocketFactory(getSSLConfig(applicationContext).getSocketFactory()).build();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttp = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                // Add any other configurations or interceptors as needed
                .build();

        return builder
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // Loading CAs from an InputStream
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance("X.509");

        Certificate ca = null;
        // I'm using Java7. If you used Java6 close it manually with finally.
        try {
            InputStream cert = context.getResources().openRawResource(R.raw.campuswallet);
            ca = cf.generateCertificate(cert);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }
}