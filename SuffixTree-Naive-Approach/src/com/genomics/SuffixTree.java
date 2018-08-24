package com.genomics;

import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class SuffixTree {
	public Node root;
	public int number_of_nodes;
	public String s;
	public int suffixId;

	public SuffixTree() {
		root = new Node();
		root.setNodeId(++number_of_nodes);
		root.setedgeStart(-1);
		root.setEdgeEnd(-1);
		root.setSuffixLink(root);
		root.setStringDepth(0);
	}
	
	private String generateString(int i) {
		String newString= "";
		while(i<s.length()) {
			newString = newString + s.charAt(i);
			i++;
		}
		return newString;
	}
	
	public void findPath(Node u, int i) {
		//System.out.println(s.charAt(i));
		if(u.getChildren()!=null && u.getChildren().containsKey(s.charAt(i))) {
			// contains children
			//System.out.println("children hai"+"\n");
			
			// match-mismatch process
			int j=i; 	// this is suffix pointer
						//i is string pointer
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
					
					//System.out.println(internalNode.getNodeId());
					//System.out.println(internalNode.getedgeStart()+" "+internalNode.getEdgeEnd());
					//System.out.println();
					
					// create new leaf node
					
					Node leaf = new Node();
					leaf.setedgeStart(i);
					leaf.setEdgeEnd(this.s.length()-1);
					leaf.setNodeId(++number_of_nodes);
					leaf.setParent(internalNode);
					leaf.setSuffix_id(suffixId++);
					
					//System.out.println(leaf.getNodeId());
					//System.out.println(leaf.getedgeStart()+" "+leaf.getEdgeEnd());
					//System.out.println();
					
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
						//System.out.println(children);
						internalNode.setChildrens(children);
					}
					break;
				}
			}
			
			if(start>end) {
				findPath(current, i);
			}
			//System.out.println("children hai ends");
		} else {
			// does not contain children
			// create a new node and assign the whole suffix here
			
			//System.out.println("children nahi");
			Node leaf= new Node();
			leaf.setedgeStart(i);
			leaf.setEdgeEnd(this.s.length()-1);
			leaf.setNodeId(++number_of_nodes);
			leaf.setParent(u);
			leaf.setSuffix_id(suffixId++);
			
			//System.out.println(leaf.getNodeId());
			//System.out.println(leaf.getedgeStart()+" "+leaf.getEdgeEnd());
			//System.out.println();
			
			Map<Character,Node> children = null;
			if(u.getChildren()!=null) {
				u.getChildren().put(this.s.charAt(i), leaf);
			} else {
				children = new TreeMap<Character,Node>();
				children.put(this.s.charAt(i), leaf);
				u.setChildrens(children);
			}
		}
		
		//System.out.println(suffixId);
	}

	public void generateSuffixTree(String s, List<Character> alphabets) {
		
		// concatenate $ to input string
		this.s= s+"$";
		
		// construct a tree using suffix links
		//generateSuffixLinks(root,generateString(0), 0);
		
		// to construct the tree
		for(int i=0;i<this.s.length();i++) {
			
			// check if input symbol belongs to alphabet
			if(alphabets.contains(this.s.charAt(i))) {
				// symbol belongs to alphabet
				
				// check if tree is not empty
				if(root!=null) {
					
					// tree is not empty
					// generate tree for suffix s[i....s.length()]
					findPath(root, i);
				}
				
			} else {
				// symbol does not belong to alphabet throw exception
				//System.out.println("wrong input");
				break;
			}
		}
	}
	
	public void generateSuffixLinks(Node leaf, String suffix, int i) {
		//System.out.println("---------------------");
		//System.out.println(suffix);
		if(i<s.length()) {
		
			int betaStart;
			int betaEnd;
			int betaPrimeStart;
			int betaPrimeEnd;
			
			/**
			 * CASE 1B
			 */
			if(null==leaf.getParent()) {
				//findPath(leaf, suffix, i);
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
						//findPath(v, generateString(i), i);
					}
					/**
					 * CASE 1B: u != root
					 */
					else {
						v = u.getSuffixLink();
						//findPath(v, generateString(i+alpha), i+alpha); // check what should be value of i, and suffix here
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
						
						//betaStart = u.getedgeStart();
						//betaEnd = u.getEdgeEnd();
						//betaPrimeStart = u.getedgeStart()+1;
						//betaPrimeEnd = u.getEdgeEnd();
						
						if(u.getedgeStart() == u.getEdgeEnd()) {
							u.setSuffixLink(root);
							v=root;
						//	findPath(v, generateString(i+(u.getEdgeEnd()-u.getedgeStart())), i+(u.getEdgeEnd()-u.getedgeStart()));
						} else {
						
							//vPrime = root;
							
							v = nodeHop(root,generateString(i),u.getedgeStart()+1,u.getEdgeEnd(),i);
							//System.out.println(i);
							if(v!=null) {
								u.setSuffixLink(v);
							}
						}
						//findPath(v, generateString(i+(u.getEdgeEnd()-u.getedgeStart())), i+(u.getEdgeEnd()-u.getedgeStart()));
					} else {
						
						/**
						 * CASE 2B: u' != root , SL(u) = unknown
						 */
						
						//betaStart = u.getedgeStart();
						//betaEnd = u.getEdgeEnd();
						// beta = u.getEdgeEnd()-u.getedgeStart()+1
						
						vPrime = uPrime.getSuffixLink();
						if(vPrime!=null) {
							v = nodeHop(vPrime, generateString(i+vPrime.getStringDepth()), u.getedgeStart(), u.getEdgeEnd(), i+vPrime.getStringDepth());
						}
						
						if(v!=null) {
							u.setSuffixLink(v);
							//findPath(v, generateString(i+v.getStringDepth()),i+v.getStringDepth());
						}
					}
				}
				
			}	
		}
	}

	private Node nodeHop(Node vPrime, String suffix, int betaStart, int betaEnd, int i) {
		Node v=null;
		
		int beta = betaEnd - betaStart + 1;
		int c;
		if(beta == 0) {
			v = vPrime;
		} else {
			// exhaust beta
			Node child = vPrime.getChildren().get(suffix.charAt(0));
			c = child.getEdgeEnd()-child.getedgeStart()+1;
			
			if (beta == c) {
				v = child;
				return v;
			} else {
				if(beta < c) {
					int n = 0; int j= child.getedgeStart();
					while(n<beta) {
						i++;n++;j++;   
					}
					
					// n = startedge
					// m = 0
					// j = i
					
					// create new node between j-1 and j
					v = new Node();
					v.setNodeId(++number_of_nodes);
					v.setedgeStart(child.getedgeStart());
					v.setEdgeEnd(j-1);
				
					//System.out.println(v.getNodeId());
					//System.out.println(v.getedgeStart()+" , "+v.getEdgeEnd());
					
					// create a new leaf node for the remaining characters of suffix
					Node leaf = new Node();
					leaf.setNodeId(++number_of_nodes);
					leaf.setedgeStart(i);
					leaf.setEdgeEnd(s.length()-1);
					leaf.setSuffix_id(suffixId++);
					
					//System.out.println(leaf.getNodeId());
					//System.out.println(leaf.getedgeStart()+ ", "+leaf.getEdgeEnd());
					// create map to store children of new InternalNode
					Map<Character, Node> children = null;
					
					if(v.getChildren()!=null) {
						
					} else {
						children = new TreeMap<Character, Node>();
						children.put(suffix.charAt(n), leaf);
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
					v = nodeHop(child, generateString(i+c), betaStart + c, betaEnd, i);
				}
			}
		}
		
		return v;
	}

	public void depthFirstSearch(Node u) {
		if(u.getChildren()!=null) {
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
}
