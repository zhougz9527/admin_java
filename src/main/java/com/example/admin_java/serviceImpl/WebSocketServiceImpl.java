package com.example.admin_java.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.example.admin_java.global.Constant;
import com.example.admin_java.service.WebSocketService;
import com.example.admin_java.websocket.WebSocketClientEndPoint;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.ByteBuffer;

/**
 * @Author: Think
 * @Date: 2018/10/11
 * @Time: 18:25
 */
@Service
public class WebSocketServiceImpl extends BaseServiceImpl implements WebSocketService {

    @Override
    public void connectWebSocket(String uri) {
        try {
            // 打开websocket
            final WebSocketClientEndPoint clientEndpoint = new WebSocketClientEndPoint(
                    new URI(uri)
            );
            //添加消息监听
            clientEndpoint.addMessageHandler(new WebSocketClientEndPoint.MessageHandler() {
                @Override
                public void handleMessage(String message) {
                    logger.info("返回的数据: {}", message);
//                    String verfyStr = "{\"cn\":{\"st\":";
//                    if (message.contains(verfyStr)) {
//                        String result = getContent(message);
//                        System.out.println(isFinalResult(message) ? "sentence:" +
//                                result : "progressive:" + result);
//                    }
                }
            });
            String pathname = System.getProperty("user.dir") + File.separator +
                    "audio"+ File.separator + "20170718105332_13916521187_10005_1050_16k_pcm";
            FileInputStream inputStream = new FileInputStream(pathname);
            // 发送音频流
            byte[] buffer = new byte[1280];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, length);
                clientEndpoint.sendBinaryMessage(byteBuffer);
                Thread.sleep(40L);
            }
            // 发送结束标识
            JSONObject json = new JSONObject();
            json.put("end", true);
            clientEndpoint.snedTextMessage(json.toJSONString());
            synchronized (clientEndpoint) {
                while (!clientEndpoint.isSessionFinish()) {
                    clientEndpoint.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送文件失败: " + e.getMessage());
        }
    }
}
