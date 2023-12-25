package ChatApplication;

import java.math.BigInteger;
import java.security.SecureRandom;

public class cryptograph {
    private static BigInteger privateKey;
    private static BigInteger publicKey;
    private static BigInteger modulus;

    public static void generateKeys(int bitLength) {
        final SecureRandom random = new SecureRandom();
        final BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
        final BigInteger q = BigInteger.probablePrime(bitLength / 2, random);
        modulus = p.multiply(q);

        final BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        publicKey = new BigInteger("65537");
        privateKey = publicKey.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    public String decrypt(String str) {
        BigInteger encrypted = new BigInteger(str);
        return new String(encrypted.modPow(privateKey, modulus).toByteArray());
    }
}
