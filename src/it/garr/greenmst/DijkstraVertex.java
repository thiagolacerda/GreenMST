package it.garr.greenmst;

import java.util.Vector;

public class DijkstraVertex implements Comparable<DijkstraVertex>
{
	public final String name;
	public Vector<DijkstraEdge> adjacencies = new Vector<DijkstraEdge>();
	public double minDistance = Double.POSITIVE_INFINITY;
	public DijkstraVertex previous;
	public DijkstraVertex(String argName) { name = argName; }
	public String toString() { return name; }
	public int compareTo(DijkstraVertex other)
	{
		return Double.compare(minDistance, other.minDistance);
	}
	
	public void addAdjacency(DijkstraVertex target, double cost)
	{
		DijkstraEdge e = new DijkstraEdge(target, cost);
		adjacencies.add(e);
	}
	
	public DijkstraEdge getAdjacent(String id) {
		for (DijkstraEdge e : adjacencies)
			if (e.target.name.equals(id))
				return e;
		
		return null;
	}
}