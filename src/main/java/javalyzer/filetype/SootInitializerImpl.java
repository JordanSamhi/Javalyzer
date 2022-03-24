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
import org.apache.tika.Tika;
import java.io.File;
import java.io.IOException;

public abstract class SootInitializerImpl implements SootInitializer {
    private final SootInitializerImpl next;

    public SootInitializerImpl(SootInitializerImpl next) {
        this.next = next;
    }

    @Override
    public boolean recognizeFileType(File f) {
        boolean recognized = false;

        try {
            String type = new Tika().detect(f);
            if (type.equals(this.getType())) {
                this.initializeSoot(f);
                recognized = true;
                Environment.v().setFileType(this.getFileType());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}