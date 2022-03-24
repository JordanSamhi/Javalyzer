package javalyzer.extractors;

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
import javalyzer.utils.Loading;
import javalyzer.utils.Writer;

import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractExtractor implements Extractor {
    protected final Loading l;

    protected AbstractExtractor() {
        this.l = new Loading();
    }

    protected void close(boolean b) {
        this.l.kill(b);
        this.l.interrupt();
    }

    @Override
    public void extract() {
        this.l.start();
        this.l.load(this.getMessage());
        boolean b = this.process();
        l.kill(true);
        l.load(String.format("Writing %s/%s.%s", Environment.v().getOutputFolder(),
                this.getOutputFileName(), Environment.v().getOutputFormat()));
        this.write();
        this.close(b);
    }

    protected abstract String getMessage();

    @Override
    public void write() {
        String content;
        if(Environment.v().getOutputFormat().equals(Constants.YAML)) {
            content = this.getYaml();
        } else {
            content = this.getJson();
        }
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(String.format("%s/%s.%s", Environment.v().getOutputFolder(),
                    this.getOutputFileName(), Environment.v().getOutputFormat()));
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            Writer.v().perror(e.getMessage());
        }
    }

    protected abstract String getYaml();
    protected abstract String getJson();
}
