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

import soot.SootClass;
import soot.jimple.infoflow.InfoflowConfiguration;

import java.nio.file.Path;

public class Utils {

    public static boolean isRtJar(Path p) {
        return p.getFileName().toString().equals("rt.jar");
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

    public static boolean isSystemClass(SootClass clazz) {
        String className = clazz.getName();
        return (className.startsWith("android.") || className.startsWith("java.") || className.startsWith("javax.")
                || className.startsWith("sun.") || className.startsWith("org.omg.")
                || className.startsWith("org.w3c.dom.") || className.startsWith("com.google.")
                || className.startsWith("com.android.") || className.startsWith("androidx."));
    }
}
