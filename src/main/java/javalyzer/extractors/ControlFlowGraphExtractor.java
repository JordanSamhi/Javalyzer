package javalyzer.extractors;

import javalyzer.files.LibrariesManager;
import javalyzer.utils.Constants;
import javalyzer.utils.Environment;
import javalyzer.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import soot.*;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.*;

public class ControlFlowGraphExtractor extends AbstractExtractor {

    private final Map<SootMethod, Map<String, List<String>>> methodToadjacencyList;
    private final Map<SootMethod, Integer> methodTonumEdges;

    public ControlFlowGraphExtractor() {
        super();
        this.methodToadjacencyList = new HashMap<>();
        this.methodTonumEdges = new HashMap<>();
    }

    @Override
    protected String getMessage() {
        String msg = "Extracting Control Flow Graph";
        if (Environment.v().getMethodToExtractCFG().size() > 1) {
            msg = msg.concat("s");
        }
        return msg;
    }

    @Override
    public boolean process() {
        List<SootMethod> methods = new ArrayList<>();
        Map<String, String> methodsToExtract = Environment.v().getMethodToExtractCFG();
        if (methodsToExtract.size() == 1 && methodsToExtract.get(Constants.ALL) != null && methodsToExtract.get(Constants.ALL).equals(Constants.ALL)) {
            for (SootClass sc : Scene.v().getApplicationClasses()) {
                if (!Utils.isSystemClass(sc) && !LibrariesManager.v().isLibrary(sc)) {
                    for (SootMethod sm : sc.getMethods()) {
                        if (sm.isConcrete()) {
                            methods.add(sm);
                        }
                    }
                }
            }
        } else {
            for (Map.Entry<String, String> entry : Environment.v().getMethodToExtractCFG().entrySet()) {
                String clazz = entry.getKey();
                String method = entry.getValue();
                for (SootClass sc : Scene.v().getClasses()) {
                    if (sc.getName().equals(clazz)) {
                        for (SootMethod sm : sc.getMethods()) {
                            if (sm.getName().equals(method)) {
                                if (sm.isConcrete()) {
                                    methods.add(sm);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (SootMethod sm : methods) {
            Body b = sm.retrieveActiveBody();
            ExceptionalUnitGraph eug = new ExceptionalUnitGraph(b);
            Iterator<Unit> it = eug.iterator();
            Unit next;
            Map<String, List<String>> adjacencyList;
            if (!this.methodToadjacencyList.containsKey(sm)) {
                this.methodToadjacencyList.put(sm, new HashMap<>());
            }
            if (!this.methodTonumEdges.containsKey(sm)) {
                this.methodTonumEdges.put(sm, 0);
            }
            adjacencyList = this.methodToadjacencyList.get(sm);
            List<String> tgts;
            while (it.hasNext()) {
                tgts = new ArrayList<>();
                next = it.next();
                for (Unit u : eug.getSuccsOf(next)) {
                    Integer i = this.methodTonumEdges.get(sm);
                    this.methodTonumEdges.put(sm, i + 1);
                    tgts.add(u.toString());
                }
                adjacencyList.put(next.toString(), tgts);
            }
        }
        return true;
    }

    @Override
    public String getOutputFileName() {
        return Constants.CFG_OUTPUT;
    }

    @Override
    public String getYaml() {
        return null;
    }

    @Override
    public String getJson() {
        JSONObject jsonCFG = new JSONObject();
        JSONArray jaCFG;
        JSONArray jaAdjacencyList;
        JSONArray cfgs = new JSONArray();
        JSONObject joCFG;
        for (Map.Entry<SootMethod, Map<String, List<String>>> entry : this.methodToadjacencyList.entrySet()) {
            joCFG = new JSONObject();
            jaCFG = new JSONArray();
            SootMethod sm = entry.getKey();
            Map<String, List<String>> adjacencyList = entry.getValue();
            JSONObject NumEdges = new JSONObject().put("NumEdges", this.methodTonumEdges.get(sm));
            JSONObject NumVertices = new JSONObject().put("NumVertices", adjacencyList.size());

            JSONArray jaTgts;
            JSONObject joEntry;
            jaAdjacencyList = new JSONArray();
            for(Map.Entry<String, List<String>> e: adjacencyList.entrySet()) {
                jaTgts = new JSONArray();
                for (String s : e.getValue()) {
                    jaTgts.put(s);
                }
                joEntry = new JSONObject();
                joEntry.put(e.getKey(), jaTgts);
                jaAdjacencyList.put(joEntry);
            }
            JSONObject joAdjacencyList = new JSONObject();
            joAdjacencyList.put("AdjacencyList", jaAdjacencyList);
            jaCFG.put(NumVertices);
            jaCFG.put(NumEdges);
            jaCFG.put(joAdjacencyList);
            joCFG.put(sm.getSignature(), jaCFG);
            cfgs.put(joCFG);
        }

        jsonCFG.put("ControlFlowGraphs", cfgs);
        return jsonCFG.toString(4);
    }
}
