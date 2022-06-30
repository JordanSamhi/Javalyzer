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

import javalyzer.utils.Environment;
import javalyzer.utils.Writer;
import org.apache.tika.Tika;
import soot.G;
import soot.Scene;
import soot.options.Options;

import java.io.File;
import java.io.IOException;

public abstract class AbstractSootInitializer implements SootInitializer {
    private final AbstractSootInitializer next;

    public AbstractSootInitializer(AbstractSootInitializer next) {
        this.next = next;
    }

    @Override
    public boolean recognizeFileType(File f) {
        boolean recognized = false;

        try {
            String type = new Tika().detect(f);
            System.out.println(type);
            if (type.equals(this.getType())) {
                this.initializeSoot(f);
                recognized = true;
                Environment.v().setFileType(this.getFileType());
            }
        } catch (IOException e) {
            Writer.v().perror(e.getMessage());
            return false;
        }

        if (recognized) {
            return true;
        }
        if (this.next != null) {
            return this.next.recognizeFileType(f);
        } else {
            return false;
        }
    }
    @Override
    public void initializeSoot(File f) {
        G.reset();
        if (Environment.v().isHasCG()) {
            Options.v().set_whole_program(true);
            Options.v().setPhaseOption("cg", "enabled:true");
            Options.v().setPhaseOption(String.format("cg.%s", Environment.v().getCgAlgo()), "enabled:true");
        } else {
            Options.v().set_whole_program(false);
            Options.v().setPhaseOption("cg", "enabled:false");
        }
        Options.v().setPhaseOption("wjop", "enabled:false");
        Options.v().setPhaseOption("wjap", "enabled:false");
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_none);
        this.specificInitialization(f);
        Scene.v().loadNecessaryClasses();
        this.setEntryPoints();
    }

    public abstract void setEntryPoints();

    public abstract void specificInitialization(File f);
}