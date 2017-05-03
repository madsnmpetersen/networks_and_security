/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigInteger;
import java.util.Arrays;
/**
 *
 * @author ShameOnU
 */
public class test {
    
    public static void main(String args[])
    {
        BigInteger param_a = new BigInteger("13"); BigInteger param_b = new BigInteger("1260");
        System.out.println("Calling extended euclidean algorithm with inputs "+param_a+" and "+param_b+" = " + Arrays.toString(NumberHelpers.extendedEuclideanAlgorithm(param_a, param_b)));
        
        BigInteger minN = new BigInteger("13817467"); //(27^4 * 26) + 1
        int bitLen = minN.bitLength();
        System.out.println("Minimum number of bits needed to represent n = "+minN+": "+bitLen);
        System.out.println( "Creating new Rsa instance with max bit size of n = "+(bitLen-1)+" meaning p and q must be set up with a max bit size of around "+((bitLen-1)/2));
        Rsa malfunctioning = new Rsa((bitLen-1)/2);
        BigInteger n = malfunctioning.returnN();
        System.out.print("n is: "+n+" this is ");
        
        if(n.compareTo(minN) >= 0)
            System.out.println("big enough");
        else
            System.out.println("NOT big enough");
        
        String msg = "zzzzz";
        System.out.println("Encrypting the message\""+msg+"\"");
        String EncryptedMalfunctioningMsg;
        EncryptedMalfunctioningMsg = malfunctioning.encryptMessage(msg);
        String DecryptedMalfunctioningMsg = malfunctioning.decrypt(EncryptedMalfunctioningMsg);
        System.out.println("Decrypting the message to: "+DecryptedMalfunctioningMsg);
        
        System.out.println("Now trying with an Rsa instance with p and q max bit size = "+((bitLen)));
        Rsa functioning = new Rsa(((bitLen)));
        
        n = functioning.returnN();
        System.out.print("n is: "+n+" this is ");
        
        if(n.compareTo(minN) >= 0)
            System.out.println("big enough");
        else
            System.out.println("NOT big enough");
        
        System.out.println("Encrypting the message\""+msg+"\"");        
        String EncryptedFunctioningMsg = functioning.encryptMessage(msg);
        String DecryptedFunctioningMsg = functioning.decrypt(EncryptedFunctioningMsg);
        System.out.println("Decrypting the message to: "+DecryptedFunctioningMsg);
       
        BigInteger valOfa; BigInteger valOfb; BigInteger valOfc; BigInteger valOfz; BigInteger valOfWhitespace;
        valOfa = AlphabetConversion.test_charToNumber('a');
        valOfb = AlphabetConversion.test_charToNumber('b');
        valOfc = AlphabetConversion.test_charToNumber('c');
        valOfz = AlphabetConversion.test_charToNumber('z');
        valOfWhitespace = AlphabetConversion.test_charToNumber(' ');
        System.out.println("a = " + valOfa + ", b = " + valOfb + ", c = " + valOfc + ", z = " + valOfz + ", whitesspace = " + valOfWhitespace);
        char a; char b; char c; char z; char whitespace;
        a = AlphabetConversion.test_numberToChar(valOfa);
        b = AlphabetConversion.test_numberToChar(valOfb);
        c = AlphabetConversion.test_numberToChar(valOfc);
        z = AlphabetConversion.test_numberToChar(valOfz);
        whitespace = AlphabetConversion.test_numberToChar(valOfWhitespace);
        System.out.println("A: " + a + ", B: " + b + ", C: " + c + ", Z: " + z + ", whitespace:"+whitespace);
        
        
        System.out.println("converting \"this \" to number: "+AlphabetConversion.stringToNumber("this "));
        System.out.println("converting \"10793358\" to string: "+AlphabetConversion.numberToString(new BigInteger("10793358")));
        
        
        Rsa myRSA = new Rsa(512);
        String plain = "The quick brown fox jumps over the lazy dog";
        String encrypted1; String encrypted2; String encrypted3;
        String decrypted1; String decrypted2; String decrypted3;
        System.out.println("Encrypting:\"" + plain + "\" 3 times.");
        encrypted1 = myRSA.encryptMessagev2(plain);
        encrypted2 = myRSA.encryptMessagev2(plain);
        encrypted3 = myRSA.encryptMessagev2(plain);
        
        if(encrypted1.equals(encrypted2) || encrypted1.equals(encrypted3) || encrypted2.equals(encrypted3))
            System.out.println("Encrypted blocks are alike");
        else
            System.out.println("Encrypted blocks are NOT alike");
        
        decrypted1 = myRSA.decrypt(encrypted1);
        System.out.print("Block 1 Decrypted to: ");
        System.out.println(decrypted1);
        decrypted2 = myRSA.decrypt(encrypted2);
        System.out.print("Block 2 Decrypted to: ");
        System.out.println(decrypted2);
        decrypted3 = myRSA.decrypt(encrypted3);
        System.out.print("Block 3 Decrypted to: ");
        System.out.println(decrypted3);
        
        if(decrypted1.equals(decrypted2) || decrypted1.equals(decrypted3) || decrypted2.equals(decrypted3))
            System.out.println("Decrypted blocks are alike");
        else
            System.out.println("Decrypted blocks are NOT alike");

    }
}
