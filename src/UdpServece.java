import java.net.*;

public class UdpServece {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String receiveMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received message: " + receiveMessage);
                String sendMessage = "Hello, client!";
                byte[] sendData = sendMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
