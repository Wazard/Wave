/*
 * Decompiled with CFR 0.152.
 */
package org.ninjacave.jarsplice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarSpliceLauncher {
    public JarSpliceLauncher() throws Exception {
        File file = this.getCodeSourceLocation();
        String nativeDirectory = this.getNativeDirectory();
        String mainClass = this.getMainClass(file);
        String vmArgs = this.getVmArgs(file);
        try {
            this.extractNatives(file, nativeDirectory);
            ArrayList<String> arguments = new ArrayList<String>();
            String javaPath = String.valueOf(System.getProperty("java.home")) + File.separator + "bin" + File.separator + "java";
            arguments.add(javaPath);
            StringTokenizer vmArgsToken = new StringTokenizer(vmArgs, " ");
            int count = vmArgsToken.countTokens();
            int i = 0;
            while (i < count) {
                arguments.add(vmArgsToken.nextToken());
                ++i;
            }
            arguments.add("-cp");
            arguments.add(file.getAbsoluteFile().toString());
            arguments.add("-Djava.library.path=" + nativeDirectory);
            arguments.add(mainClass);
            ProcessBuilder processBuilder = new ProcessBuilder(arguments);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            this.writeConsoleOutput(process);
            process.waitFor();
        }
        finally {
            this.deleteNativeDirectory(nativeDirectory);
        }
    }

    public void writeConsoleOutput(Process process) throws Exception {
        String line;
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public void extractNatives(File file, String nativeDirectory) throws Exception {
        JarFile jarFile = new JarFile(file, false);
        Enumeration<JarEntry> entities = jarFile.entries();
        while (entities.hasMoreElements()) {
            int bufferSize;
            JarEntry entry = entities.nextElement();
            if (entry.isDirectory() || entry.getName().indexOf(47) != -1 || !this.isNativeFile(entry.getName())) continue;
            InputStream in = jarFile.getInputStream(jarFile.getEntry(entry.getName()));
            FileOutputStream out = new FileOutputStream(String.valueOf(nativeDirectory) + File.separator + entry.getName());
            byte[] buffer = new byte[65536];
            while ((bufferSize = in.read(buffer, 0, buffer.length)) != -1) {
                ((OutputStream)out).write(buffer, 0, bufferSize);
            }
            in.close();
            ((OutputStream)out).close();
        }
        jarFile.close();
    }

    public boolean isNativeFile(String entryName) {
        String osName = System.getProperty("os.name");
        String name = entryName.toLowerCase();
        return osName.startsWith("Win") ? name.endsWith(".dll") : (osName.startsWith("Linux") ? name.endsWith(".so") : !(!osName.startsWith("Mac") && !osName.startsWith("Darwin") || !name.endsWith(".jnilib") && !name.endsWith(".dylib")));
    }

    public String getNativeDirectory() {
        File dir;
        String nativeDir = System.getProperty("deployment.user.cachedir");
        if (nativeDir == null || System.getProperty("os.name").startsWith("Win")) {
            nativeDir = System.getProperty("java.io.tmpdir");
        }
        if (!(dir = new File(nativeDir = String.valueOf(nativeDir) + File.separator + "natives" + new Random().nextInt())).exists()) {
            dir.mkdirs();
        }
        return nativeDir;
    }

    public void deleteNativeDirectory(String directoryName) {
        File[] files;
        File directory = new File(directoryName);
        File[] fileArray = files = directory.listFiles();
        int n = files.length;
        int n2 = 0;
        while (n2 < n) {
            File file = fileArray[n2];
            file.delete();
            ++n2;
        }
        directory.delete();
    }

    public String getMainClass(File file) throws Exception {
        JarFile jarFile = new JarFile(file);
        Manifest manifest = jarFile.getManifest();
        Attributes attribute = manifest.getMainAttributes();
        return attribute.getValue("Launcher-Main-Class");
    }

    public String getVmArgs(File file) throws Exception {
        JarFile jarFile = new JarFile(file);
        Manifest manifest = jarFile.getManifest();
        Attributes attribute = manifest.getMainAttributes();
        return attribute.getValue("Launcher-VM-Args");
    }

    public File getCodeSourceLocation() {
        try {
            return new File(JarSpliceLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        new JarSpliceLauncher();
    }
}

