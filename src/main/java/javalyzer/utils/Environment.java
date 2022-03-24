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

public class Environment {

    private static Environment instance;
    private String cgAlgo;
    private String fileType;
    private boolean hasCG;
    private boolean wholeProgramAnalysis;
    private String outputFolder;
    private String outputFormat;

    public Environment() {}

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

    public boolean isHasCG() {
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
}
