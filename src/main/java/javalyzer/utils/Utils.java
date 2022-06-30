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

import soot.jimple.infoflow.InfoflowConfiguration;

import java.nio.file.Path;

public class Utils {
    public static String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

    public static boolean isRtJar(Path p) {
        return p.getFileName().toString().equals("rt.jar");
    }

    public static String getRandomString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (alphaNumeric.length() * Math.random());
            sb.append(alphaNumeric.charAt(index));
        }
        return sb.toString();
    }

    public static InfoflowConfiguration.CallgraphAlgorithm getCGAlgo(String s) {
        switch (s) {
            case Constants.RTA:
                return InfoflowConfiguration.CallgraphAlgorithm.RTA;
            case Constants.VTA:
                return InfoflowConfiguration.CallgraphAlgorithm.VTA;
            case Constants.SPARK:
                return InfoflowConfiguration.CallgraphAlgorithm.SPARK;
            default:
                return InfoflowConfiguration.CallgraphAlgorithm.CHA;
        }
    }
}
