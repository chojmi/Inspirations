package com.github.chojmi.inspirations.data.source.remote.signing;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class SignatureProvider {
    private final String key;

    public SignatureProvider(@NonNull String key) {
        this.key = checkNotNull(key);
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
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes("UTF-8"), 0, s.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            Timber.e(e);
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
        StringBuffer buffer = new StringBuffer(key);
        sortedArgs.forEach((s, s2) -> {
            buffer.append(s);
            buffer.append(s2);
        });
        return buffer.toString();
    }
}
