package it.garr.greenmst;

import it.garr.greenmst.types.LinkWithCost;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class MinSubgraphTopo {

	public Dijkstra fullDijkstra;
	
	Vector<DijkstraVertex> hosts = new Vector<DijkstraVertex>();
	Set<DijkstraVertex> minSubGraph;
	
	public Set<DijkstraVertex> getMinSubGraphFromTopoEdges(List<LinkWithCost> topoEdges) {
		System.out.println("Will compute minSubgraph from topo edges. Full: " + fullDijkstra);
		Dijkstra d = new Dijkstra();
			
		if (hosts.isEmpty()) {
			for (DijkstraVertex v : fullDijkstra.vertexes) {
				if (v.name.charAt(0) == 'h')
					hosts.add(v);
			}
		}

		for (LinkWithCost l : topoEdges) {
			DijkstraVertex sourceInFull = fullDijkstra.getVertex(l.getSrc() + "");
			DijkstraVertex destInFull = fullDijkstra.getVertex(l.getDst() + "");
			
			DijkstraVertex newSource = d.getVertex(sourceInFull.name);
			DijkstraVertex newDest = d.getVertex(destInFull.name);
			if (newSource == null) {
				newSource = new DijkstraVertex(sourceInFull.name);
				d.vertexes.add(newSource);
			}
			if (newDest == null) {
				newDest = new DijkstraVertex(destInFull.name);
				d.vertexes.add(newDest);
			}
			DijkstraEdge adjacentInFull = sourceInFull.getAdjacent(destInFull.name);
			DijkstraEdge newAdjacent = newSource.getAdjacent(destInFull.name);
			if (newAdjacent == null)
				newSource.addAdjacency(newDest, adjacentInFull.weight);

			newAdjacent = newDest.getAdjacent(sourceInFull.name);
			if (newAdjacent == null)
				newDest.addAdjacency(newSource, adjacentInFull.weight);

			System.out.println("Will start iterating in hosts " + hosts.size());
			for (DijkstraVertex h : hosts) {
				DijkstraEdge hSourceEdge = h.getAdjacent(newSource.name);
				DijkstraEdge hDestEdge = h.getAdjacent(newDest.name);
				if (hSourceEdge != null) {
					DijkstraVertex newHost = d.getVertex(h.name);
					if (newHost == null) {
						newHost = new DijkstraVertex(h.name);
						d.vertexes.add(newHost);
					}
					newHost.addAdjacency(newSource, hSourceEdge.weight);
					newSource.addAdjacency(newHost, hSourceEdge.weight);
				} else if (hDestEdge != null) {
					DijkstraVertex newHost = d.getVertex(h.name);
					if (newHost == null) {
						newHost = new DijkstraVertex(h.name); 
						d.vertexes.add(newHost);
					}
					newHost.addAdjacency(newDest, hDestEdge.weight);
					newDest.addAdjacency(newHost, hDestEdge.weight);
				}
			}
		}
		System.out.println("In dijkstra");
		for (DijkstraVertex v : d.vertexes) {
			System.out.println("In set: " + v.name);
		}
		return computeMinSubgraph(d);
	}
	
	public Set<DijkstraVertex> computeMinSubgraph(Dijkstra dijkstra)
	{
		DijkstraVertex refHost = null;
		Vector<DijkstraVertex> hosts = new Vector<DijkstraVertex>();
		for (DijkstraVertex v : dijkstra.vertexes) {
			if (v.name.charAt(0) == 'h') {
				if (refHost == null)
					refHost = v;
				else
					hosts.add(v);
			}
		}
		if (refHost == null)
			return new HashSet<DijkstraVertex>();

		System.out.println("Hosts size: " + hosts.size() + " refHost: " + refHost.name);
		dijkstra.computePaths(refHost);
		
		Set<DijkstraVertex> set = new HashSet<DijkstraVertex>();
		
		for (DijkstraVertex host : hosts) {
			List<DijkstraVertex> path = dijkstra.getShortestPathTo(host);
			System.out.println("shortest from " + host.name + " to " + refHost.name + " " + path.size());
			System.out.println("PATH " + path);
			set.addAll(path);
		}
		if (set.isEmpty()) {
			set.add(refHost);
			DijkstraEdge adj = refHost.adjacencies.firstElement();
			if (adj != null)
				set.add(adj.target);
		}
		minSubGraph = set;
		for (DijkstraVertex v : set) {
			System.out.println("In set: " + v.name);
		}
		return set;
	}
}
