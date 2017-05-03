
import java.math.BigInteger;
import java.util.Random;
//import java.util.Scanner;

public class Rsa {

	private Random random;
	private BigInteger primeOne;
	private BigInteger primeTwo;

	private BigInteger n;
	private BigInteger publicKey; //Renamed e and d to make the code a bit more readable even tho they must be paired with n it makes more sense when reading the code imo.
	private BigInteger privateKey;
	private BigInteger z;


	public Rsa(Integer bits) 
        {
		random = new Random();
		BigInteger primeOne = BigInteger.probablePrime(bits, random);
		BigInteger primeTwo = BigInteger.probablePrime(bits, random);
		setUpNumbers(primeOne,primeTwo, null);
	}

	public Rsa(BigInteger primeOne, BigInteger primeTwo) 
        {
		random = new Random();
		setUpNumbers(primeOne, primeTwo, null);
	}

	public Rsa(BigInteger primeOne, BigInteger primeTwo, BigInteger e) 
        {
		random = new Random();
		setUpNumbers(primeOne, primeTwo, e);
	}

	private void setUpNumbers(BigInteger primeOne, BigInteger primeTwo, BigInteger chosenE) 
        {
		this.primeOne = primeOne;
		this.primeTwo = primeTwo;
		this.n = primeOne.multiply(primeTwo);

		z = (primeOne.subtract(BigInteger.ONE)).multiply(  (primeTwo.subtract(BigInteger.ONE)) );
		if( chosenE == null) 
                {
			publicKey = selectE(z);
		} 
                else 
                {
			if( !isValidE(chosenE, z) ) 
                        {
				throw new IllegalArgumentException("The chosen e is not relative prime to z");
			}
			publicKey = chosenE;
		}
		privateKey = selectD(publicKey,z);
	}

	private boolean isValidE(BigInteger e, BigInteger z) 
        {
		return NumberHelpers.extendedEuclideanAlgorithm(e, z)[0].equals(BigInteger.ONE);
	}

	private BigInteger selectE(BigInteger z) 
        {
		BigInteger guess = BigInteger.probablePrime(primeOne.bitLength()/3, random);
		while( !isValidE(guess, z) ) 
                {
			guess = guess.add(BigInteger.ONE);
		}
		return guess;
	}

	private BigInteger selectD(BigInteger e, BigInteger z) 
        {
		BigInteger d = NumberHelpers.extendedEuclideanAlgorithm(e, z)[1];
		if( d.compareTo(BigInteger.ZERO) < 0) 
                {
			return d.add(z);
		}
		return d;
	}

	/**
	 * Encrypt using the public key. Each block of 5 characters are encrypted,
         * and placed in a separate line (i.publicKey. followed by "\n"
	 * @param plaintext The text to encrypt
	 * @return The encrypted message
	 */
	public String encryptMessage(String plaintext) 
        {
            int rem = plaintext.length() % 5;
            if(rem != 0)
            {
                String zeroes2 = String.format("%" + (5-rem) + "c", ' ');
                plaintext = plaintext + zeroes2;		
            }
            BigInteger unencryptedBlock; BigInteger encryptedBlock;
            String result = "";
            for(int i = 0; i < plaintext.length(); i = i + 5)
            {
                unencryptedBlock = AlphabetConversion.stringToNumber(plaintext.substring(i, i+5));
                encryptedBlock = NumberHelpers.recursiveModularExponentiation(unencryptedBlock, publicKey , n);
                result = result + encryptedBlock + '\n';                
            }
		return result;
	}
        
        /**
	 * Encrypt using the public key. Does the same as encrypt except there's a twist to make the same plaintext encrypt to different ciphers.
	 * @param plaintext The text to encrypt
	 * @return The encrypted message
	 */
	public String encryptMessagev2(String plaintext) 
        {
            int rem = plaintext.length() % 5;
            if(rem != 0)
            {
                String zeroes2 = String.format("%" + (5-rem) + "c", ' ');
                plaintext = plaintext + zeroes2;		
            }
            BigInteger unencryptedBlock; BigInteger encryptedBlock;
            String result = "";
            for(int i = 0; i < plaintext.length(); i = i + 5)
            {
                unencryptedBlock = AlphabetConversion.stringToNumber(plaintext.substring(i, i+5));
                encryptedBlock = NumberHelpers.recursiveModularExponentiation(unencryptedBlock, publicKey , n);
                encryptedBlock = encryptedBlock.add(new BigInteger(n.bitLength()/4,random).multiply(n));
                result = result + encryptedBlock + '\n';                
            }
		return result;
	}

	/**
	 * Decrypt using the private key. The ciphertext has a block of encrypted text on each line.
	 * @param cipherText Encrypted text to decrypt
	 * @return Decrypted plaintext message
	 */
	public String decrypt(String cipherText) 
        {
            BigInteger encryptedBlock;
            BigInteger decryptedBlock;
            String result = "";
            int startOfBlock = 0;
            int endOfBlock;
            while(startOfBlock < cipherText.length())
            {
                endOfBlock = cipherText.indexOf("\n", startOfBlock);
                encryptedBlock = new BigInteger(cipherText.substring(startOfBlock, endOfBlock));
                decryptedBlock = NumberHelpers.recursiveModularExponentiation(encryptedBlock, privateKey, n);
                result = result + AlphabetConversion.numberToString(decryptedBlock);
                startOfBlock = endOfBlock+1;
            }
            return result;
	}
        
        public void printImportantStuff() //for testing purposes.
        {
            System.out.println("The public key is: "+publicKey);
            System.out.println("The private key is: "+privateKey);
            System.out.println("Prime 1 is: "+primeOne);
            System.out.println("Prime 2 is: "+primeTwo);
        }
        
        public BigInteger returnN() //for testing purposes.
        {
            return n;
        }
                
}
