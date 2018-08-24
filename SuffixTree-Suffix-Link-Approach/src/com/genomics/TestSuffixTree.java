/**
 * 
 */
package com.genomics;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServerConnection;

import com.sun.management.OperatingSystemMXBean;

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
		//System.out.println("start");
		//tree.generateSuffixTree(s,alphabets);
		//System.out.println("end");
		//System.out.println(s.length());
		// perform depth first search which will print the bwt index
		//tree.depthFirstSearch(tree.root);
		
		//System.out.println(tree.number_of_nodes);
		//System.out.println(s.length());
		//System.out.println(tree.longestDepth);
		//System.out.println(tree.subsStart);
		//System.out.println(tree.subsEnd);
		
		// to calculate the CPU usage
		MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();

		OperatingSystemMXBean osMBean;
		try {
			osMBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
			long nanoBefore = System.nanoTime();
			long cpuBefore = osMBean.getProcessCpuTime();

			// Call an expensive task, or sleep if you are monitoring a remote process
			
			tree.generateSuffixTree(s,alphabets);

			long cpuAfter = osMBean.getProcessCpuTime();
			long nanoAfter = System.nanoTime();

			long percent;
			if (nanoAfter > nanoBefore)
			 percent = ((cpuAfter-cpuBefore)*100L)/
			   (nanoAfter-nanoBefore);
			else percent = 0;

			System.out.println("CPU TIME: "+(cpuAfter-cpuBefore));
			System.out.println("TIME: "+(nanoAfter-nanoBefore));
			//System.out.println("Cpu usage: "+percent+"%");
			
			tree.depthFirstSearch(tree.root);
			tree.exactMatchingSubstring();
			System.out.println(tree.number_of_nodes);
			//System.out.println(tree.longestDepth);
			//System.out.println(tree.subsStart);
			//System.out.println(tree.subsEnd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
