/**
 * 
 */
package com.genomics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanghvi Bhavesh
 *
 */

public class TestSuffixTree {
	public static void main(String[] args) {
		
		//To Read file locations from command line arguments 
		String fastaFileName = args[0];
		String alphabetFileName = args[1];
		
		// To read fasta file
		ReadFastaFile readFastaFile = new ReadFastaFile();
		List<Input> inputStrings =  readFastaFile.getInput(fastaFileName);
		String s = null;
	
		for (Input x: inputStrings) {
			s = x.getContent();
		}
		
		// To read alphabet
		ReadAlphabet readAlphabet = new ReadAlphabet();
		List<Character> alphabets = readAlphabet.readAlphabet(alphabetFileName);
		

		// Pass alphabet and fasta file to suffix tree
		SuffixTree tree = new SuffixTree();
		//
		//
		/*String s = "BANANA";
		List<Character> alphabets = new ArrayList<>();
		alphabets.add('A');
		alphabets.add('B');
		alphabets.add('N');
		alphabets.add('M');
		alphabets.add('I');
		alphabets.add('S');
		alphabets.add('P');
		alphabets.add('G');
		alphabets.add('C');
		alphabets.add('T');
				
		*/
		tree.generateSuffixTree(s,alphabets);
		tree.depthFirstSearch(tree.root);
		System.out.println(tree.number_of_nodes);
		System.out.println(tree.suffixId);
		//System.out.println(tree.root.getChildren().keySet());
		
	}
}
