import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.*;
import java.util.*;
import java.nio.*;

public class Client_201501051 {
   private Client_201501051() {}

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

   public static byte[] encrypt(int number, byte[] bytes_key)
   {
     byte[] bytes_number = ByteBuffer.allocate(4).putInt(number).array();
     ByteBuffer bytes_encrypt = ByteBuffer.allocate(4);
     for(int i=0;i<bytes_number.length;i++)
     {
       bytes_encrypt.put((byte)(bytes_key[i%4]^bytes_number[i]));
     }
     return bytes_encrypt.array();
   }

   public static byte[] encrypt_char(char[] arr, byte[] bytes_key)
   {
     ByteBuffer bytes_encrypt = ByteBuffer.allocate(arr.length);
     for(int i=0;i<arr.length;i++)
     {
       bytes_encrypt.put((byte)(bytes_key[i%4]^arr[i]));
     }
     return bytes_encrypt.array();
   }

   public static int decrypt(byte[] number,int val)
   {
    byte[] bytes = ByteBuffer.allocate(4).putInt(val).array();
		int value=0;
		for(int i=0;i<number.length;i++)
		{
			int shift = (4 - 1 - i) * 8;
      value += ((number[i]^bytes[i%4]) & 0x000000FF) << shift;
		}
    return value;
   }

   public static String decrypt_char(byte[] number,int val)
   {
     byte[] bytes = ByteBuffer.allocate(4).putInt(val).array();
     ByteBuffer str = ByteBuffer.allocate(number.length);
     for(int i=0;i<number.length;i++)
     {
       str.put((byte)(number[i]^bytes[i%4]));
     }
     return new String(str.array());
   }

   public static void main(String[] args) {
      try {
         // Getting the registry
         Registry registry = LocateRegistry.getRegistry(null);

         // Looking up the registry for the remote object
         Interface_201501051 stub = (Interface_201501051) registry.lookup("Interface_201501051");

         // Calling the remote method using the obtained object
         Random rand = new Random();
         int g = rand.nextInt(10000) + 10;
         int a = rand.nextInt(10000) + 10;
         int p = 1000000007;
         int val = stub.generate_key(g,a,p);
         int key = new Client_201501051().power(val,a,p);
         byte[] bytes_key = ByteBuffer.allocate(4).putInt(key).array();
         while(true)
         {
           System.out.printf(">> ");
           java.io.BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
           String text_input;
           text_input = in.readLine();
           String[] words = text_input.split(" ");
           if(words[0].equals("exit"))
           {
             break;
           }
           else if(words[0].equals("primality"))
           {
             int number;
             try
             {
               number = Integer.parseInt(words[1]);
             }catch(Exception e){
               System.out.println("Argument not passed");
               continue;
             }
             byte[] bytes_encrypt = encrypt(number,bytes_key);
             if(decrypt(stub.primality(bytes_encrypt),val) == 1)
             {
               System.out.println("Yes");
             }
             else
             {
               System.out.println("No");
             }
           }
           else if(words[0].equals("fibonacci"))
           {
             int number;
             try
             {
               number = Integer.parseInt(words[1]);
             }catch(Exception e){
               System.out.println("Argument not passed");
               continue;
             }
             byte[] bytes_encrypt = encrypt(number,bytes_key);
             System.out.println(decrypt(stub.fibonacci(bytes_encrypt),val));
           }
           else if(words[0].equals("palindrome"))
           {
             char[] number;
             try
             {
               number = words[1].toCharArray();
             }catch(Exception e){
               System.out.println("Argument not passed");
               continue;
             }
             byte[] bytes_encrypt = encrypt_char(number,bytes_key);
             if(decrypt(stub.palindrome(bytes_encrypt),val) == 1)
             {
               System.out.println("Yes");
             }
             else
             {
               System.out.println("No");
             }
           }
           else if(words[0].equals("caseConvert"))
           {
             char[] number;
             try
             {
               number = words[1].toCharArray();
             }catch(Exception e){
               System.out.println("Argument not passed");
               continue;
             }
             byte[] bytes_encrypt = encrypt_char(number,bytes_key);
             System.out.println(decrypt_char(stub.caseConvert(bytes_encrypt),val));
           }
           else
           {
             System.out.println("Enter proper command");
           }
         }

         // System.out.println("Remote method invoked");
      } catch (Exception e) {
         System.err.println("Client exception: " + e.toString());
         e.printStackTrace();
      }
   }
}
