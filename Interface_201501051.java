import java.rmi.Remote;
import java.rmi.RemoteException;

// Creating Remote interface for our application
public interface Interface_201501051 extends Remote {
   void printMsg() throws RemoteException;
   public int generate_key(int g,int a,int p) throws RemoteException;
   public byte[] primality(byte[] number) throws RemoteException;
   public byte[] fibonacci(byte[] number) throws RemoteException;
   public byte[] palindrome(byte[] number) throws RemoteException;
   public byte[] caseConvert(byte[] number) throws RemoteException;
}
