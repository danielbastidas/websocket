package endpoint;

import java.io.IOException;

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
	
	@OnMessage
	public String receiveMessage(String message, Session session) {
		log.info("Received : "+ message + ", session:" + session.getId());
		return "Response from the server";
	}
	
	@OnOpen
	public void open(Session session) throws IOException {
		log.info("Open session:" + session.getId());
		session.getBasicRemote().sendText("Message from server");
		
	}
	
	@OnClose
	public void close(Session session, CloseReason c) {
		log.info("Closing:" + session.getId());
	}
}