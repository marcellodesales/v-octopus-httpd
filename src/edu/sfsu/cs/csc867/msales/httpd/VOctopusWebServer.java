package edu.sfsu.cs.csc867.msales.httpd;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Company: V-Ocean, Inc.
 * </p>
 * 
 * @author Marcello de Sales (marcellosales@acm.org)
 * @version 0.1
 */
public class VOctopusWebServer {

	private static final int PORT = 1025;

	/**
	 * The pool of threads will be used to control the requests to the server. If has the following
	 * attributes: 
	 * <BR><li>corePoolSize is the number of threads that are allowed to be in the pool even if they
	 * are in idle;
	 * <BR><li>maximumPoolSize is the maximum number of threads allowed in the pool;
	 * <BR><LI>keepAliveTime and timeUnit are used to indicate how long new threads will wait when the
	 * the number of threads in the pool are greater than the corePoolSize;
	 * <BR><LI>workQueue is the queue that will hold the runnables before they execute;
	 * <br><li>retentionPolicy is the policy used to remove the tasks.
	 */
	private static Executor threadsPool = new ThreadPoolExecutor(5, 5,
			Long.MAX_VALUE, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(100),
			new ThreadPoolExecutor.DiscardOldestPolicy());

	/**
	 * Prints the clients thread pool.
	 */
	private static void printServerPoolStatus() {

		ThreadPoolExecutor pool = (ThreadPoolExecutor) threadsPool;
		System.out.println("Aquarium Pool [ " + pool.getCorePoolSize() + " , "
				+ pool.getMaximumPoolSize() + " ]");
		System.out
				.println("The maximun number of threads executing on the aquarium: "
						+ pool.getLargestPoolSize());
		System.out.println("# of open Connection " + pool.getActiveCount());
		System.out.println("# of open Connection " + pool.getMaximumPoolSize());

	}

	/**
	 * Hands the client connection, handling the Request and Response.
	 * @param clientSocket
	 * @throws HttpErrorException
	 */
	private static void handleClientConnection(Socket clientSocket) throws HttpErrorException {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					clientSocket.getOutputStream()));

			Request req = Request.buildRequest(clientSocket);
			req.print();

				PrintWriter writer = new PrintWriter(out, true);
				writer.println("<html><title>This is the page</title>");
				writer.println("<body>herehre</body></html>");
				
			System.out.println("Connection closed by server...");
			
			//The buffer needs to be closed.
			if (out != null) {
				out.close();
			} 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * Here you will create your server, configure all the settings and listen
	 * to the port designated within your configuration. Whenever a request
	 * comes in, you will need to process that request appropriately and respond
	 * as needed.
	 * 
	 * All the configuration should be given through the main method, either by the configuration file
	 * (by default httpd.conf is searched).
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {

		try {
			ServerSocket socketServer = new ServerSocket(PORT);
			while (true) {
				System.out.println("V-Octopus Web Serving running on port " + PORT);
				
				final Socket clientSocket = socketServer.accept();

				Runnable connectionHandler = new Runnable() {
					public void run() {
						try {
							handleClientConnection(clientSocket);
						} catch (HttpErrorException e) {
						    e.printStackTrace();
						}
					}
				};
				
				printServerPoolStatus();
				threadsPool.execute(connectionHandler);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}