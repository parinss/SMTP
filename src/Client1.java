import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Client1 {
	public static void main(String args[]) throws IOException {
		Client1 c = new Client1();
		c.toReceive();
	}

	public void toReceive() throws IOException {
		ServerSocket s3 = null;
		System.out.println("hi");
		try {
			s3 = new ServerSocket(8091);
			System.out.println("how are you");
			
			while (true) {
				Socket serverSocket = s3.accept();
				BufferedReader input = new BufferedReader(
						new InputStreamReader(serverSocket.getInputStream()));
				//String a=input.readLine();
				String mailfrom = input.readLine();
				String receivedto = input.readLine();
				String data = input.readLine();
				String text = input.readLine();
				System.out.println(mailfrom);
				System.out.println(receivedto);
				System.out.println(data);
				System.out.println(text);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			s3.close();
		}
	}
}
