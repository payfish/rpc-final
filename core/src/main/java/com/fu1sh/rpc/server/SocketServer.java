package com.fu1sh.rpc.server;

import com.fu1sh.rpc.register.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private static final RequestHandler requestHandler = new RequestHandler();
    private static final int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static final int maxPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
    private static final int queueSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
    private static final int keepAliveTime = 60;
    private final ServiceRegistry serviceRegistry;
    private final ThreadPoolExecutor threadPool;

    public SocketServer(ServiceRegistry serviceRegistry) {
        threadPool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        this.serviceRegistry = serviceRegistry;
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务启动中");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("Accepted connection from {}", socket.getRemoteSocketAddress());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
        } catch (Exception e) {
            logger.error("连接出错了");
        }

    }

}
