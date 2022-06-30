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
import soot.G;
import soot.Scene;
import soot.SootMethod;
import soot.options.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JarInitializer extends AbstractSootInitializer {

    public JarInitializer(AbstractSootInitializer next) {
        super(next);
    }

    @Override
    public void initializeSoot(File f) {
        G.reset();
        Options.v().setPhaseOption("wjop", "enabled:false");
        Options.v().setPhaseOption("wjap", "enabled:false");
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_none);
        if (Environment.v().isHasCG()) {
            Options.v().set_whole_program(true);
            Options.v().setPhaseOption("cg", "enabled:true");
            Options.v().setPhaseOption(String.format("cg.%s", Environment.v().getCgAlgo()), "enabled:true");
        } else {
            Options.v().set_whole_program(false);
            Options.v().setPhaseOption("cg", "enabled:false");
        }
        List<String> dirs = new ArrayList<>();
        dirs.add(f.getAbsolutePath());
        Options.v().set_process_dir(dirs);
        Options.v().set_soot_classpath(Environment.v().getRtJarPath());
        Scene.v().loadNecessaryClasses();
        this.setEntryPoints();
    }

    @Override
    public String getType() {
        return Constants.JAR_TYPE;
    }

    @Override
    public String getFileType() {
        return Constants.JAR;
    }

    public void setEntryPoints() {
        List<SootMethod> entryPoints = Scene.v().getEntryPoints();
        List<SootMethod> eps = new ArrayList<>();
        for (SootMethod ep : entryPoints) {
            if (ep.isPublic() && ep.isStatic() && ep.getSubSignature().equals(Constants.MAIN_METHOD)) {
                eps.add(ep);
            }
        }
        entryPoints.clear();
        entryPoints.addAll(eps);
    }
}
