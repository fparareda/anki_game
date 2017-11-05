package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private String fileName;

    public Session(String fileName){
        System.out.println("Welcome to the Anki game!");
        this.fileName = fileName;
    }

    public List<String> getFileContent() {
        return readFileByFilename(fileName);
    }


    private List<String> readFileByFilename(String fileName) {
        File file = new File(fileName);
        return readFile(file);
    }

    private List<String> readFile(File file) {
        List<String> listOfSentences = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                listOfSentences.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfSentences;
    }

    public void saveFile(List<String> deck) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            for(String sentence : deck){
                writer.println(sentence);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveCardsIntoFile(List<String> allBoxesWithCardsToSave) {
        saveFile(allBoxesWithCardsToSave);
    }
}
