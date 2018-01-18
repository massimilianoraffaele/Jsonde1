package com.jsonde.agent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Pattern;
/**
 * 
 * @author admin
 *
 */
public class ResolveAgentLibrariesClassLoader extends URLClassLoader {

    private static String jSondeLibrariesRegexp;

    /**
     * ResolveAgentLibrariesClassLoader
     */
    public ResolveAgentLibrariesClassLoader() {
        super(getUrls(), null);
    }

    /**
     * 
     * @return
     */
    private static URL[] getUrls() {

        try {

            URL agentJarLocation = ResolveAgentLibrariesClassLoader.class.getProtectionDomain().getCodeSource().getLocation();

            URI agentJarURI = agentJarLocation.toURI();

            JarFile agentJarFile = new JarFile(agentJarURI.getPath());

            Manifest agentManifest = agentJarFile.getManifest();

            Attributes agentManifestAttributes = agentManifest.getMainAttributes();

            String jSondeClassPath = agentManifestAttributes.getValue("JSonde-Class-Path");
            jSondeLibrariesRegexp = agentManifestAttributes.getValue("JSonde-Libraries-Regexp");

            List<URL> urls = new LinkedList<URL>();
            try {
            for (String jar : jSondeClassPath.split("\\s")) {
                
                    URL o = new URL(agentJarLocation, jar);

                    urls.add(o);
                } 
            }catch (MalformedURLException e) {
            	System.out.println("Something was wrong");
            }

            return urls.toArray(new URL[urls.size()]);

        } catch (IOException e) {
        	System.out.println("Something was wrong");
            return new URL[]{};
        } catch (URISyntaxException e) {
        	System.out.println("Something was wrong");
            return new URL[]{};
        }

    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        if (Pattern.matches(jSondeLibrariesRegexp, name)) {
            return super.findClass(name);
        } else {
            return findSystemClass(name);
        }
    }

}
