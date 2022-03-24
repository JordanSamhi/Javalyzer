package javalyzer.options;

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

import javalyzer.utils.CommandLineOptions;
import javalyzer.utils.Constants;
import javalyzer.utils.Environment;
import javalyzer.utils.Writer;
import org.apache.commons.io.FilenameUtils;
import java.io.File;

public class OutputFolderSetter extends EnvironmentConstructorImpl {

    public OutputFolderSetter(EnvironmentConstructorImpl next) {
        super(next);
    }

    @Override
    public void setEnvironment() {
        String outputFolder = Constants.CURRENT_FOLDER;
        if (CommandLineOptions.v().hasOutput()) {
            String output = CommandLineOptions.v().getOutput();
            if (output != null) {
                File f = new File(output);
                if (f.isFile()) {
                    outputFolder = FilenameUtils.getFullPath(output);
                } else if (f.isDirectory()) {
                    outputFolder = output;
                }
                f = new File(outputFolder);
                if (!f.exists()) {
                    Writer.v().perror("Output folder does not exist");
                    System.exit(1);
                }
                if (!f.canWrite()) {
                    Writer.v().perror("Cannot write in output folder");
                    System.exit(1);
                }
            }
        }
        Environment.v().setOutputFolder(outputFolder);
    }
}
