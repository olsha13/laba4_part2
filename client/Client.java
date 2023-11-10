package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class Client{
  
    public final static int SERVICE_PORT = 50001;
  
    public static void main(String[] args) throws IOException{
        try{
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
      
            // Создайте соответствующие буферы
            byte[] sendingDataBuffer = new byte[10];
            byte[] receivingDataBuffer = new byte[10];
      
            
            Scanner сin = new Scanner(System.in);
            System.out.print("Введите x y z: ");
            int x = сin.nextInt();
            int y = сin.nextInt();
            int z = сin.nextInt();

            sendingDataBuffer[0]=(byte)x;

            sendingDataBuffer[1]=(byte)y;
            sendingDataBuffer[2]=(byte)z;
            DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer,sendingDataBuffer.length,IPAddress, SERVICE_PORT);
            clientSocket.send(sendingPacket);
      
            // Получите ответ от сервера, т.е. предложение из заглавных букв
            DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer,receivingDataBuffer.length);
            clientSocket.receive(receivingPacket);
      
            // Выведите на экране полученные данные
            String receivedData = new String(receivingPacket.getData());
            System.out.println("Результат вычислений: "+receivedData);
      
            // Закройте соединение с сервером через сокет
            clientSocket.close();
        }
        catch(SocketException e) {
            e.printStackTrace();
        }
    }
}
