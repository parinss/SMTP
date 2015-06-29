import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sender {
	public static void main(String args[]) throws UnknownHostException,
			IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Socket socket11 = new Socket("localhost", 4080);

		PrintWriter output = new PrintWriter(new OutputStreamWriter(
				socket11.getOutputStream()), true);
		try {
			System.out.println("Sending from parin@fcnproj.com");
			String senderid = "parin@fcnproj.com";
			output.println(senderid);
			output.flush();
			System.out.println("Please enter the recipients ID");
			String receiver = br.readLine();
			output.println(receiver);
			output.flush();
			System.out.println("Subject");
			String Subject = br.readLine();
			output.println(Subject);
			output.flush();
			System.out.println("Text");
			String s = br.readLine();
			output.println(s);
			output.flush();
		} catch (Exception e) {

		} finally {
			socket11.close();
		}
	}

}
