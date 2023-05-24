package com.chillar.manoramaapps.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.chillar.manoramaapps.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shanil on 12/4/2017.
 * Basic Api class
 */
public class ApiClient {

    public static final String BASE_URL1 = "http://dev.chillarcards.com/manorama_new/app_api/api_1_0/";
//      public static final String BASE_URL1 = "http://10.1.2.220/app_api/api_1_0/";
//      public static final String BASE_URL1 = "http://mmcanteen.mm/app_api/api_1_0/";
//    public static final String BASE_URL1  = "http://192.168.0.200/manorama/app_api/api_1_0/";

    /*
    * * Retrofit adapts a Java interface to HTTP calls by using annotations on the declared methods to
     * define how requests are made. Create instances using {@linkplain Builder
     * the builder} and pass your interface to {@link #create} to generate an implementation.
    * */
    public static Retrofit getClient(Context applicationContext,String Api) {

/**
 * Checking Api from shared preference
 */

        Retrofit retrofit =null;
        SharedPreferences prefs = applicationContext.getSharedPreferences("Manorama", MODE_PRIVATE);
        String IPADDRESS = prefs.getString("IPAddress", "");
        System.out.println("MANORAMA   value ::::   "+Api);
        Retrofit.Builder builder = null;

        try {
            builder= new Retrofit.Builder().baseUrl(BASE_URL1);

            OkHttpClient okHttp = null;
            try {
                okHttp = new OkHttpClient().newBuilder().sslSocketFactory(getSSLConfig(applicationContext).getSocketFactory()).build();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = builder
                    .client(okHttp)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();



        }catch (Exception e){
            Toast.makeText(applicationContext, "You entered a wrong IP address", Toast.LENGTH_SHORT).show();

        }

        return retrofit;

    }

/*
*
* Used for SSLconnection
*
* */
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
