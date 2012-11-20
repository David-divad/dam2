import java.net.InetAddress;
import java.net.UnknownHostException;

public class InformacionHost {


	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println("error: no ha indicado ningun nombre de host");
			System.out.println("uso: InformacionHost host [, host, ...]");
		}
		
		for (String hostname : args) {

			try {
				printHostInfo(hostname);
				
			} catch (UnknownHostException e) {
				System.err.format("Imposible resolver el host: %s, ¿existe?", hostname);
			}
		}
	}

	public static void printHostInfo(String hostname) throws UnknownHostException
	{
		InetAddress[] ips = InetAddress.getAllByName(hostname);
		
		System.out.format("Informacion para el host %s:\n", hostname);
		
		for (InetAddress addr : ips) {
			System.out.format("\tdireccion ip: %s, dominio: %s, global: %s, wildcard: %s, link local: %s\n", 
					addr.getHostAddress(), 
					addr.getCanonicalHostName(),
					addr.isMCGlobal(),
					addr.isMCSiteLocal(),
					addr.isMCLinkLocal());
		}
		
		System.out.println("");
	}
}
