package Common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


public class NetworkManager {
    private InetAddress localAddress = null;

    public InetAddress getLocalAddress(String interfaceName) {
        try {
            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

            while (inetAddresses.hasMoreElements()) {
                InetAddress ia = inetAddresses.nextElement();
                if (!ia.isLinkLocalAddress()) {
                    if (!ia.isLoopbackAddress()) {
                        System.out.println(ni.getName() + " =>   IP: " + ia.getHostAddress());
                        localAddress = ia;
                    }
                }
            }
            
            return localAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    
    public String getOwnIp(String interfaceName) {
        try {
            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

            while (inetAddresses.hasMoreElements()) {
                InetAddress ia = inetAddresses.nextElement();

                if (!ia.isLoopbackAddress()) {
                    System.out.println(ni.getName() + " =>   IP: " + ia.getHostAddress());
                    return ia.getHostAddress();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
