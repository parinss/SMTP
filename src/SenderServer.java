import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SenderServer extends Thread {
	Queue<SenderObj> q = new LinkedList<SenderObj>();

	public static void main(String args[]) throws IOException {
		SenderServer ss = new SenderServer();
		ss.start();
		ss.SenderData();

	}

	public void run() {
		try {
			this.ClientServer();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SenderData() throws IOException {
		// int port;
		BufferedReader a = new BufferedReader(new InputStreamReader(System.in));
		// port = Integer.parseInt(a.readLine());
		ServerSocket sss1=null;
		try {
			 sss1 = new ServerSocket(4080);

			while (true) {
				Socket serverSocket = sss1.accept();
				System.out.println("Connected to the Server");
				BufferedReader input = new BufferedReader(
						new InputStreamReader(serverSocket.getInputStream()));
				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));

				PrintWriter output = new PrintWriter(new OutputStreamWriter(
						serverSocket.getOutputStream()), true);
				String senderid = input.readLine();
				char at = '@';
				int value = senderid.indexOf(at);
				String serverName = senderid.substring(value + 1,
						senderid.length());

				String receiverid = input.readLine();

				String subject = input.readLine();
				String text = input.readLine();
				SenderObj so = new SenderObj(serverName, senderid, receiverid,
						subject, text);

				q.add(so);
				System.out.println("ServerName" + serverName);
				System.out.println("Senders Id" + senderid);
				System.out.println("Receiver ID's" + receiverid);
				System.out.println("Subject" + subject);
				System.out.println("Text" + text);
			}
		} catch (Exception e) {

		} finally {
			sss1.close();
		}
	}

	public void ClientServer() throws NumberFormatException, IOException,
			InterruptedException {
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));

		ServerSocket s12 = new ServerSocket(5080);
		System.out.println("here2");

		try {
			SenderObj s1 = null;
			Socket serverSocket = s12.accept();
			System.out.println("please work");

			// System.out.println("Connected to the Server");
			BufferedReader input = new BufferedReader(
					new InputStreamReader(serverSocket.getInputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.println("please");
			PrintWriter output = new PrintWriter(new OutputStreamWriter(
					serverSocket.getOutputStream()), true);
			while (true) {
				s1 = (SenderObj) q.poll();
				boolean flag = false;
				boolean flag1 = false;
				boolean flag2 = false;
				boolean flag3 = false;
				if (s1 != null) {
					System.out.println("abcd");
					String serverName = s1.getServerName();
					String senderid = s1.getServerid();
					String s2[] = s1.getReceiverName().split(";");
					String subject = s1.getSubject();
					String text = s1.getText();
					System.out.println("here");
					String a = input.readLine();
					System.out.println("done");
					while (!a.equals("221")) {
						if (a.equals("220")) {
							// System.out.println(a);
							output.println("HELO " + serverName);
							output.flush();
						} else if ((a.equals("250") || a.equals("550"))
								&& flag1 == false) {
							if (flag == false) {
								// System.out.println(a);
								output.println("MAIL FROM:<" + senderid + ">");
								output.flush();
								flag = true;
							} else if (flag == true) {
								String b = a;
								for (int i = 0; i < s2.length; i++) {
									if (b.equals("250")) {
										System.out.println(a);
										output.println("RCPT TO:<" + s2[i]
												+ ">");
										output.flush();
									}
									if (b.equals("550")) {
										System.out.println(b);
										output.println("RCPT TO:<" + s2[i]
												+ ">");
										output.flush();

									}
									if (i < s2.length - 1)
										b = input.readLine();
								}
								flag1 = true;
							}
						} else if ((a.equals("250") || a.equals("550"))
								&& flag1 && flag3 == false) {
							output.println("DATA");
							output.flush();
							flag3 = true;
						} else if (a.equals("354")) {
							output.println("Subject " + subject);
							output.println("TEXT " + text);
							output.flush();
							flag2 = true;
						}

						else if (a.equals("250") && flag2 == true
								&& flag3 == true) {
							// System.out.println(a);
							output.println("QUIT");
							output.flush();
					} else if (a.equals("500")) {
							// System.out.println(a);
							output.println("QUIT");
							output.flush();
							break;
						}
						System.out.println(a);
						a = input.readLine();
					}// while ends
					s1 = null;
					System.out.println(a);
				}// if
			}// while true ends
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			s12.close();
		}
	}
}

class SenderObj {
	String serverName;
	String serverid;
	String receiverName;
	String subject;
	String text;

	public SenderObj(String serverName, String serverid, String receivername,
			String subject, String text) {
		this.serverid = serverid;
		this.serverName = serverName;
		this.receiverName = receivername;
		this.subject = subject;
		this.text = text;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
