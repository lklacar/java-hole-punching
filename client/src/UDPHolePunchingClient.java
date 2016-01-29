import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by luka on 29.1.16..
 */
public class UDPHolePunchingClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket clientSocket = new DatagramSocket();

        byte[] sendData = "Hello".getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData,
                sendData.length, InetAddress.getByName("localhost"), 7071);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);

        String response = new String(receivePacket.getData());
        String[] splitResponse = response.split("-");
        InetAddress ip = InetAddress.getByName(splitResponse[0].substring(1));

        int port = Integer.parseInt(splitResponse[1]);

        System.out.println("IP: " + ip + " PORT: " + port);

        int localPort = clientSocket.getLocalPort();
        clientSocket.close();
        clientSocket = new DatagramSocket(localPort);

        clientSocket.setSoTimeout(1000);

        for (int i = 0; i < 5000; i++) {
            sendData = ("Datapacket(" + i + ")").getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
            clientSocket.send(sendPacket);

            try {
                receivePacket.setData(new byte[1024]);
                clientSocket.receive(receivePacket);
                System.out.println("REC: "
                        + new String(receivePacket.getData()));

            } catch (Exception e) {
                System.out.println("SERVER TIMED OUT");
            }
        }
        
        clientSocket.close();
    }
}
