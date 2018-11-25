package cc.ncgame.webSocket;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.hyperic.sigar.SigarException;
import org.json.JSONObject;

import cc.ncgame.util.SystemUtil;

@ServerEndpoint(value = "/websocket/admin", configurator = GetHttpSessionConfigurator.class)
public class WebSocketAdmin {

	private static final String GUEST_PREFIX = "Guest";
	private static final AtomicInteger connectionIds = new AtomicInteger(0);
	private static final Set<WebSocketAdmin> connections = new CopyOnWriteArraySet<>();

	private final String nickname;
	private Session session;
	private boolean isAdmin;
	private static ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

	public WebSocketAdmin() {
		nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
	}
	private boolean flag = true;

	@OnOpen
	public void start(Session session, EndpointConfig config) {

//		获取httpsession
//		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//
//		System.out.println(session.getId() + "=" + hs.getHttpSessionId() + " = ");
//		System.out.println(httpSession);
//		System.out.println(httpSession.getAttribute("a"));
		this.session = session;
		
		newSingleThreadExecutor.execute(new Runnable() {

			@Override
			public void run() {
				while (flag) {
					
					try {
						int cpu = SystemUtil.cpu();
						int memory = SystemUtil.memory();
						long time = new Date().getTime();

						JSONObject jsonObject = new JSONObject();

						/**
						 * { date: Date.now(), value: { cpu: (Math.random() * 100) | 0, ram:
						 * (Math.random() * 100) | 0 } }
						 */
						jsonObject.put("date", time);
						JSONObject value = new JSONObject();
						value.put("cpu", cpu);
						value.put("ram", memory);
						jsonObject.put("value", value);
						send(jsonObject.toString());

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					} catch (SigarException e) {
						e.printStackTrace();
						flag = false;
					}catch (Exception e) {
						e.printStackTrace();
						flag = false;
					}


				}
			}
		});
		connections.add(this);
	}

	@OnClose
	public void end() {
		try {
			session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		connections.remove(this);

	}

	@OnMessage
	public void incoming(String message) {
		// Never trust the client
//		broadcast(message);
	}

	private static void send(String msg) {
		for (WebSocketAdmin webSocketAdmin : connections) {
			try {
				
				webSocketAdmin.session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	private static void broadcast(String msg) {
		for (WebSocketAdmin webSocketAdmin : connections) {
			try {
				webSocketAdmin.session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(msg);
	}
}