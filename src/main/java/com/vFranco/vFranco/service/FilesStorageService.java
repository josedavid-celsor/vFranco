package com.vFranco.vFranco.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageService {

    private final Path root = Paths.get("uploads");
    private final String[] FOTOS = {"ambientador.jpg", "desengrasante.jpg", "opubwnpvjue.jpeg", "panuelo.jpg"};

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Nombre de archivo duplicado.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }


    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se ha podido leer el archivo!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public void deleteOne(String name) {
        if(ArrayUtils.indexOf(FOTOS, name) == -1){
            FileSystemUtils.deleteRecursively(root.resolve(name).toFile());
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("No se han podido cargar los archivos!");
        }
    }
}