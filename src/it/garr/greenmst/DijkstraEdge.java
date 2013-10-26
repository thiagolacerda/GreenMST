package it.garr.greenmst;

public class DijkstraEdge
{
	public final DijkstraVertex target;
	public final double weight;
	public DijkstraEdge(DijkstraVertex argTarget, double argWeight)
	{ target = argTarget; weight = argWeight; }
}