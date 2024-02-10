package fr.erased.clans.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class FileContentUtils {


    public static String getFileContent(File file) {
        String result = "";
        try {
            result = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setFileContent(File file, String string){
        try {
            FileUtils.writeStringToFile(file, string, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
