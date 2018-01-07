package com.jsonde.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

import org.h2.jdbcx.JdbcConnectionPool;

import com.jsonde.api.Message;
import com.jsonde.api.MessageListener;
import com.jsonde.api.function.heap.ClassHeapDataDto;
import com.jsonde.api.function.heap.DumpHeapFunctionRequest;
import com.jsonde.api.function.heap.DumpHeapFunctionResponse;
import com.jsonde.api.methodCall.RegisterClassMessage;
import com.jsonde.client.dao.ClazzDao;
import com.jsonde.client.dao.DaoException;
import com.jsonde.client.dao.DaoFactory;
import com.jsonde.client.dao.TopMethodCallDao;
import com.jsonde.client.domain.Clazz;
import com.jsonde.client.domain.Method;
import com.jsonde.client.domain.MethodCall;
import com.jsonde.client.domain.TopMethodCall;
import com.jsonde.client.network.NetworkClient;
import com.jsonde.client.network.NetworkClientException;
import com.jsonde.client.network.NetworkClientImpl;
/**
 * 
 * @author admin
 *
 */
public class Client implements MessageListener {
	
	
/**
 * public
 */
    public NetworkClient networkClient;

    /**
     * vector
     */
    public Vector<MethodCallListener> methodCallListeners = new Vector<MethodCallListener>();
   /**
    * vector
    */
    public static Vector<ClassListener> classListeners = new Vector<ClassListener>();

    /**
     * 
     * @param methodCallListener
     */
    public void addMethodCallListener(MethodCallListener methodCallListener) {
        methodCallListeners.add(methodCallListener);
    }

    public void addClassListener(ClassListener classListener) {
        classListeners.add(classListener);
    }

    public void fireMethodCallEvent(MethodCall methodCall) {

        for (MethodCallListener listener : methodCallListeners) {
            listener.onMethodCall(methodCall);
        }

    }

    public static void fireRegisterClassEvent(Clazz clazz) {

        for (ClassListener classListener : classListeners) {
            classListener.onRegisterClass(clazz);
        }

    }

    public void loadMethodCalls() {

        try {
            for (TopMethodCall topMethodCall :
                    DaoFactory.getTopMethodCallDao().getAll()) {
                fireMethodCallEvent(
                        DaoFactory.getMethodCallDao().get(topMethodCall.getMethodCallId())
                );
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void dumpHeap() {

        try {

            DumpHeapFunctionResponse dumpHeapFunctionResponse =
                    networkClient.invokeFunction(new DumpHeapFunctionRequest());

            for (ClassHeapDataDto classHeapDataDto : dumpHeapFunctionResponse.getClassHeapDataDtos()) {

                Method method = DaoFactory.getMethodDao().get(classHeapDataDto.getConstructorId());

                long classId = method.getClassId();

                ClazzDao clazzDao = DaoFactory.getClazzDao();

                Clazz clazz = clazzDao.get(classId);

                clazz.setCreateCounter(classHeapDataDto.getCreateCounter());
                clazz.setCollectCounter(classHeapDataDto.getCollectCounter());
                clazz.setTotalCurrentSize(classHeapDataDto.getTotalCurrentSize());

                clazzDao.update(clazz);

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public final boolean online;

    public final JdbcConnectionPool jdbcConnectionPool;

    public final static String DB_CONNECTION_MODIFIERS = "LOCK_MODE=0;LOG=0;UNDO_LOG=0;CACHE_SIZE=65536";

    public Client(String databaseFileName, String address, int port) {

        online = true;

        jdbcConnectionPool = JdbcConnectionPool.create(
                "jdbc:h2:" + databaseFileName + ";" + DB_CONNECTION_MODIFIERS,
                "sa", "sa");
        jdbcConnectionPool.setMaxConnections(100);
        jdbcConnectionPool.setLoginTimeout(0);

        try {

            DaoFactory.initialize(
                    jdbcConnectionPool
            );

            DaoFactory.createSchema();

        } catch (DaoException e) {
            e.printStackTrace();
        }
        networkClient = new NetworkClientImpl(address, port);
    }

    public Client(String databaseFileName) {

        online = false;

        jdbcConnectionPool = JdbcConnectionPool.create(
                "jdbc:h2:" + databaseFileName + ";" + DB_CONNECTION_MODIFIERS,
                "sa", "sa");
        jdbcConnectionPool.setMaxConnections(100);
        jdbcConnectionPool.setLoginTimeout(0);

        try {

            DaoFactory.initialize(
                    jdbcConnectionPool
            );

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public boolean isOnline() {
        return online;
    }

    public void start() {

        networkClient.addMessageListener(this);

        try {
            networkClient.start();
        } catch (NetworkClientException e) {
            e.printStackTrace();
        }

    }

    public void stop() {

        try {
            networkClient.stop();
        } catch (NetworkClientException e) {
            e.printStackTrace();
        }

        networkClient.removeMessageListener(this);

    }

    public void sendMessage(Message message) {
        networkClient.sendMessage(message);
    }

    public long getClassCount() {
        try {
            return DaoFactory.getClazzDao().count();
        } catch (DaoException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void onMessage(Message message) {
    	
    		Map<Class, MessageHandler> handlers = new HashMap<Class, MessageHandler>();
    		handlers.put(RegisterClassMessage.class, new RegisterClassMessageHandler());
    	
    	  MessageHandler h = handlers.get(message.getClass());
    	  if(h != null) ((MessageListener) h).onMessage(message);
    	
    }

    /**
     * 
     * @param methodCall
     */
    public void createTopMethodCall(MethodCall methodCall) {

        //todo move this logic to thread local profiler

        TopMethodCallBuilder builder = new TopMethodCallBuilder();
        try {
            builder.visitMethodCall(methodCall);
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            TopMethodCallDao topMethodCallDao = DaoFactory.getTopMethodCallDao();

            List<TopMethodCall> topMethodCalls =
                    topMethodCallDao.getByCondition(
                            "HASHCODE = ? AND COUNT = ?",
                            builder.getHashCode(), builder.getCount());

            if (0 == topMethodCalls.size()) {

                fireMethodCallEvent(methodCall);

                TopMethodCall topMethodCall = new TopMethodCall();

                topMethodCall.setId(topMethodCallIdGenerator.getAndIncrement());
                topMethodCall.setMethodCallId(methodCall.getId());
                topMethodCall.setHashCode(builder.getHashCode());
                topMethodCall.setCount(builder.getCount());

                topMethodCallDao.insert(topMethodCall);

            }

        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @author albertomadio
     * TopMethodCallBuilder
     */
    private static class TopMethodCallBuilder {

        private int hashCode;
        private ByteArrayOutputStream outputStream;
        private int count;

        /**
         * TopMethodCallBuilder
         */
        private TopMethodCallBuilder() {
            hashCode = 1;
            outputStream = new ByteArrayOutputStream();
        }

        /**
         * 
         * @param methodCall
         * @throws IOException
         * @throws DaoException
         */
        public void visitMethodCall(MethodCall methodCall) throws IOException, DaoException {

            count++;

            writeByte(0);

            long value = methodCall.getMethodId();

            byte[] bytes = new byte[8];

            for (int i = 0; i < bytes.length; ++i) {
                int offset = (bytes.length - i - 1) * 8;
                bytes[i] = (byte) ((value & (0xff << offset)) >>> offset);
            }

            writeByte(bytes);

            for (MethodCall callee :
                    DaoFactory.getMethodCallDao().getByCondition("CALLERID = ?", methodCall.getMethodId())) {
                visitMethodCall(callee);
            }

            // traverse children

            writeByte(1);

        }

        private void writeByte(int b) {

            outputStream.write(b);
            hashCode = 31 * hashCode + (byte) b;

        }

        /**
         * 
         * @param bs
         */
        private void writeByte(byte[] bs) {
            for (byte b : bs) {
                writeByte(b);
            }
        }

        public int getHashCode() {
            return hashCode;
        }

        public ByteArrayOutputStream getOutputStream() {
            return outputStream;
        }

        public int getCount() {
            return count;
        }
    }

    public static AtomicLong codeSourceIdGenerator = new AtomicLong();
    private AtomicLong topMethodCallIdGenerator = new AtomicLong();

}
