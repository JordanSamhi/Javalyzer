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

public class Constants {

    /**
     * Misc names
     */
    public static final String TOOL_NAME = "Javalyzer";
    public static final String VERSION = "0.1";
    public static final String CHA = "cha";
    public static final String RTA = "rta";
    public static final String VTA = "vta";
    public static final String SPARK = "spark";
    public static final String JSON = "json";
    public static final String YAML = "yaml";
    public static final String CURRENT_FOLDER = "./";
    public static final String ALL = "ALL";

    /**
     * File types
     */
    public static final String JAR_TYPE = "application/java-archive";
    public static final String APK_TYPE = "application/vnd.android.package-archive";
    public static final String JAR = "jar";
    public static final String APK = "apk";

    /**
     * File names
     */
    public static final String CALLGRAPH_OUTPUT = "callgraph";

    /**
     * Method signatures
     */
    public static final String MAIN_METHOD = "void main(java.lang.String[])";

    /**
     * Files
     */
    public static final String LIBRARIES_FILE = "/libraries.txt";
}