package com.javarush.task.task33.task3310.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static java.nio.file.StandardOpenOption.APPEND;

public class FileBucket {
    private Path path;

    public FileBucket(){
        try {
            path = Files.createTempFile(null, null);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        path.toFile().deleteOnExit();
    }

    public long getFileSize() throws IOException {
        Long size = Files.size(path);
        return size;
    }

    public void putEntry(Entry entry) throws IOException {
            OutputStream ous = Files.newOutputStream(path);
            ObjectOutputStream oous = new ObjectOutputStream(ous);
            oous.writeObject(entry);
            oous.flush();
            oous.close();
    }

    public Entry getEntry() throws IOException, ClassNotFoundException {
        if (getFileSize() > 0) {
            InputStream is = Files.newInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(is);
            Entry entry = (Entry) ois.readObject();
            ois.close();
            return entry;
        }
        return null;
    }

    public void remove() throws IOException {
        Files.delete(this.path);
    }

}
