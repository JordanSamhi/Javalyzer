package javalyzer.extractors.listbuilders;
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


import javalyzer.extractors.AbstractExtractor;
import java.util.ArrayList;
import java.util.List;

public abstract class ExtractorListBuilderImpl implements ExtractorListBuilder {
    protected final ExtractorListBuilderImpl next;

    public ExtractorListBuilderImpl(ExtractorListBuilderImpl next) {
        this.next = next;
    }

    @Override
    public List<AbstractExtractor> recognizeOptions() {
        List<AbstractExtractor> extractors = new ArrayList<>();
        if (this.getCondition()) {
            extractors.add(this.getExtractorHandled());
        }
        if (this.next != null) {
            extractors.addAll(this.next.recognizeOptions());
        }
        return extractors;
    }
}
