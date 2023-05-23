package mz.mzlib.algorithm;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Graph<NodeKey,NodeValue,EdgeData>
{
	public class Node
	{
		public NodeValue value;
		public List<OutEdge> outEdges;
		public Node(NodeValue value)
		{
			this.value=value;
			outEdges=new ArrayList<>();
		}
	}
	public class OutEdge
	{
		public NodeKey target;
		public EdgeData data;
		public OutEdge(NodeKey target,EdgeData data)
		{
			this.target=target;
			this.data=data;
		}
	}
	public Map<NodeKey,Node> nodes=new HashMap<>();
	
	/**
	 * add or replace a node to this graph
	 * @param key the key of node
	 * @param value the value of node
	 * @return last value before replacing, or null if adding
	 */
	public NodeValue putNode(NodeKey key,NodeValue value)
	{
		if(nodes.containsKey(key))
		{
			NodeValue last=nodes.get(key).value;
			nodes.get(key).value=value;
			return last;
		}
		else
		{
			nodes.put(key,new Node(value));
			return null;
		}
	}
	public void addEdge(NodeKey from,NodeKey to,EdgeData data)
	{
		nodes.get(from).outEdges.add(new OutEdge(to,data));
	}
	public boolean containsNode(NodeKey key)
	{
		return nodes.containsKey(key);
	}
	public Node getNode(NodeKey key)
	{
		return nodes.get(key);
	}
	public NodeValue getNodeValue(NodeKey key)
	{
		return getNode(key).value;
	}
	
	/**
	 * DFS every node
	 * @param beginning the first node to dfs
	 * @param proc proc for each node, the first param is the key of current searching node, the second param is current searching depth
	 */
	public void depthFirstSearch(NodeKey beginning,BiConsumer<NodeKey,Integer> proc)
	{
		depthFirstSearch(beginning,proc,0);
	}
	public void depthFirstSearch(NodeKey beginning,BiConsumer<NodeKey,Integer> proc,int depthMin)
	{
		proc.accept(beginning,depthMin);
		for(OutEdge i:getNode(beginning).outEdges)
			depthFirstSearch(i.target,proc,depthMin+1);
	}
	
	public void topologySearch(NodeKey beginning,Consumer<NodeKey> proc)
	{
		Map<NodeKey,Integer> degreeIn=new HashMap<>();
		for(Map.Entry<NodeKey,Node> i:nodes.entrySet())
			degreeIn.put(i.getKey(),0);
		for(Map.Entry<NodeKey,Node> i:nodes.entrySet())
		{
			for(OutEdge j:i.getValue().outEdges)
				degreeIn.compute(j.target,(k,v)->Objects.requireNonNull(v)+1);
		}
		Queue<NodeKey> q=new ArrayDeque<>();
		for(Map.Entry<NodeKey,Integer> i:degreeIn.entrySet())
			if(i.getValue()==0)
				q.add(i.getKey());
		while(!q.isEmpty())
		{
			NodeKey now=q.poll();
			proc.accept(now);
			for(OutEdge i:getNode(now).outEdges)
				if(degreeIn.compute(i.target,(k,v)->Objects.requireNonNull(v)-1)==0)
					q.add(i.target);
		}
	}
}
