package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.nio.ByteBuffer;
import java.lang.Math;
import java.io.*;


public class Server{
  public final static int SERVICE_PORT=50001;
 
  public static void main(String[] args) throws IOException{
    try{
        // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
        DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
        
        /* Создайте буферы для хранения отправляемых и получаемых данных.
  Они временно хранят данные в случае задержек связи */
        byte[] receivingDataBuffer = new byte[10];
        byte[] sendingDataBuffer = new byte[10];
        
        /* Создайте экземпляр UDP-пакета для хранения клиентских данных с использованием буфера для полученных данных */
        DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        System.out.println("Waiting for a client to connect...");
        
        // Получите данные от клиента и сохраните их в inputPacket
        serverSocket.receive(inputPacket);
        int x = inputPacket.getData()[0];
        
        int y = inputPacket.getData()[1];

        int z = inputPacket.getData()[2];
        
        double itog = (Math.pow(y, x)+Math.sqrt(Math.abs(x)+Math.pow(2.72, y)
         -((Math.pow(z, 3)*Math.pow(Math.sin(y), 2))/(y+( (Math.pow(z, 3))/(y - Math.pow(z, 3)) )))));

        
         try(FileWriter writer = new FileWriter("1.txt", false))
         {
            // запись всей строки
            writer.write("x: "+x+"\n");
            writer.write("y: "+y+"\n");
            writer.write("z: "+z+"\n");
            writer.write("итог: "+itog+"\n");
             writer.flush();
         }
         catch(IOException ex){
              
             System.out.println(ex.getMessage());
         } 
        sendingDataBuffer = Double.toString(itog).getBytes();
        
        // Получите IP-адрес и порт клиента
        InetAddress senderAddress = inputPacket.getAddress();
        int senderPort = inputPacket.getPort();
        
        // Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
        DatagramPacket outputPacket = new DatagramPacket(
          sendingDataBuffer, sendingDataBuffer.length,
          senderAddress,senderPort
        );
        
        // Отправьте пакет клиенту
        serverSocket.send(outputPacket);
        // Закройте соединение сокетов
        serverSocket.close();
      }
      catch (SocketException e){
        e.printStackTrace();
        }
    }
}
