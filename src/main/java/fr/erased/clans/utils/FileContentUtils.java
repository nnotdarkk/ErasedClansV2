package fr.erased.clans.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileContentReader {

    private final File file;

    public FileContentReader(File file) {
        this.file = file;
    }

    public String getFileContent() {
        String result = "";
        try {
            result = FileUtils.readFileToString(this.file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void 



}
