package javalyzer.filetype;

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

import javalyzer.utils.Constants;
import javalyzer.utils.Environment;
import javalyzer.utils.Utils;
import soot.G;
import soot.Scene;
import soot.options.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JarInitializer extends SootInitializerImpl {

    public JarInitializer(SootInitializerImpl next) {
        super(next);
    }

    @Override
    public void initializeSoot(File f) {
        G.reset();
        if(Environment.v().isHasCG()) {
            Options.v().set_whole_program(true);
            Options.v().setPhaseOption("cg", "enabled:true");
            Options.v().setPhaseOption(String.format("cg.%s", Environment.v().getCgAlgo()), "enabled:true");
        } else {
            Options.v().set_whole_program(false);
            Options.v().setPhaseOption("cg", "enabled:false");
        }
        Options.v().setPhaseOption("wjop", "enabled:false");
        Options.v().setPhaseOption("wjap", "enabled:false");
        List<String> includeList = new LinkedList<>();
        includeList.add("java.lang.*");
        includeList.add("javax.management.*");
        includeList.add("java.util.*");
        includeList.add("javax.xml.*");
        includeList.add("java.io.*");
        includeList.add("sun.*");
        includeList.add("com.sun.*");
        includeList.add("java.net.*");
        includeList.add("javax.servlet.*");
        includeList.add("javax.crypto.*");
        includeList.add("org.apache.*");
        includeList.add("de.test.*");
        includeList.add("soot.*");
        includeList.add("com.example.*");
        includeList.add("libcore.icu.*");
        includeList.add("securibench.*");
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_include(includeList);
        Options.v().set_output_format(Options.output_format_none);
        Options.v().set_src_prec(Options.src_prec_java);
        Options.v().set_soot_classpath(Utils.getRtJar());
        List<String> dirs = new ArrayList<>();
        dirs.add(f.getAbsolutePath());
        Options.v().set_process_dir(dirs);
        Scene.v().loadNecessaryClasses();
    }

    @Override
    public String getType() {
        return Constants.JAR_TYPE;
    }

    @Override
    public String getFileType() {
        return Constants.JAR;
    }
}
