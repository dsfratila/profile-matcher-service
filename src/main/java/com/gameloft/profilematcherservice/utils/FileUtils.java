package com.gameloft.profilematcherservice.utils;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    @SneakyThrows
    public static String readAsString(final String filePath) {

        return Files.readString(Paths.get(new ClassPathResource(filePath).getURI()));
    }

}
