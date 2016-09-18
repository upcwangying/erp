package com.erp.util;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 */
public class ClassesManage {
    static Log log = LogFactory.getLog(ClassesManage.class);
    static Paranamer paranamer = new CachingParanamer(new BytecodeReadingParanamer());

    public static Set<Class<?>> getClassesByPackage(String packagename, Set<Class<?>> classes) {
        if (classes == null) {
            classes = new LinkedHashSet<Class<?>>();
        }

        // �Ƿ�ѭ������
        boolean recursive = true;
        // ��ȡ�������� �������滻
        String packageName = packagename;
        String packageDirName = packageName.replace('.', '/');
        try {
            // ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // ѭ��������ȥ
            while (dirs.hasMoreElements()) {
                // ��ȡ��һ��Ԫ��
                URL url = dirs.nextElement();
                // �õ�Э�������
                String protocol = url.getProtocol();
                // ��������ļ�����ʽ�����ڷ�������
                if ("file".equals(protocol)) {
                    // ��ȡ��������·��
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // �����jar���ļ�
                    // ����һ��JarFile
                    try {
                        // ��ȡjar
                        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // �Ӵ�jar�� �õ�һ��ö����
                        Enumeration<JarEntry> entries = jar.entries();
                        // ͬ���Ľ���ѭ������
                        while (entries.hasMoreElements()) {
                            // ��ȡjar���һ��ʵ�� ������Ŀ¼ ��һЩjar����������ļ� ��META-INF���ļ�
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // �������/��ͷ��
                            if (name.charAt(0) == '/') {
                                // ��ȡ������ַ���
                                name = name.substring(1);
                            }
                            // ���ǰ�벿�ֺͶ���İ�����ͬ
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // �����"/"��β ��һ����
                                if (idx != -1) {
                                    // ��ȡ���� ��"/"�滻��"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // ������Ե�����ȥ ������һ����
                                if ((idx != -1) || recursive) {
                                    // �����һ��.class�ļ� ���Ҳ���Ŀ¼
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // ȥ�������".class" ��ȡ����������
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            // ��ӵ�classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (Exception e) {
                                            System.err.println(packageName+"."+className);
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return classes;
    }


    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // ��ȡ�˰���Ŀ¼ ����һ��File
        File dir = new File(packagePath);
        // ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // ѭ�������ļ�
        for (File file : dirfiles) {
            // �����Ŀ¼ �����ɨ��
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // �����java���ļ� ȥ�������.class ֻ��������
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // ��ӵ�������ȥ
                    classes.add(Class.forName(packageName + '.' + className));
                    // ������forName��һЩ���ã��ᴥ��static������û��ʹ��classLoader��load�ɾ�
//                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage(), e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ��ȡ��������
     *
     * @param clsname
     * @param method
     * @param types
     * @return
     * @throws Exception
     */
    public static String[] getMethodParamNames(String clsname, String method, Class... types) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
        CtClass cc = pool.getCtClass(clsname);
        CtMethod cm;
        if (types != null && types.length > 0) {
            CtClass[] params = new CtClass[types.length];
            for (int i = 0; i < params.length; i++) {
                params[i] = pool.getCtClass(types[i].getName());
            }
            cm = cc.getDeclaredMethod(method, params);
        } else {
            cm = cc.getDeclaredMethod(method);
        }

        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null)
            throw new Exception(cc.getName());
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++)
            paramNames[i] = attr.variableName(i + pos);
        return paramNames;
    }

    public static String[] getMethodParamNames(Class cls, Method m) throws Exception {
        return paranamer.lookupParameterNames(m,false);
//        return getMethodParamNames(cls.getName(), m.getName(), m.getParameterTypes());
    }
}
