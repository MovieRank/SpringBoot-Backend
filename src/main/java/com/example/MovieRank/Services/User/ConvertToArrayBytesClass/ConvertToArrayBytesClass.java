package com.example.MovieRank.Services.User.ConvertToArrayBytesClass;

import com.example.MovieRank.Exceptions.ImageConvertException;
import com.example.MovieRank.Exceptions.NoFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConvertToArrayBytesClass {

    static Logger logger = LoggerFactory.getLogger(ConvertToArrayBytesClass.class);

    public static byte[] convertDefaultIcon() {

        Path path = Paths.get("src/main/resources/defaultIcon.jpg");
        if (!Files.exists(path)) {
            logger.error("There is no file at the given path");
            throw new NoFileException("Brak pliku do zapisu!");
        }

        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            logger.error("DefaultIcon convert error");
            throw new ImageConvertException("Błąd podczas konwersji to tablicy bajtów!");
        }

        return bytes;
    }
}
