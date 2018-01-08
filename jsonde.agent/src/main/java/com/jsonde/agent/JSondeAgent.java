package com.jsonde.agent;

import com.jsonde.api.Message;
import com.jsonde.api.MessageListener;
import com.jsonde.api.configuration.AgentConfigurationMessage;
import com.jsonde.api.configuration.ClassFilterDto;
import com.jsonde.profiler.Profiler;
import com.jsonde.profiler.network.NetworkServerException;
import com.jsonde.util.ClassUtils;
import com.jsonde.util.StringUtils;
import com.jsonde.util.log.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.security.ProtectionDomain;
/**
 * 
 * @author admin
 *
 */
public class JSondeAgent implements MessageListener, ClassFileTransformer {

    private final static Log log = Log.getLog(JSondeAgent.class);

    private static final int DEFAULT_PORT_NUMBER = 60001;

    private final ClassFileTransformer byteCodeTransformer;
    private final Profiler profiler;

    private final String arguments;
    private final Instrumentation instrumentation;

    private final ClassLoader resolveAgentLibrariesClassLoader;

    /**
     * 
     * @param arg2
     * @param instrumentation2
     */
    public static void premain(final String arg2, Instrumentation instrumentation2) {
        JSondeAgent jSondeAgent = new JSondeAgent(arg2, instrumentation2);
        jSondeAgent.execute();
        jSondeAgent.setTransformer();
    }

    @SuppressWarnings("unused")
    public static void agentmain(String arg1, final Instrumentation instrumentation1) {
        final JSondeAgent jSondeAgent = new JSondeAgent(arg1, instrumentation1);
        new Thread(new Runnable() {

            public void run() {
                jSondeAgent.execute();
                jSondeAgent.redefineLoadedClasses();
                jSondeAgent.setTransformer();
               
            }

        }).start();
    }

    public JSondeAgent(String arguments, Instrumentation instrumentation) {

        System.out.println("jSonde agent started");

        this.arguments = arguments;
        this.instrumentation = instrumentation;

        ResolveAgentLibrariesClassLoader resolveAgentLibrariesClassLoader = new ResolveAgentLibrariesClassLoader();

        this.resolveAgentLibrariesClassLoader = resolveAgentLibrariesClassLoader;

        byteCodeTransformer = createByteCodeTransformer();

        int portNumber = getPortNumber();

        profiler = Profiler.initializeProfiler(instrumentation, portNumber);
    }

    /**
     * 
     * @return
     */
    private int getPortNumber() {
        try {
            return Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            return DEFAULT_PORT_NUMBER;
        }
    }

    private ClassFileTransformer createByteCodeTransformer() {
    	
    	try {
            return (ClassFileTransformer)
                    resolveAgentLibrariesClassLoader.
                            loadClass("com.jsonde.instrumentation.ByteCodeTransformer").
                            newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	return byteCodeTransformer ;
    }

    public void execute() {

        final String METHOD_NAME = "execute()";

        try {
            startServer();
        } catch (NetworkServerException e) {
            log.error(METHOD_NAME, e);
        }

    }

    public void setTransformer() {
        instrumentation.addTransformer(this);
    }

    private void redefineLoadedClasses() {
        for (Class clazz : instrumentation.getAllLoadedClasses()) {

            try {
                redefineLoadedClass(clazz);
            } catch (Exception e) {
                System.out.println("Error while transforming class " + clazz);
                e.printStackTrace();
            }

        }
    }

    private void redefineLoadedClass(Class clazz) {

        if (clazz.isArray()) {
            clazz = clazz.getComponentType();
        }

        String className = clazz.getName();

        if (shouldTransformClass(className)) {

            URL classFileResourceURL = null;

            ClassLoader classLoader = clazz.getClassLoader();
            if1(classLoader,classFileResourceURL, className);
            
       if (null == classFileResourceURL)
                return;

            InputStream byteCodeInputStream = null;
            ByteArrayOutputStream originalByteArrayOutputStream = new ByteArrayOutputStream();

            try {

                byteCodeInputStream = classFileResourceURL.openStream();

                while (byteCodeInputStream.available() > 0) {
                    originalByteArrayOutputStream.write(byteCodeInputStream.read());
                }

                byte[] bytecode = originalByteArrayOutputStream.toByteArray();

                bytecode = transform(
                        classLoader, className, clazz, clazz.getProtectionDomain(), bytecode
                );

                if (null != bytecode) {
                    Profiler.getProfiler().redefineClass(
                            bytecode,
                            className,
                            clazz.getClassLoader());
                }
            }
           
            catch (Exception e) {
                e.printStackTrace();
            } 

        }

    }

    public static void if2 () {
    	
    }
    
    public static void if1 (ClassLoader classLoader, URL classFileResourceURL, String className){
    	 if (null == classLoader) {
             classFileResourceURL = ClassLoader.getSystemResource(
                     ClassUtils.convertClassNameToResourceName(className));}
         else 
         {
             classFileResourceURL = classLoader.getResource(
                     ClassUtils.convertClassNameToResourceName(className));
         }
    }
    
    
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {

    	
        Thread currentThread = Thread.currentThread();

        for (Long profilerThreadId : Profiler.getProfiler().getProfilerThreadIds()) {
            if (currentThread.getId() == profilerThreadId) {
                return classfileBuffer;
            }
        }

        ClassLoader contextClassLoader = currentThread.getContextClassLoader();

        try {
            currentThread.setContextClassLoader(resolveAgentLibrariesClassLoader);

            
            byte[] transformedBytes = byteCodeTransformer.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
            if3(className, transformedBytes, contextClassLoader);
            if (null == loader.getParent()) {

                return classfileBuffer;

            } else {
                return transformedBytes;
            }

        } finally {
            currentThread.setContextClassLoader(contextClassLoader);
        }

    }

    public static void if3 (String name, byte[] transformedBytes, ClassLoader loader) {
    	if (!name.startsWith("com.jsonde")) {
            Profiler.getProfiler().redefineClass(
                    transformedBytes,
                    name, loader);
        }
    }
    
    private boolean shouldTransformClass(String className) {

        if ((className.startsWith("com.jsonde")) && (!className.startsWith("com.jsonde.instrumentation.samples")))
            return false;

        boolean transform = true;

        for (ClassFilterDto classFilter : agentConfigurationMessage.getClassFilters()) {
            String regex = StringUtils.wildcardToRegex(classFilter.getPackageName());
            boolean matches = ClassUtils.getFullyQualifiedName(className).matches(regex);

            if (matches) {
                transform = classFilter.isInclusive();
            }

        }
        return transform;
    }

    public synchronized void startServer() throws NetworkServerException {

        final String METHOD_NAME = "startServer()";

        profiler.addMessageListener(this);
        profiler.start();

        while (null == agentConfigurationMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error(METHOD_NAME, e);
                Thread.currentThread().interrupt();
            }
        }

        profiler.removeMessageListener(this);

    }

    private volatile AgentConfigurationMessage agentConfigurationMessage;

    public synchronized void onMessage(Message message) {

        final String METHOD_NAME = "onMessage(Message)";

        if (log.isTraceEnabled()) {
            log.trace(METHOD_NAME, "Recieved Message" + message);
        }

        if (message instanceof AgentConfigurationMessage) {
            agentConfigurationMessage = (AgentConfigurationMessage) message;
            notifyAll();
        }

    }

}
