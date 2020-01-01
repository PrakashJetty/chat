package com.compassitesinc.chat.operator.constructs;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileSystemStorageService implements StorageService {

    private Path rootLocation;


    public FileSystemStorageService(Path rootLocation) {
        this.rootLocation = rootLocation;
    }


    @Override
    public Path store(MultipartFile file, String directory) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            Files.createDirectory(rootLocation.resolve(directory));
            Path finalPath = this.rootLocation.resolve(directory).resolve(filename);
            Files.copy(file.getInputStream(), finalPath,
                    StandardCopyOption.REPLACE_EXISTING);
            return Paths.get(directory).resolve(filename);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename, String directory) {
        return rootLocation.resolve(directory).resolve(filename);
    }

    public Resource loadAsResource(String fileUri) {
        try {
            Path file = Paths.get(fileUri);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + fileUri);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + fileUri, e);
        }
    }


    @Override
    public Resource loadAsResource(String filename, String directory) {
        try {
            Path file = load(filename, directory);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }


    @Override
    public Path storeFile(File file, String directory) {

        String filename = StringUtils.cleanPath(file.getName());
        try {
            if (file == null) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            Path finalPath = this.rootLocation.resolve(directory).resolve(filename);
            File dir = new File(finalPath.toUri());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Files.copy(new FileInputStream(file), finalPath,
                    StandardCopyOption.REPLACE_EXISTING);
            return this.rootLocation.resolve(directory).resolve(filename);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
}

