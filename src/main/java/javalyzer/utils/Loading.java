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

import java.util.ArrayList;
import java.util.List;

public class Loading extends Thread {
    private String message;
    private final List<String> signs;
    private int current;
    private boolean isRunning;
    private boolean isInterrupted;

    public Loading() {
        this.signs = new ArrayList<>();
        this.signs.add("|");
        this.signs.add("/");
        this.signs.add("-");
        this.signs.add("\\");
        this.current = 0;
        this.message = "";
        this.isRunning = false;
        this.isInterrupted = false;
    }

    private String getNext() {
        if (this.current == this.signs.size() - 1) {
            this.current = 0;
        } else {
            this.current++;
        }
        return this.signs.get(this.current);
    }

    public void load(String message) {
        this.isRunning = true;
        this.message = message;
    }

    @Override
    public void run() {
        String format = "\r%-40s [%s]";
        while (!this.isInterrupted) {
            if (this.isRunning) {
                System.out.printf(format, this.message, this.getNext());
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void kill(boolean success) {
        char c = success ? '✓' : 'x';
        System.out.printf("\r%-40s [%c]\n", this.message, c);
        this.isRunning = false;
    }

    @Override
    public void interrupt() {
        this.isInterrupted = true;
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
