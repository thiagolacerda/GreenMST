/**
 * Copyright (C) 2013 Luca Prete, Andrea Biancini, Fabio Farina - www.garr.it - Consortium GARR
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Extends the Link class from net.floodlightcontroller.routing to
 * implement Comparable and Comparator interfaces.
 * 
 * @author Luca Prete <luca.prete@garr.it>
 * @author Andrea Biancini <andrea.biancini@garr.it>
 * @author Fabio Farina <fabio.farina@garr.it>
 * 
 * @version 0.90
 * @see it.garr.greenmst.GreenMST
 * @see it.garr.greenmst.TopologyCostsLoader
 * @see it.garr.greenmst.algorithms.IMinimumSpanningTreeAlgorithm
 * @see it.garr.greenmst.algorithms.KruskalAlgorithm
 * @see it.garr.greenmst.IGreenMSTService
 * @see it.garr.greenmst.types.LinkWithCost
 * @see it.garr.greenmst.web.GreenMSTWebRoutable
 * @see it.garr.greenmst.web.MSTEdgesResource
 * @see it.garr.greenmst.web.RedundantEdgesResource
 * @see it.garr.greenmst.web.TopoCostsResource
 * @see it.garr.greenmst.web.TopoEdgesResource
 * @see it.garr.greenmst.web.serializers.LinkWithCostJSONSerializer
 * @see it.garr.greenmst.web.serializers.TopologyCostsJSONDeserializer
 * @see it.garr.greenmst.web.serializers.TopologyCostsJSONSerializer
 * 
 */

package it.garr.greenmst.types;

import it.garr.greenmst.Dijkstra;
import it.garr.greenmst.DijkstraVertex;
import it.garr.greenmst.MinSubgraphTopo;
import it.garr.greenmst.web.serializers.TopologyCostsJSONDeserializer;
import it.garr.greenmst.web.serializers.TopologyCostsJSONSerializer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=TopologyCostsJSONSerializer.class)
@JsonDeserialize(using=TopologyCostsJSONDeserializer.class)
public class TopologyCosts {
	
	protected static Logger logger = LoggerFactory.getLogger(TopologyCosts.class);
	private static HashMap<String, Integer> costs = new HashMap<String, Integer>();
	private static HashMap<String, Boolean> temp = new HashMap<String, Boolean>();
	public static final int DEFAULT_COST = 1;
	
	public static Dijkstra dijkstra = new Dijkstra();
	public MinSubgraphTopo minTopo;// = new MinSubgraphTopo();
	
	public void markNodeAsAdded(LinkWithCost l) {
		if (temp.containsKey(l.getSrc() + "," + l.getDst()))
			temp.put(l.getSrc() + "," + l.getDst(), true);
		else if (temp.containsKey(l.getDst() + "," + l.getSrc()))
			temp.put(l.getDst() + "," + l.getSrc(), true);
	}

	public boolean hasAll() {
		for (Map.Entry<String, Boolean> entry : temp.entrySet())
			if (!entry.getValue())
				return false;
		return true;
	}
	
	public TopologyCosts(MinSubgraphTopo minSubGraph) {
		minTopo = minSubGraph;
		try {
			//load a properties file from class path, inside static method
			Properties prop = new Properties(); 
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("nodecosts.properties"));
			 
			Enumeration<?> e = prop.propertyNames();
		    while (e.hasMoreElements()) {
		      String key = (String) e.nextElement();
		      Integer value = Integer.parseInt(prop.getProperty(key));
		      costs.put(key, value);
		      
		      String sourceKey = key.split(",")[0] + "";
		      String destKey = key.split(",")[1] + "";
		      if (sourceKey.charAt(0) != 'h' && destKey.charAt(0) != 'h')
		    	  temp.put(key, false);
		      //System.out.println("SourceKey " + sourceKey + " destKey " + destKey);
		      DijkstraVertex source = dijkstra.getVertex(sourceKey);
		      DijkstraVertex dest = dijkstra.getVertex(destKey);
		      if (source == null) {
		    	  source = new DijkstraVertex(sourceKey);
		    	  dijkstra.vertexes.add(source);
		      }
		      if (dest == null) {
		    	  dest = new DijkstraVertex(destKey);
		    	  dijkstra.vertexes.add(dest);
		      }
		      source.addAdjacency(dest, value);
		      dest.addAdjacency(source, value);
		    }
		    for (DijkstraVertex v : dijkstra.vertexes)
		    	System.out.println("v: " + v.name + " size: " + v.adjacencies.size());
		    minTopo.fullDijkstra = dijkstra;
		}  catch (IOException ex) {
			logger.error("Error while reading nodecosts.properties file.", ex);
		}
	}
	
	public void setCostsValues(HashMap<String, Integer> map) {
		//costs.clear();
		costs.putAll(map);
	}
	
	public HashMap<String, Integer> getCosts() {
		return costs;
	}
	
	public void setCost(long source, long destination, int cost) {
		if (costs != null) {
			if (costs.get(source + "," + destination) != null) costs.put(source + "," + destination, cost);
			if (costs.get(destination + "," + source) != null) costs.put(destination + "," + source, cost);
		}
	}
	
	public int getCost(long source, long destination) {
		if (costs != null) {
			Integer sCost = costs.get(source + "," + destination);
			if (sCost == null) sCost = costs.get(destination + "," + source);
			if (sCost != null) return sCost;
		}
		
		return DEFAULT_COST;
	}
	
	public String toString() {
		if (costs == null) return "(null)";
		
		String s = "";
		for (Entry<String, Integer> curProp: costs.entrySet()) {
			if (!s.equals("")) s += "\n";
			s += curProp.getKey() + " => " + curProp.getValue();
		}
		return s;
	}
	
	
}
