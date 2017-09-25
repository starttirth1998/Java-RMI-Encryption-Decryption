import java.util.*;
import java.nio.*;

// Implementing the remote interface
public class ImplExample implements Interface_201501051 {
   public static int key;
   public static int val;
   // Implementing the interface method

   public int power(int g,int a,int p)
   {
     long result=1;
     long x = Long.valueOf(g);
     long y = Long.valueOf(a);
     while(y>0)
     {
       if(y%2 == 1)
       {
         result = (result*x)%p;
       }
       x = (x*x)%p;
       y = y/2;
     }
     return (int)result%p;
   }

   public int generate_key(int g,int a,int p)
   {
     Random rand = new Random();
     int b = rand.nextInt(10000)+10;
     val = power(g,b,p);
     key = power(val,a,p);
     return val;
   }

   public static byte[] encrypt(int number)
   {
     byte[] bytes_key = ByteBuffer.allocate(4).putInt(val).array();
     byte[] bytes_number = ByteBuffer.allocate(4).putInt(number).array();
     ByteBuffer bytes_encrypt = ByteBuffer.allocate(4);
     for(int i=0;i<bytes_number.length;i++)
     {
       bytes_encrypt.put((byte)(bytes_key[i%4]^bytes_number[i]));
     }
     return bytes_encrypt.array();
   }

   public static byte[] encrypt_char(char[] arr)
   {
     byte[] bytes_key = ByteBuffer.allocate(4).putInt(val).array();
     ByteBuffer bytes_encrypt = ByteBuffer.allocate(arr.length);
     for(int i=0;i<arr.length;i++)
     {
       bytes_encrypt.put((byte)(bytes_key[i%4]^arr[i]));
     }
     return bytes_encrypt.array();
   }

   public int decrypt(byte[] number)
   {
    byte[] bytes = ByteBuffer.allocate(4).putInt(key).array();
		int value=0;
		for(int i=0;i<number.length;i++)
		{
			int shift = (4 - 1 - i) * 8;
      value += ((number[i]^bytes[i%4]) & 0x000000FF) << shift;
		}
    return value;
   }

   public String decrypt_char(byte[] number)
   {
     byte[] bytes = ByteBuffer.allocate(4).putInt(key).array();
     ByteBuffer str = ByteBuffer.allocate(number.length);
     for(int i=0;i<number.length;i++)
     {
       str.put((byte)(number[i]^bytes[i%4]));
     }
     return new String(str.array());
   }

   public byte[] primality(byte[] number)
   {
     int num = decrypt(number);
     //System.out.println(num);
     for(int i=2;i<=Math.sqrt(num);i++)
     {
       if(num%i == 0)
       {
         return encrypt(0);
       }
     }
     return encrypt(1);
   }

   public byte[] fibonacci(byte[] number)
   {
     int num = decrypt(number);
     if(num == 1)
     {
       return encrypt(0);
     }
     if(num == 2)
     {
       return encrypt(1);
     }
     int n1 = 0;
     int n2 = 1;
     int n3 = 0;
     for(int i=2;i<num;i++)
     {
       n3 = n1 + n2;
       n1 = n2;
       n2 = n3;
     }
     return encrypt(n3);
   }

   public byte[] palindrome(byte[] number)
   {
     String str = decrypt_char(number);
     int i=0;
     int j=str.length()-1;
     while(i < j)
     {
       if(str.charAt(i) == str.charAt(j))
       {
         i++;
         j--;
       }
       else
       {
         return encrypt(0);
       }
     }
     return encrypt(1);
   }

   public byte[] caseConvert(byte[] number)
   {
     String str = decrypt_char(number);
     StringBuilder sb = new StringBuilder(str);
     for(int i=0;i<str.length();i++)
     {
       char c = sb.charAt(i);
       if(Character.isLowerCase(c))
       {
         sb.setCharAt(i, Character.toUpperCase(c));
       }
       else
       {
         sb.setCharAt(i, Character.toLowerCase(c));
       }
     }
     return encrypt_char(sb.toString().toCharArray());
   }

   public void printMsg() {
      System.out.println("This is an example RMI program");
   }
}
