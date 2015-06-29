import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
	public static void main(String arg[]) throws NumberFormatException,
			IOException {
		ServerSocket s3 = null;
		try {
			s3 = new ServerSocket(9080);
			while (true) {
				Socket serverSocket = s3.accept();
				BufferedReader input = new BufferedReader(
						new InputStreamReader(serverSocket.getInputStream()));

				String mailfrom = input.readLine();
				String receivedto = input.readLine();
				String data = input.readLine();
				System.out.println(mailfrom);
				System.out.println(receivedto);
				System.out.println(data);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			s3.close();
		}
	}
}
