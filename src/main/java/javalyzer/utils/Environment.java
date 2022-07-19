package javalyzer.utils;

/*-
 * #%L
 * Javalyzer
 *
 * %%
 * Copyright (C) 2022 Jordan Samhi
 *
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Environment {

    private static Environment instance;
    private String cgAlgo;
    private String fileType;
    private boolean hasCG;
    private boolean wholeProgramAnalysis;
    private String outputFolder;
    private String outputFormat;
    private String rtJarPath;
    private boolean hasCFG;
    private final Map<String, String> methodToExtractCFG;

    public Map<String, String> getMethodToExtractCFG() {
        return methodToExtractCFG;
    }

    public boolean hasCFG() {
        return hasCFG;
    }

    public void setHasCFG(boolean hasCFG) {
        this.hasCFG = hasCFG;
    }

    public String getPlatformPath() {
        return platformPath;
    }

    public void setPlatformPath(String platformPath) {
        this.platformPath = platformPath;
    }

    private String platformPath;

    public Environment() {
        this.getRtJar();
        this.methodToExtractCFG = new HashMap<>();
    }

    public static Environment v() {
        if (instance == null) {
            instance = new Environment();
        }
        return instance;
    }

    public void setCgAlgo(String cgAlgo) {
        this.cgAlgo = cgAlgo;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCgAlgo() {
        return cgAlgo;
    }

    public String getFileType() {
        return fileType;
    }

    public boolean hasCG() {
        return hasCG;
    }

    public void setHasCG(boolean hasCG) {
        this.hasCG = hasCG;
    }

    public boolean isWholeProgramAnalysis() {
        return wholeProgramAnalysis;
    }

    public void setWholeProgramAnalysis(boolean wholeProgramAnalysis) {
        this.wholeProgramAnalysis = wholeProgramAnalysis;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getRtJarPath() {
        return rtJarPath;
    }

    private void getRtJar() {
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("java.home")))) {
            List<String> result = walk.filter(Utils::isRtJar).map(Path::toString).collect(Collectors.toList());
            if (result.size() > 0) {
                this.rtJarPath = result.get(0);
                return;
            }
        } catch (IOException e) {
            Writer.v().perror(e.getMessage());
        }
        this.rtJarPath = null;
    }

    public boolean hasPlatform() {
        return this.platformPath != null;
    }

    public void addMethodToExtractCFG(String clazz, String method) {
        this.methodToExtractCFG.put(clazz, method);
    }
}
