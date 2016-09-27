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
    //concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
    private static CopyOnWriteArraySet<MessageWebSocket> webSocketSet = new CopyOnWriteArraySet<MessageWebSocket>();

    //��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
    private Session session;
    private String staffId;
    private boolean init_flag;

    /**
     * ���ӽ����ɹ����õķ���
     * @param session  ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
     */
    @OnOpen
    public void onOpen(@PathParam("staffId") String staffId, @PathParam("init_flag") String init_flag, Session session) {
        this.session = session;
        this.staffId = staffId;
        this.init_flag = Boolean.valueOf(init_flag);
        webSocketSet.add(this);     //����set��
        logger.info("websocket���ӿ�����");
    }

    /**
     * ���ӹرյ��õķ���
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //��set��ɾ��
        logger.info("websocket���ӹرգ�");
    }

    /**
     * ��������ʱ����
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        logger.error("websocket���ӷ�������!", error);
    }

    /**
     * �յ��ͻ�����Ϣ����õķ���
     * @param message �ͻ��˷��͹�������Ϣ
     * @param session ��ѡ�Ĳ���
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("���Կͻ��˵���Ϣ:" + message);

        //Ⱥ����Ϣ
        for (MessageWebSocket item: webSocketSet) {
            if (!item.init_flag)
                item.sendMessage(item.session, message);
        }
    }

    /**
     * ������������漸��������һ����
     * û����ע�⣬�Ǹ����Լ���Ҫ��ӵķ�����
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
