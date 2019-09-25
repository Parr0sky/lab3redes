import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class ClientTCP 
{
	public static void main(String[] args) throws NoSuchAlgorithmException 
	{
		try 
		{
			Scanner scanner = new Scanner(System.in);
			Socket socketCliente = new Socket("localhost", 8080);
			
			DataOutputStream escritor = new DataOutputStream(socketCliente.getOutputStream());
			DataInputStream lector = new DataInputStream(socketCliente.getInputStream());
			
			System.out.println("Solicito conexión" + "\n");
			System.out.println("Esperando confirmación..." + "\n");

			
			String confirmacionServidor = lector.readUTF();
			
			if(confirmacionServidor.equals("OK"))
			{
				System.out.println("Recibí confirmación"+ "\n");
				escritor.writeUTF("OK");
				System.out.println("Estoy listo para recibir archivos");
				escritor.writeUTF("OK");
				
				byte[] arrayFile = new byte[(int) 262144000];
				//byte[] arrayFiles = lector.read(arrayFile,0,arrayFile.length);	
					
				escritor.writeUTF("OK");
					
					System.out.println("Procedo a hacer la prueba de integridad");						
					
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					
			//		md.update(arrayFile);
		
				    byte[] digest = md.digest();      
		 
				
				    StringBuffer hexString = new StringBuffer();
				      
				    for (int i = 0;i<digest.length;i++) 
				    {
				        hexString.append(Integer.toHexString(0xFF & digest[i]));
				    }
				    System.out.println("Formato Hexa resultante: " + hexString.toString() + "\n"); 
				    
				    System.out.println("Ejecutando comparación..." + "\n" );
				    escritor.writeUTF(hexString.toString());
				    
				    if(lector.readUTF().equals("OK"))
				    {
				    	
				    }
				}
			
			
			socketCliente.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}
}
