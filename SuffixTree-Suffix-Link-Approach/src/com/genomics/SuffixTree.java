package com.genomics;

import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class SuffixTree {
	public Node root;
	public int number_of_nodes;
	public String s;
	public int suffixId;
	public int longestDepth=0;
	public int subsStart;
	public int subsEnd;
	public Node exactMatchingSubstring;

	public SuffixTree() {
		root = new Node();
		root.setNodeId(++number_of_nodes);
		root.setedgeStart(-1);
		root.setEdgeEnd(-1);
		root.setSuffixLink(root);
		root.setStringDepth(0);
	}
	
	public Node findPath(Node u, int i) {
		Node leaf = null;
		if(u.getChildren()!=null && u.getChildren().containsKey(s.charAt(i))) {
			// match-mismatch process
			int j=i; // save for later use
		
			Node current = u.getChildren().get(s.charAt(i));
			int start = current.getedgeStart();
			int end = current.getEdgeEnd();
			
			while(start!=end+1) {
				if(s.charAt(start)==s.charAt(i)) {
					// match
					start++;
					i++;
				} else {
					// mismatch
					// create new internal node
					
					Node internalNode = new Node();
					internalNode.setedgeStart(current.getedgeStart());
					internalNode.setEdgeEnd(start-1);
					internalNode.setNodeId(++number_of_nodes);
					internalNode.setParent(u);
					
					// create new leaf node
					
					leaf = new Node();
					leaf.setedgeStart(i);
					leaf.setEdgeEnd(this.s.length()-1);
					leaf.setNodeId(++number_of_nodes);
					leaf.setParent(internalNode);
					leaf.setSuffix_id(suffixId++);
					
					// changes in existing current
					
					current.setedgeStart(start);
					current.setParent(internalNode);
					
					u.getChildren().put(s.charAt(j), internalNode);
					
					Map<Character,Node> children = null;
					if(internalNode.getChildren()!=null) {
						internalNode.getChildren().put(this.s.charAt(i), leaf);
						internalNode.getChildren().put(s.charAt(start), current);
					} else {
						children = new TreeMap<Character,Node>();
						children.put(this.s.charAt(i), leaf);
						children.put(this.s.charAt(start), current);
						internalNode.setChildrens(children);
					}
					
					internalNode.setStringDepth(internalNode.getParent().getStringDepth()+(internalNode.getEdgeEnd() - internalNode.getedgeStart() + 1));
					leaf.setStringDepth(leaf.getParent().getStringDepth()+(leaf.getEdgeEnd() - leaf.getedgeStart() + 1));
					break;
				}
			}
			
			if(start>end) {
				findPath(current, i);
			}
		} else {
			// does not contain children
			// create a new node and assign the whole suffix here
			
			leaf= new Node();
			leaf.setedgeStart(i);
			leaf.setEdgeEnd(this.s.length()-1);
			leaf.setNodeId(++number_of_nodes);
			leaf.setParent(u);
			leaf.setSuffix_id(suffixId++);
			
			Map<Character,Node> children = null;
			if(u.getChildren()!=null) {
				u.getChildren().put(this.s.charAt(i), leaf);
			} else {
				children = new TreeMap<Character,Node>();
				children.put(this.s.charAt(i), leaf);
				u.setChildrens(children);
			}
		}
		return leaf;
	}

	public void generateSuffixTree(String s, List<Character> alphabets) {
		
		// concatenate $ to input string
		this.s= s+"$";
		
		// construct a tree using suffix links
		for(int i=0;i<this.s.length();i++) {
			generateSuffixLinks(root, i);
		}
	}
	
	public void generateSuffixLinks(Node leaf, int i) {
		
		if(i<s.length()) {
			
			/**
			 * CASE 1
			 */
			if(null==leaf.getParent()) {
				// root node
				findPath(leaf, i);
			} else {
				
				Node u = leaf.getParent();
				Node v = null;
				int alpha = u.getStringDepth();
				
				/**
				 * CASE 1 : SL(u) => known 
				 */
				
				if(null!=u.getSuffixLink()) {
					
					/**
					 * CASE 1A: u = root
					 */
					
					if(u == root) {
						v = root;
						findPath(v, i);
					}
					/**
					 * CASE 1B: u != root
					 */
					else {
						v = u.getSuffixLink();
						findPath(v, i+alpha); // check what should be value of i, and suffix here
					}
				}
				
				/**
				 * CASE 2: SL(u) is not known
				 */
				
				else {
					
					Node uPrime = u.getParent();
					Node vPrime;
					
					/**
					 * CASE 2A: u' = root
					 */
					
					if(uPrime == root) {
						
						if(u.getedgeStart() == u.getEdgeEnd()) {
							u.setSuffixLink(root);
							v=root;
						} else {
						
							//vPrime = root;
							
							v = nodeHop(root,u.getedgeStart()+1,u.getEdgeEnd(),i);
							if(v!=null) {
								u.setSuffixLink(v);
							}
						}
						findPath(v, i+(u.getEdgeEnd()-u.getedgeStart()));
					} else {
						
						/**
						 * CASE 2B: u' != root , SL(u) = unknown
						 */
						
						vPrime = uPrime.getSuffixLink();
						if(vPrime!=null) {
							v = nodeHop(vPrime, u.getedgeStart(), u.getEdgeEnd(), i+vPrime.getStringDepth());
						}
						
						if(v!=null) {
							u.setSuffixLink(v);
							findPath(v, i+v.getStringDepth());
						}
					}
				}
				
			}	
		}
	}

	private Node nodeHop(Node vPrime, int betaStart, int betaEnd, int i) {
		Node v=null;
		
		int beta = betaEnd - betaStart + 1;
		int c;
		if(beta == 0) {
			v = vPrime;
		} else {
			// exhaust beta
			Node child = vPrime.getChildren().get(s.charAt(i));
			c = child.getEdgeEnd()-child.getedgeStart()+1;
			
			if (beta == c) {
				v = child;
				return v;
			} else {
				if(beta < c) {
					int n = 0; int j= child.getedgeStart();
					
					i=i+beta;
					j=j+beta;
					n=n+beta;
					
					// create new node between j-1 and j
					v = new Node();
					v.setNodeId(++number_of_nodes);
					v.setedgeStart(child.getedgeStart());
					v.setEdgeEnd(j-1);
					
					// create a new leaf node for the remaining characters of suffix
					Node leaf = new Node();
					leaf.setNodeId(++number_of_nodes);
					leaf.setedgeStart(i);
					leaf.setEdgeEnd(s.length()-1);
					leaf.setSuffix_id(suffixId++);
					
					// create map to store children of new InternalNode
					Map<Character, Node> children = null;
					
					if(v.getChildren()!=null) {
						
					} else {
						children = new TreeMap<Character, Node>();
						children.put(s.charAt(n), leaf);
						children.put(this.s.charAt(j), child);
						v.setChildrens(children);
					}
					
					// assigning new parents to all three nodes
					v.setParent(child.getParent());
					leaf.setParent(v);
					child.setParent(v);
					
					// making v as children of vprime
					vPrime.getChildren().put(s.charAt(0), v);
					
					// change start edge of current, end edge will remain unchanged
					child.setedgeStart(j);
					
					// setting depth for internal
					v.setStringDepth(v.getParent().getStringDepth()+(v.getEdgeEnd() - v.getedgeStart() + 1));
					
				} else { // beta > c
					v = nodeHop(child, betaStart + c, betaEnd, i);
				}
			}
		}
		
		return v;
	}

	public void depthFirstSearch(Node u) {
		if(u.getChildren()!=null) {
			if(u.getStringDepth()>longestDepth) {
				  longestDepth = u.getStringDepth();
				  subsStart = u.getedgeStart(); 
				  subsEnd = u.getEdgeEnd();
				  exactMatchingSubstring = u;
			  }
			for(Map.Entry<Character,Node> entry : u.getChildren().entrySet()) {
				  depthFirstSearch(entry.getValue());
 			}
		} else {
			if(u.getSuffix_id()!=0)
				System.out.println(s.charAt(u.getSuffix_id()-1));
			else
				System.out.println(s.charAt(s.length()-1));
		}
	}

	public void exactMatchingSubstring() {
		int endedge = exactMatchingSubstring.getEdgeEnd();
		while(exactMatchingSubstring.getParent().getParent()!=null) {
			exactMatchingSubstring = exactMatchingSubstring.getParent();
		}
		int start = exactMatchingSubstring.getedgeStart();
		System.out.println("Start Edge: "+start+"\nEnd Edge: "+endedge);
	}
}
