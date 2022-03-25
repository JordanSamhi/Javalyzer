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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

    public static boolean isRtJar(Path p) {
        return p.getFileName().toString().equals("rt.jar");
    }

    public static String getRtJar() {
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("java.home")))) {
            List<String> result = walk.filter(Utils::isRtJar).map(Path::toString).collect(Collectors.toList());
            if (result.size() > 0) {
                return result.get(0);
            }
        } catch (IOException e) {
            Writer.v().perror(e.getMessage());
        }
        return null;
    }

    public static String getRandomString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (alphaNumeric.length() * Math.random());
            sb.append(alphaNumeric.charAt(index));
        }
        return sb.toString();
    }
}
