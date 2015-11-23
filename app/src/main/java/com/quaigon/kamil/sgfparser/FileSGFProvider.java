package com.quaigon.kamil.sgfparser;


import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class FileSGFProvider implements SGFProvider {


    private File filename;

    public FileSGFProvider(File filename) {
        this.filename = filename;
    }



    @Override
    public String getLines() {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getFilename() {
        return filename;
    }

    public void setFilename(File filename) {
        this.filename = filename;
    }
}

