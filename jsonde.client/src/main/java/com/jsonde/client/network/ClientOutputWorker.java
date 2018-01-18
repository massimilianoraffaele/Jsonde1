package com.jsonde.client.network;

import com.jsonde.api.Message;
import com.jsonde.util.io.IO;
import com.jsonde.util.log.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 
 * @author admin
 *
 */
public class ClientOutputWorker implements Runnable {

    private final static Log log = Log.getLog(ClientOutputWorker.class);

    private NetworkClientImpl client;
    private final Socket socket;

    /**
     * 
     * @param client
     * @param socket
     */
    public ClientOutputWorker(NetworkClientImpl client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    /**
     * run
     */
    public void run() {

        final String METHOD_NAME = "run()";

        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);

            client.setOutputWorkerReady(true);

            log.info("[ClientOutputWorker] ready");

            boolean b1 = client.isRunning() || client.isMessageInQueue();
            boolean b2 = client.isMessageInQueue();
            while (b1) {

                log.info("[ClientOutputWorker] running");
                while (b2) {

                    log.info("[ClientOutputWorker] message is in queue");

                    Message message = client.takeMessageFromQueue();
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();

                    log.info("[ClientOutputWorker] message was sent");
                    b2 = client.isMessageInQueue();
                }

                log.info("[ClientOutputWorker] running");
                b1 = client.isRunning() || client.isMessageInQueue();
            }

            log.info("[ClientOutputWorker] stopped");
            objectOutputStream.close();

        } catch (IOException e) {
            log.error(METHOD_NAME, e);
        } catch (InterruptedException e) {
            log.error(METHOD_NAME, e);
            Thread.currentThread().interrupt();
        } finally {
            IO.close(objectOutputStream);
            IO.close(outputStream);
        }

    }

}
