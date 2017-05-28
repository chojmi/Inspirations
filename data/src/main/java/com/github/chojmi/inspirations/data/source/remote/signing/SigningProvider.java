package com.github.chojmi.inspirations.data.source.remote.signing;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.github.chojmi.presentation.data.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

public class SigningProvider {

    @Inject
    public SigningProvider() {
    }

    public String sha1(String s, String keyString) throws
            UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return Base64.encodeToString(bytes, 0);
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Map<String, String> provideSigArg(Map<String, String> args) {
        args.put("api_sig", calcSig(args));
        return args;
    }

    @NonNull
    private String calcSig(Map<String, String> args) {
        Map<String, String> sortedArgs = new TreeMap<>(args);
        String sigString = createSigString(sortedArgs);
        return md5(sigString);
    }

    private String createSigString(Map<String, String> sortedArgs) {
        StringBuffer buffer = new StringBuffer(BuildConfig.API_SECRET_KEY);
        sortedArgs.forEach((s, s2) -> {
            buffer.append(s);
            buffer.append(s2);
        });
        return buffer.toString();
    }
}
