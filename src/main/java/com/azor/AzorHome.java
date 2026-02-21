package com.azor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AzorHome {

    @Inject
    @ConfigProperty(name = "azor.home.path")
    String azorHomePath;

    public List<Path> listSessionFiles() {
        var homeDir = Path.of(azorHomePath);
        if (!Files.exists(homeDir) || !Files.isDirectory(homeDir)) {
            return Collections.emptyList();
        }
        try (var walk = Files.walk(homeDir)) {
            return walk
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith("-log.json"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
