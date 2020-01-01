package com.compassitesinc.chat.operator.constructs;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public interface StorageService {

    void init();

    Path store(MultipartFile file, String directory);

    Stream<Path> loadAll();

    Path load(String filename, String directory);

    Resource loadAsResource(String filename, String directory);

    void deleteAll();

    public Path storeFile(File file, String directory);

}
