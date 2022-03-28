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
import soot.options.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JarInitializer extends AbstractSootJavaInitializer {

    public JarInitializer(AbstractSootInitializer next) {
        super(next);
    }

    @Override
    public void specificInitialization(File f) {
        List<String> dirs = new ArrayList<>();
        dirs.add(f.getAbsolutePath());
        Options.v().set_process_dir(dirs);
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
