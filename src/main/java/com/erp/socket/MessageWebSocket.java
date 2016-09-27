package com.erp.socket;

import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by wang_ on 2016-08-11.
 */
@ServerEndpoint("/websocket/{staffId}/{init_flag}")
public class MessageWebSocket {
    private static final Logger logger = Logger.getLogger(MessageWebSocket.class);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<MessageWebSocket> webSocketSet = new CopyOnWriteArraySet<MessageWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String staffId;
    private boolean init_flag;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("staffId") String staffId, @PathParam("init_flag") String init_flag, Session session) {
        this.session = session;
        this.staffId = staffId;
        this.init_flag = Boolean.valueOf(init_flag);
        webSocketSet.add(this);     //加入set中
        logger.info("websocket连接开启！");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        logger.info("websocket连接关闭！");
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        logger.error("websocket连接发生错误!", error);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息:" + message);

        //群发消息
        for (MessageWebSocket item: webSocketSet) {
            if (!item.init_flag)
                item.sendMessage(item.session, message);
        }
    }

    /**
     * 这个方法与上面几个方法不一样。
     * 没有用注解，是根据自己需要添加的方法。
     * @param session
     * @param message
     * @throws IOException
     */
    public void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.session.getAsyncRemote().sendText(message);
    }

}
