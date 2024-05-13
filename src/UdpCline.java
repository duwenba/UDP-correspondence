import java.io.IOException;
import java.net.*;


public class UdpCline {
    InetAddress ip;
    int port;
    DatagramSocket socket;

    public void send(String ip, int port, String message) {
        try {
            this.ip = InetAddress.getByName(ip);
            this.port = port;
            this.socket = new DatagramSocket(4567);
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, this.ip, this.port);
            this.socket.send(packet);
//            this.socket.close();
            System.out.println("Message sent to " + ip + ":" + port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String receive() {
        try {
//            this.socket = new DatagramSocket(4567);
            System.out.println("Waiting for message on port 4567...");
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            this.socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            this.socket.close();
            return message;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void main(String[] args) {
        UdpCline client = new UdpCline();
        client.send("127.0.0.1", 9876, "Hello, World!");
        String message = client.receive();
        System.out.println("Received message: " + message);
    }
}
