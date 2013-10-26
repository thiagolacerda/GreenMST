package it.garr.greenmst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

public class Dijkstra
{
	public Vector<DijkstraVertex> vertexes = new Vector<DijkstraVertex>();

	public DijkstraVertex getVertex(String id)
	{
		for (DijkstraVertex v : vertexes) {
			if (v.name.equals(id))
				return v;
		}
		
		return null;
	}
	
	public void computePaths(DijkstraVertex source)
	{
		source.minDistance = 0.;
		PriorityQueue<DijkstraVertex> vertexQueue = new PriorityQueue<DijkstraVertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			DijkstraVertex u = vertexQueue.poll();

			// Visit each edge exiting u
			for (DijkstraEdge e : u.adjacencies)
			{
				DijkstraVertex v = e.target;
				double weight = e.weight;
				double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distanceThroughU ;
					v.previous = u;
					vertexQueue.add(v);
				}
			}
		}
	}

	public List<DijkstraVertex> getShortestPathTo(DijkstraVertex target)
	{
		List<DijkstraVertex> path = new ArrayList<DijkstraVertex>();
		for (DijkstraVertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}
}