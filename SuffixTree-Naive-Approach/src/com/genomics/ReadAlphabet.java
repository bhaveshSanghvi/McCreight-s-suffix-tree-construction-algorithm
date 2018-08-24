package com.genomics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadAlphabet {
	List<Character> alphabet;
	
	public List<Character> readAlphabet(String alphabetFileName) {
		alphabet = new ArrayList<Character>();
		
		FileReader fr;

		try {
			fr = new FileReader(alphabetFileName);
			BufferedReader br = new BufferedReader(fr);

			int x;
			String s = "";
			while ((x = br.read()) != -1) {
				s = s + (char) x;
			}
			br.close();

			if(!s.equals(null)) {
				for(int i=0;i<s.length();i++) {
					if(s.charAt(i)!=' ') {
						alphabet.add(s.charAt(i));
					}
				}
			} 
			
			alphabet.add('$');
		} catch(Exception e) {
			e.printStackTrace();
		}
		return alphabet;
	}
}
