package com.jsonde.profiler.network;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import com.jsonde.api.Message;
import com.jsonde.util.io.IO;
import com.jsonde.util.log.Log;
/**
 * 
 * @author admin
 *
 */
public class ServerInputWorker implements Runnable, Closeable {

    private final static Log log = Log.getLog(ServerInputWorker.class);

    private NetworkServerImpl server;
    private final Socket socket;
    private InputStream inputStream;

    /**
     * 
     * @param server
     * @param socket
     */
    public ServerInputWorker(NetworkServerImpl server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            this.inputStream = socket.getInputStream();
        } catch (IOException e) {
        	System.out.println("Something was wrong");
        }
    }

    public void run() {

        final String METHOD_NAME = "run()";

        ObjectInputStream objectInputStream = null;

        try {

            objectInputStream = new ObjectInputStream(inputStream);

            server.setInputWorkerReady(true);

            log.info("[ServerInputWorker] ready");

            boolean b1 = server.isRunning();
            while (b1) {

                log.info("[ServerInputWorker] running");

                log.info("[ServerInputWorker] reading message from socket");

                Object object = objectInputStream.readObject();

                log.info("[ServerInputWorker] message recieved");

                Message message = (Message) object;

                server.processMessage(message);

                log.info("[ServerInputWorker] running");
                b1 = server.isRunning();
            }

            log.info("[ServerInputWorker] stopped");
            objectInputStream.close();
        } catch (SocketException e) {
            log.info("[ServerInputWorker] SocketException catched");
            if (server.isRunning()) {
                log.error(METHOD_NAME, e);
            } else {
                log.trace(METHOD_NAME, e);
            }
        } catch (EOFException e) {
            log.info("[ServerInputWorker] EOFException catched");
            if (server.isRunning()) {
                log.error(METHOD_NAME, e);
            } else {
                log.trace(METHOD_NAME, e);
            }
        } catch (IOException e) {
            log.error(METHOD_NAME, e);
        } catch (ClassNotFoundException e) {
            log.error(METHOD_NAME, e);
        } finally {
            IO.close(objectInputStream);
            IO.close(inputStream);
        }

    }

    public void close() throws IOException {
        inputStream.close();
    }

}
