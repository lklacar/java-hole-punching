import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by luka on 29.1.16..
 */
public class UDPHolePunchingServer {

    public static void main(String args[]) throws Exception {

        DatagramSocket serverSocket1 = new DatagramSocket(7070);

        System.out.println("Waiting for Client 1 on Port "
                + serverSocket1.getLocalPort());

        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        serverSocket1.receive(receivePacket);


        InetAddress IPAddress1 = receivePacket.getAddress();
        int port1 = receivePacket.getPort();
        String msgInfoOfClient1 = IPAddress1 + "-" + port1 + "-";

        System.out.println("Client1: " + msgInfoOfClient1);

        DatagramSocket serverSocket2 = new DatagramSocket(7071);

        System.out.println("Waiting for Client 2 on Port "
                + serverSocket2.getLocalPort());


        receivePacket = new DatagramPacket(new byte[1024], 1024);
        serverSocket2.receive(receivePacket);

        InetAddress IPAddress2 = receivePacket.getAddress();
        int port2 = receivePacket.getPort();
        String msgInfoOfClient2 = IPAddress2 + "-" + port2 + "-";

        System.out.println("Client2:" + msgInfoOfClient2);

        serverSocket1.send(new DatagramPacket(msgInfoOfClient2.getBytes(),
                msgInfoOfClient2.getBytes().length, IPAddress1, port1));

        serverSocket2.send(new DatagramPacket(msgInfoOfClient1.getBytes(),
                msgInfoOfClient1.getBytes().length, IPAddress2, port2));

        serverSocket1.close();
        serverSocket2.close();
    }
}