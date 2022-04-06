package com.cleverdeath.cryptolabthird.fileprocessor;

import java.nio.file.Path;
import java.util.Optional;

public interface FileProcessor {
    Optional<String> readStringFromFile(Path path);
    void writeStringToFile(String text, String filePath);
}
