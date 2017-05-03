
import java.math.BigInteger;

public class AlphabetConversion 
{

	/**
	 * Converts a char to a BigInteger according to the alphabet conversion in the assignment
	 * @param c the char to be converted
	 * @return The new BigInteger
	 */
	private static BigInteger charToNumber(char c) 
        {
            if(Character.isLetter(c))
                return new BigInteger(""+(Character.getNumericValue(c)-9));
            else 
                return BigInteger.ZERO;
	}

	/**
	 * Converts a number in the interval [0:26] to the corresponding char
	 * @param number The number to convert
	 * @return The new char
	 */
	private static char numberToChar(BigInteger number) 
        {
            if ((number.compareTo(BigInteger.ZERO) < 0) || (number.compareTo(new BigInteger("26")) > 0)) 
            {
                throw new IllegalArgumentException("Must be a number between 0 and 26 inclusive");
            }
            else if(number.equals(BigInteger.ZERO))
                return' ';
            else
                return Character.toLowerCase(Character.forDigit(number.intValue()+9, 36));
	}


	/**
	 * Converts a number to a string, according to the alphabet conversion rules of the assignment
	 * @param number
	 * @return
	 */
	public static String numberToString(BigInteger number) 
        {
            BigInteger[] rem;
            char[] chars = new char[5];
            for(int i = 0; i < 5; i++)
            {
                rem = number.divideAndRemainder(new BigInteger("27"));
                chars[4-i] = numberToChar(rem[1]);
                number = rem[0];
            }
            String result = "";
            for(int i = 0; i < 5; i++)
                result = result + chars[i];
            return result;
	}


	/**
	 * Convert a string of length 5 to a BigInteger number
	 * @param string The string to convert
	 * @return The converted number
	 */
	public static BigInteger stringToNumber(String string) 
        {
            //Random myRand = new Random();
            //BigInteger BI27 = new BigInteger("27");
            BigInteger result = BigInteger.ZERO;
            char[] toConvert = string.toCharArray();
            for(int i = 0; i < 5; i++)
            {
                result = result.add(charToNumber(toConvert[i]).multiply(new BigInteger(Integer.toString((int)Math.pow(27, 4-i)))));
            }
            //result = result.add(new BigInteger(Integer.toString(myRand.nextInt(1) * 27)));
            return result;
        }
        
        public static BigInteger test_charToNumber(char c)
        {
            return charToNumber(c);
        }
        
        public static char test_numberToChar(BigInteger number)
        {
            return numberToChar(number);
        }
}
