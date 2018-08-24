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
			//String s = "";
			String inputName = br.readLine();
			String inputContent = "";
			String s="";
			
			while ((s=br.readLine())!=null) {
				inputContent = inputContent + s;				
			}
			
			Input input = new Input();
			input.setName(inputName);
			input.setContent(inputContent);
			inputs.add(input);

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
