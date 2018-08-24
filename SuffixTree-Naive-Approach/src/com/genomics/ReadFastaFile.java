package com.genomics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFastaFile {
	List<Input> inputs;

	public List<Input> getInput(String inputFileName) {
		inputs = new ArrayList<Input>();
		FileReader fr;

		try {
			fr = new FileReader(inputFileName);
			BufferedReader br = new BufferedReader(fr);

			int x;
			String s = "";
			while ((x = br.read()) != -1) {
				s = s + (char) x;
			}
			
			int counter = 0;
			String inputName;
			String inputContent;
			
			for (int i = 0; i < s.length();) {
				if (s.charAt(i) == '>') {
					inputName = "";
					inputContent = "";
					for (; s.charAt(i) != '\n'; i++) {
						inputName = inputName + s.charAt(i);
						inputName = inputName.trim();
					}
					i++;
					for (; i < s.length() && s.charAt(i) != '>'; i++) {
						inputContent = inputContent + s.charAt(i);
						inputContent = inputContent.trim();
					}

					Input input = new Input();
					input.setName(inputName);
					input.setContent(inputContent);
					inputs.add(input);
				}
			}

			br.close();
			fr.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return inputs;
	}
}
