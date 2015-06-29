import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ClientServer extends Thread {
	ArrayList<String> arr = new ArrayList<String>();
	Queue<ClientObj> q = new LinkedList<ClientObj>();

	public static void main(String args[]) throws IOException {
		ClientServer cs = new ClientServer();
		
		cs.arr.add("siddhi@fcnproj.com");
		cs.arr.add("parin@fcnproj.com");
		cs.arr.add("kwon@fcnproj.com");
		cs.arr.add("a");

		cs.rest();
		cs.start();
	}

	public void run() {
		try {
			System.out.println("waiting for entering intot the client method");
			this.client1();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gmail(int port) throws IOException {
		ServerSocket s = new ServerSocket(port);
		System.out.println("here2");
		Socket serverSocket = s.accept();
		System.out.println("Connected to the Server");
		BufferedReader input = new BufferedReader(new InputStreamReader(
				serverSocket.getInputStream()));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		PrintWriter output = new PrintWriter(new OutputStreamWriter(
				serverSocket.getOutputStream()), true);

		String send = br.readLine();
		output.println(send);
		System.out.println("Sent 1st message");
		String receive = input.readLine();
		System.out.println(receive);
		output.flush();
		// while (!receive.equals(".")) {
		while (!receive.equals("DATA")) {
			output.flush();
			send = br.readLine();
			output.println(send);
			receive = input.readLine();
			System.out.println(receive);
			output.flush();
		}
		output.flush();
		send = br.readLine();
		output.println(send);

		while (!receive.equals(".")) {
			receive = input.readLine();
			System.out.println(receive);
			output.flush();
		}

		send = br.readLine();
		output.println(send);
		receive = input.readLine();
		System.out.println(receive);
		output.flush();

		send = br.readLine();
		output.println(send);
		output.flush();
		serverSocket.close();
		s.close();
	}

	public void rest() throws IOException {
		
			System.out.println("here");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Socket socket21 = new Socket("localhost", 5080);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					socket21.getInputStream()));
			
			PrintWriter output = new PrintWriter(new OutputStreamWriter(
					socket21.getOutputStream()), true);
			//ClientObj c=null;
			try{
			System.out.println("finally");
			while (true) {
				//if(c!=null){
				output.println(220);
				output.flush();
				String mailfrom = null;
				ArrayList<String> recepient = new ArrayList<String>();
				ArrayList<String> data = new ArrayList<String>();
				System.out.println("done1");
				String a = input.readLine();
				System.out.println("here1");
				while (!a.contains("QUIT")) {
					if (a.contains("HELO")) {
						// System.out.println(a);
						output.println(250);
						output.flush();

					} else if (a.contains("MAIL")) {
						// System.out.println(a);
						mailfrom = a;
						output.println(250);
						output.flush();

					} else if (a.contains("RCPT")) {
						boolean flag = false;
						String b = a.substring(a.indexOf("<") + 1,
								a.indexOf(">"));
						for (int i = 0; i < arr.size(); i++) {
							if ((arr.get(i)).equals(b)) {
								//System.out.println("times");
								recepient.add(a);
								output.println(250);

								output.flush();
								flag = true;
							}
						}
						if (flag == false) {
							output.println(550);
							output.flush();
						}

					} else if (a.contains("DATA")) {
						// System.out.println("5  " + a);
						output.println(354);
						output.flush();
					} else if (a.contains("TEXT") || a.contains("Subject")) {
						data.add(a);
						output.println(250);
						output.flush();

					}
					System.out.println(a);
					a = input.readLine();
				}
				System.out.println(a);
				output.println(221);
				output.flush();
				ClientObj s = new ClientObj(mailfrom, recepient, data);
				q.add(s);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(socket21!=null)
			socket21.close();
		}
		// serverSocket.close();
		// s.close();

	}

	public void client1() throws NumberFormatException, IOException {
		Socket socket1 = null;
		try {
			System.out.println("i am hopefully here");
			socket1 = new Socket("localhost", 9080);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					socket1.getInputStream()));

			PrintWriter output = new PrintWriter(new OutputStreamWriter(
					socket1.getOutputStream()), true);
			System.out.println("here call");
			while (true) {
				ClientObj rem = q.poll();
				if (rem != null) {
					System.out.println("here");
					ArrayList<String> recepient = rem.getReceiverName();
					ArrayList<String> data = rem.getText();
					String mailfrom = rem.getServerid();
					// TODO Auto-generated method stub
					output.println(mailfrom);
					output.flush();
					output.println(recepient);
					output.flush();
					output.println(data);
					output.flush();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(socket1!=null)
			socket1.close();
		}
	}
}

class ClientObj {
	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public ArrayList<String> getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(ArrayList<String> receiverName) {
		this.receiverName = receiverName;
	}

	public ArrayList<String> getText() {
		return text;
	}

	public void setText(ArrayList<String> text) {
		this.text = text;
	}

	String serverid;
	ArrayList<String> receiverName;
	ArrayList<String> text;

	public ClientObj(String serverid, ArrayList<String> receiverName,
			ArrayList<String> text) {
		this.serverid = serverid;
		this.receiverName = receiverName;

		this.text = text;
	}

}
