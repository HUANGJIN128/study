package com.kim.study.common.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    private static final Charset CHARSET_UTF8;
    private static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_ALGORITHM_SIGN = "SHA256WithRSA";
    private static final int KEY_SIZE = 1024;

    public RSAUtil() {
    }

    public static RSAUtil.RSAKeyPair generator() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024, new SecureRandom());
            KeyPair keyPair = kpg.generateKeyPair();
            return new RSAUtil.RSAKeyPair(new String(encode(keyPair.getPublic().getEncoded())), new String(encode(keyPair.getPrivate().getEncoded())));
        } catch (NoSuchAlgorithmException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String encryptByPrivateKey(String data, String privateKey) {
        return byteToString(encryptByPrivateKey(stringToByte(data), privateKey));
    }

    public static String encryptByPublicKey(String data, String publicKey) {
        return byteToString(encryptByPublicKey(stringToByte(data), publicKey));
    }

    public static String decryptByPrivateKey(String data, String privateKey) {
        return byteToString(decryptByPrivateKey(stringToByte(data), privateKey));
    }

    public static String decryptByPublicKey(String data, String publicKey) {
        return byteToString(decryptByPublicKey(stringToByte(data), publicKey));
    }

    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        return encrypt(data, getPrivateKey(privateKey));
    }

    public static byte[] encryptByPublicKey(byte[] data, String publicKey) {
        return encrypt(data, getPublicKey(publicKey));
    }

    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) {
        return decrypt(data, getPrivateKey(privateKey));
    }

    public static byte[] decryptByPublicKey(byte[] data, String publicKey) {
        return decrypt(data, getPublicKey(publicKey));
    }

    public static String signature(String data, String privateKey) {
        return signature(stringToByte(data), privateKey);
    }

    public static String signature(byte[] data, String privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(getPrivateKey(privateKey));
            signature.update(data);
            return new String(encode(signature.sign()));
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static boolean verify(byte[] data, String sign, String publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(getPublicKey(publicKey));
            signature.update(data);
            return signature.verify(decode(sign.getBytes()));
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static boolean verify(String data, String sign, String publicKey) {
        return verify(stringToByte(data), sign, publicKey);
    }

    private static byte[] encrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, key);
            return encode(rsaSplitCodec(cipher, 1, data, ((RSAKey)key).getModulus().bitLength()));
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }
    }

    private static byte[] decrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, key);
            return rsaSplitCodec(cipher, 2, decode(data), ((RSAKey)key).getModulus().bitLength());
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }
    }

    private static PublicKey getPublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(decode(publicKey.getBytes()));
            return keyFactory.generatePublic(x509KeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }
    }

    private static PrivateKey getPrivateKey(String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decode(privateKey.getBytes()));
            return keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }
    }

    private static byte[] stringToByte(String data) {
        return data.getBytes(CHARSET_UTF8);
    }

    private static String byteToString(byte[] data) {
        return CheckHeapInspectionUtil.checkToUTF8(data).toString();
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock;
        if (opmode == 2) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;

        try {
            while(datas.length > offSet) {
                byte[] buff;
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }

                out.write(buff, 0, buff.length);
                ++i;
                offSet = i * maxBlock;
            }
        } catch (Exception var12) {
            throw new RuntimeException(var12);
        }

        byte[] resultDatas = out.toByteArray();

        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException var11) {
            var11.printStackTrace();
        }

        return resultDatas;
    }

    private static byte[] encode(byte[] data) {
        return Base64.getUrlEncoder().encode(data);
    }

    private static byte[] decode(byte[] data) {
        return Base64.getUrlDecoder().decode(data);
    }

    static {
        CHARSET_UTF8 = StandardCharsets.UTF_8;
    }

    public static class RSAKeyPair {
        private String publicKey;
        private String privateKey;

        public RSAKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return this.publicKey;
        }

        public String getPrivateKey() {
            return this.privateKey;
        }
    }
}
