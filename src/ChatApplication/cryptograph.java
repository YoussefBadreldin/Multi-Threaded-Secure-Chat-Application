package ChatApplication;
import java.math.BigInteger;
import java.security.SecureRandom;

public class cryptograph {
	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger modulus;

	public void generateKeys(int bitLength) {
		final SecureRandom random = new SecureRandom();
		final BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
		final BigInteger q = BigInteger.probablePrime(bitLength / 2, random);
		modulus = p.multiply(q);

		final BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		publicKey = new BigInteger("65537");
		privateKey = publicKey.modInverse(phi);
	}

	// Encrypt message
	public BigInteger encrypt(BigInteger message) {
		return message.modPow(publicKey, modulus);
	}

	// Decrypt message
	public String decrypt(String str) {
		BigInteger encrypted;
		try {
			encrypted = new BigInteger(str);
		} catch (NumberFormatException e) {
			System.out.println("The input string is not a valid representation of a BigInteger.");
			e.printStackTrace();
			return null;
		}
		return new String(encrypted.modPow(privateKey, modulus).toByteArray());
	}
}
