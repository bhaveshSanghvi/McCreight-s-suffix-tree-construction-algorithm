package com.genomics;

import java.util.Map;

public class Node {
	private int nodeId;
	private Node parent;
	private Map<Character, Node> children;
	private int StringDepth;
	private Node suffixLink;
	private int edgeStart;
	private int edgeEnd;
	private int suffix_id;
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Map<Character, Node> getChildren() {
		return children;
	}
	public void setChildrens(Map<Character, Node> children) {
		this.children = children;
	}
	public int getStringDepth() {
		return StringDepth;
	}
	public void setStringDepth(int stringDepth) {
		StringDepth = stringDepth;
	}
	public Node getSuffixLink() {
		return suffixLink;
	}
	public void setSuffixLink(Node suffixLink) {
		this.suffixLink = suffixLink;
	}
	public int getedgeStart() {
		return edgeStart;
	}
	public void setedgeStart(int edgeStart) {
		this.edgeStart = edgeStart;
	}
	public int getEdgeEnd() {
		return edgeEnd;
	}
	public void setEdgeEnd(int edgeEnd) {
		this.edgeEnd = edgeEnd;
	}
	
	public int getSuffix_id() {
		return suffix_id;
	}
	public void setSuffix_id(int suffix_id) {
		this.suffix_id = suffix_id;
	}
	@Override
	public String toString() {
		return "Node [nodeId=" + nodeId + ", children=" + children + ", StringDepth=" + StringDepth + ", edgeStart="
				+ edgeStart + ", edgeEnd=" + edgeEnd + "]\n";
	}
	
	
}
