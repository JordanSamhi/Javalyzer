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
import org.json.JSONArray;
import org.json.JSONObject;
import soot.Scene;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.*;

public class CallGraphExtractor extends AbstractExtractor {

    private final Map<String, List<String>> adjacencyList;
    private final CallGraph cg;
    List<SootMethod> vertices;

    public CallGraphExtractor() {
        super();
        this.adjacencyList = new HashMap<>();
        this.cg = Scene.v().getCallGraph();
        this.vertices = new ArrayList<>();
    }

    @Override
    public boolean process() {
        Iterator<Edge> it = cg.iterator();
        Edge next;
        SootMethod src, tgt;
        String srcSig, tgtSig;
        List<String> tgts;
        while (it.hasNext()) {
            next = it.next();
            src = next.src();
            tgt = next.tgt();
            if (!vertices.contains(src)) {
                vertices.add(src);
            }
            if (!vertices.contains(tgt)) {
                vertices.add(tgt);
            }
            srcSig = src.getSignature();
            tgtSig = tgt.getSignature();
            if (!adjacencyList.containsKey(srcSig)) {
                adjacencyList.put(srcSig, new ArrayList<>());
            }
            tgts = adjacencyList.get(srcSig);
            tgts.add(tgtSig);
        }
        return true;
    }

    @Override
    protected String getYaml() {
        return null;
    }

    @Override
    protected String getJson() {
        JSONObject jsonCallGraph = new JSONObject();
        JSONArray ja = new JSONArray();
        JSONObject algo = new JSONObject().put("Algorithm", Environment.v().getCgAlgo());
        JSONObject NumEdges = new JSONObject().put("NumEdges", this.cg.size());
        JSONObject NumVertices = new JSONObject().put("NumVertices", this.vertices.size());
        JSONArray eps = new JSONArray();
        for (SootMethod sm : Scene.v().getEntryPoints()) {
            eps.put(sm.getSignature());
        }
        JSONObject entrypoints = new JSONObject().put("Entrypoints", eps);
        JSONArray jaAdjacencyList = new JSONArray();
        JSONArray jaTgts;
        JSONObject joEntry;
        for (Map.Entry<String, List<String>> e : this.adjacencyList.entrySet()) {
            jaTgts = new JSONArray();
            for (String s: e.getValue()) {
                jaTgts.put(s);
            }
            joEntry = new JSONObject();
            joEntry.put(e.getKey(), jaTgts);
            jaAdjacencyList.put(joEntry);
        }
        JSONObject joAdjacencyList = new JSONObject();
        joAdjacencyList.put("AdjacencyList", jaAdjacencyList);
        ja.put(algo);
        ja.put(NumVertices);
        ja.put(NumEdges);
        ja.put(entrypoints);
        ja.put(joAdjacencyList);
        jsonCallGraph.put("CallGraph", ja);
        return jsonCallGraph.toString(4);
    }

    @Override
    protected String getMessage() {
        return "Extracting Call Graph";
    }

    @Override
    public String getOutputFileName() {
        return Constants.CALLGRAPH_OUTPUT;
    }
}
