package com.jsonde.instrumentation;

import com.jsonde.util.ClassUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
/**
 * 
 * @author admin
 *
 */
public class ByteCodeTransformer implements ClassFileTransformer {

	/**
	 * transform
	 */
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException, Error {

        className = ClassUtils.getFullyQualifiedName(className);

        if (Object.class.getName().equals(className))
            throw new Error();

        if (
                className.startsWith("com.jsonde") &&
                        !className.contains(".samples.")) {
            return classfileBuffer;
        }

        try {
            return transform(classfileBuffer, true, loader, classBeingRedefined);
        } catch (ByteCodeTransformException e) {
            e.printStackTrace();
            throw new IllegalClassFormatException(e.getMessage());
        }
    }

    public byte[] transform(byte[] originalBytes, boolean instrumentClass, ClassLoader loader, Class<?> classBeingRedefined) throws ByteCodeTransformException {

        return transform(
                originalBytes,
                0,
                originalBytes.length,
                instrumentClass,
                loader,
                classBeingRedefined);

    }

    public byte[] transform(byte[] originalBytes, int offset, int length, boolean instrumentClass, ClassLoader classLoader, Class<?> classBeingRedefined) throws ByteCodeTransformException {

        ClassReader classReader = new ClassReader(originalBytes, offset, length);

		ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

		ClassVisitor classVisitor;
		classVisitor = new JSondeClassTransformer(classWriter, instrumentClass, classLoader, classBeingRedefined);

		classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);


		final byte[] bytes = classWriter.toByteArray();
		
		return bytes;

    }

}
