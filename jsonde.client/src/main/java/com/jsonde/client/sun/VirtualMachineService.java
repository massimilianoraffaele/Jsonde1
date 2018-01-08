package com.jsonde.client.sun;

import com.jsonde.util.file.FileUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
/**
 * 
 * @author admin
 *
 */
public class VirtualMachineService {

    private URLClassLoader toolsClassLoader;
    private Class virtualMachineClass;

    private static VirtualMachineService instance;
    
    /**
     * 
     * @return
     * @throws VirtualMachineServiceException
     */
    public static VirtualMachineService getInstance() throws VirtualMachineServiceException {
        if (null == instance) {
            instance = new VirtualMachineService();
        }
        return instance;
    }

   /**
    * 
    * @throws VirtualMachineServiceException
    */
    private VirtualMachineService() throws VirtualMachineServiceException {

        try {

            // todo load attach native library
            URL toolsJarURL = getToolsJarURL();

            final String jdkPath = new File(toolsJarURL.toURI()).
                    getParentFile().
                    getParentFile().
                    getAbsolutePath();

            final String jdkJrePath = jdkPath +
                    FileUtils.FILE_SEPARATOR +
                    "jre";

            toolsClassLoader = new URLClassLoader(new URL[]{toolsJarURL}, ClassLoader.getSystemClassLoader()) {

                @Override
                protected String findLibrary(String libname) {
                    

                    String libraryFileName;

                    libraryFileName =
                            jdkJrePath +
                            FileUtils.FILE_SEPARATOR +
                            "bin" +
                            FileUtils.FILE_SEPARATOR +
                            System.mapLibraryName(libname);

                    if (new File(libraryFileName).exists()) return libraryFileName;

                    libraryFileName =
                            jdkJrePath +
                            FileUtils.FILE_SEPARATOR +
                            "lib" +
                            FileUtils.FILE_SEPARATOR +
                            "amd64" +
                            FileUtils.FILE_SEPARATOR +
                            System.mapLibraryName(libname);

                    if (new File(libraryFileName).exists()) return libraryFileName;

                    libraryFileName =
                            jdkJrePath +
                            FileUtils.FILE_SEPARATOR +
                            "lib" +
                            FileUtils.FILE_SEPARATOR +
                            "i386" +
                            FileUtils.FILE_SEPARATOR +
                            System.mapLibraryName(libname);

                    if (new File(libraryFileName).exists()) return libraryFileName;

                    return super.findLibrary(libname);

                }

            };

            

            virtualMachineClass = toolsClassLoader.loadClass("com.sun.tools.attach.VirtualMachine");

            for (Method method : virtualMachineClass.getMethods()) {
                
            	System.out.println(method);
            	
            }


            

        } catch (Throwable e) {
            throw new VirtualMachineServiceException(e);
        }

    }

    /**
     * 
     * @return
     * @throws VirtualMachineServiceException
     */
    public boolean isSun16JVM() throws VirtualMachineServiceException {
        return null != getToolsJarURL();
    }

    
    
    public URL getToolsJarURL() throws VirtualMachineServiceException {

        try {
            String javaHome = System.getenv("JAVA_HOME");

            File toolsJarFile;

            toolsJarFile = new File(javaHome +
                    FileUtils.FILE_SEPARATOR +
                    "lib" +
                    FileUtils.FILE_SEPARATOR +
                    "tools.jar");

            if (toolsJarFile.exists()) {
                
                return toolsJarFile.toURI().toURL();
            }

            toolsJarFile = new File(javaHome +
                    FileUtils.FILE_SEPARATOR +
                    ".." +
                    FileUtils.FILE_SEPARATOR +
                    "lib" +
                    FileUtils.FILE_SEPARATOR +
                    "tools.jar");


            if (toolsJarFile.exists()) {
                
                return toolsJarFile.toURI().toURL();
            }

            String jdkHome = System.getenv("JDK_HOME");

            toolsJarFile = new File(jdkHome +
                    FileUtils.FILE_SEPARATOR +
                    "lib" +
                    FileUtils.FILE_SEPARATOR +
                    "tools.jar");


            if (toolsJarFile.exists()) {
                
                return toolsJarFile.toURI().toURL();
            }

            javaHome = System.getProperty("java.home");

            

            if (toolsJarFile.exists()) {
                
                return toolsJarFile.toURI().toURL();
            }

           

            if (toolsJarFile.exists()) {
               
                return toolsJarFile.toURI().toURL();
            }

            return new URL(jdkHome);

        } catch (MalformedURLException e) {
            throw new VirtualMachineServiceException(e);
        }

    }

    public List<VirtualMachineData> getVirtualMachines() throws VirtualMachineServiceException {

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        List<VirtualMachineData> virtualMachinesData = new LinkedList<VirtualMachineData>();

        try {

            Thread.currentThread().setContextClassLoader(toolsClassLoader);

            Class virtualMachineDescriptorClass = toolsClassLoader.loadClass("com.sun.tools.attach.VirtualMachineDescriptor");

            Method virtualMachineDescriptorIdMethod = virtualMachineDescriptorClass.getMethod("id");

            Method virtualMachineDescriptorDisplayNameMethod = virtualMachineDescriptorClass.getMethod("displayName");

            Method listMethod = virtualMachineClass.getMethod("list");

            List virtualMachines = (List) listMethod.invoke(null);

            for (Object virtualMachine : virtualMachines) {

                String id = (String) virtualMachineDescriptorIdMethod.invoke(virtualMachine);
                String description = (String) virtualMachineDescriptorDisplayNameMethod.invoke(virtualMachine);
                virtualMachinesData.add(new VirtualMachineData(id, description));
            }

        } catch (IllegalAccessException e) {
            throw new VirtualMachineServiceException(e);
        } catch (InvocationTargetException e) {
            throw new VirtualMachineServiceException(e);
        } catch (NoSuchMethodException e) {
            throw new VirtualMachineServiceException(e);
        } catch (ClassNotFoundException e) {
            throw new VirtualMachineServiceException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }

        return virtualMachinesData;

    }

    public void attachAgent(String virtualMachineId, String agentJar, String agentParameters) throws VirtualMachineServiceException {

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        try {

            Thread.currentThread().setContextClassLoader(toolsClassLoader);

            Method attachMethod = virtualMachineClass.getMethod("attach", String.class);
            Object virtualMachine = attachMethod.invoke(null, virtualMachineId);

            Method loadAgentMethod = virtualMachineClass.getMethod("loadAgent", String.class, String.class);
            loadAgentMethod.invoke(virtualMachine, agentJar, agentParameters);

        } catch (NoSuchMethodException e) {
            throw new VirtualMachineServiceException(e);
        } catch (InvocationTargetException e) {
            throw new VirtualMachineServiceException(e);
        } catch (IllegalAccessException e) {
            throw new VirtualMachineServiceException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }

    }

    public static void main(String[] args) throws Exception {

        VirtualMachineService virtualMachineService = new VirtualMachineService();

        for (VirtualMachineData vmData : virtualMachineService.getVirtualMachines()) {
        	
        	System.out.println(vmData);
        }

    }

}