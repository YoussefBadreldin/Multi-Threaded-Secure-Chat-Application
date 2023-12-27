package ChatApplication;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

			
public class cryptograph {

  private static BigInteger privateKey;
  private static  BigInteger publicKey;
  private static BigInteger modulus;
  

  public static BigInteger getPrivateKey() {
	return privateKey;
}

public static void setPrivateKey(BigInteger privateKey) {
	cryptograph.privateKey = privateKey;
}

public static BigInteger getModulus() {
	return modulus;
}

public static void setModulus(BigInteger modulus) {
	cryptograph.modulus = modulus;
}

public static void generateKeys(int bitLength) {
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
  public String decrypt(String str,BigInteger p,BigInteger m) {
     
		 BigInteger encrypted=new BigInteger(str);
		 String result;
		 result=new String(encrypted.modPow(p, m).toByteArray());
		 return result;
		    }
		    public static void main(String[] args) {
		    cryptograph rsa = new cryptograph();
	        rsa.generateKeys(1024);
	        
	        //System.out.println(rsa.privateKey+"#"+rsa.publicKey +"#"+rsa.modulus);

	        String originalMessage = "hi";
	        BigInteger plaintext = new BigInteger(originalMessage.getBytes());

	        BigInteger encrypted = rsa.encrypt(plaintext);
	        String qw=new String(encrypted.toString());
	        System.out.println("Encrypted message: " + encrypted+"  #####"+ qw);
	      //  String x=new String(encrypted.toByteArray());
	        String decrypted = rsa.decrypt(qw,rsa.privateKey,rsa.modulus);
	        System.out.println("Decrypted message: " + decrypted);
	        
	        
	        ///////////////////
	        cryptograph rsa1 = new cryptograph();
	        rsa1.generateKeys(1024);
	       
	        
	    }
}
	
		

	

