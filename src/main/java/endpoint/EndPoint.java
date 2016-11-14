package endpoint;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.jboss.logging.Logger;

@ServerEndpoint("/myapp")
@Stateless
public class EndPoint {
	Logger log = Logger.getLogger(this.getClass());
	
	ExecutorService executor = Executors.newSingleThreadExecutor();
	Random rand = new Random();
	
	@OnMessage
	public String receiveMessage(String message, Session session) {
		log.info("Received : "+ message + ", session:" + session.getId());
		return "Response from the server";
	}
	
	@OnOpen
	public void open(final Session session) throws IOException {
		log.info("Open session:" + session.getId());
		
		executor.execute(new Runnable() {
			public void run() {				
				try {
					int candidate1 = 0;
					int candidate2 = 0;
					while(true) {
					 Thread.sleep(5000);
					 
					 candidate1 = rand.nextInt(101); 
					 candidate2 = rand.nextInt(101);
					 
					 session.getBasicRemote().sendText(candidate1 + " " + candidate2);
					} 
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}			
		});
		
//		session.getBasicRemote().sendText("Message from server");
		
	}
	
	@OnClose
	public void close(Session session, CloseReason c) {
		log.info("Closing:" + session.getId());
	}
}