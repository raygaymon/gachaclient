package gachaclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * learning points
 * dont have the if else functions here
 * just have the readutf function and let the server determine which output to send over
 * gacha is hard to program
 */
public class App {
    public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {
        String hostName = args[0];
        String port = args[1];

        Socket socket = new Socket(hostName, Integer.parseInt(port));

        String keybInput = "";
        String serverMSG = "";
        Console cons = System.console();

        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            // outputstream to print stuff to client side and send over to server
            try (OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                while (!keybInput.equals("done")) {

                    keybInput = cons.readLine("1 roll or 10 roll? \n");
                    dos.writeUTF(keybInput);
                    dos.flush();

                    // dont loop the read utf function it fucked up everything just dont risk it you not smart enough yet
                    

                    // if (keybInput.equals("ten")) {

                    //     for (int i = 0; i < 10; i++) {

                    //     serverMSG = dis.readUTF();
                    //     System.out.println(serverMSG);
                    //     }
                    // }

                    serverMSG = dis.readUTF();
                    System.out.println(serverMSG);


                }
                
                if (keybInput.equals("done")) {

                    System.out.println("Hope you got what you wanted");
                }

                dos.close();
                bos.close();
                os.close();

            } catch (EOFException ex) {

            }

            dis.close();
            bis.close();
            is.close();

        } catch (EOFException ex) {

            ex.printStackTrace();
            System.exit(0);
        }
    }
}
