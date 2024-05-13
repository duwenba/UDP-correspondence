import java.io.IOException;
import java.net.*;


public class UdpCline {
    InetAddress ip;
    int port;
    DatagramSocket socket =new DatagramSocket();

    public UdpCline() throws SocketException {
    }

    public void send(String ip, int port, String message) {
        try {
            this.ip = InetAddress.getByName(ip);
            this.port = port;
            this.socket = new DatagramSocket();
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
            // 超时重发
            this.socket.setSoTimeout(1000);
            this.socket.receive(packet);

//          this.socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            this.socket.close();
            return message;
        } catch (SocketTimeoutException e) {
            System.out.println("No message received in 1000 milliseconds.");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // read ip and port from command line arguments
        if (args.length < 2) {
            System.out.println("Usage: java UdpCline <ip> <port>");
            return;
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        // create client object
        UdpCline client = null;
        try {
            client = new UdpCline();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            // read input from user
            String input = System.console().readLine("Enter message to send: ");
            if (input.equals("exit")) {
                break;
            }
            // send message to server
            client.send(ip, port, input);
            // receive message from server
            String message = client.receive();
            // 超时重发
            if (message == null) {
                continue;
            }
            // print message to console
            System.out.println("Received message: " + message + "\n");
        }
        // close socket
        client.socket.close();
    }
}
