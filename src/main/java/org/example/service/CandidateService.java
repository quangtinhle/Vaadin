package org.example.service;

import com.google.gson.reflect.TypeToken;
import org.example.entities.Candidate;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class CandidateService implements Serializable {

    private static CandidateService instance;
    private ArrayList<Candidate> candidates;

    public static CandidateService getInstance() throws IOException {
        if(instance == null) {
            instance = new CandidateService();
        }
        return instance;
    }
    private CandidateService() throws IOException {
        deserializing();
    }

    private void deserializing() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/candidate.txt");
        if(fileInputStream.available() == 0 ) {
            this.candidates = new ArrayList<>();
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder json = new StringBuilder();
        String line = null;
        while((line = bufferedReader.readLine())!= null)
            json.append(line);
        this.candidates = new Gson().fromJson(json.toString(),new TypeToken<ArrayList<Candidate>>(){}.getType());
        bufferedReader.close();
        inputStreamReader.close();
        fileInputStream.close();
    }

    private void serializing() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/candidate.txt");
        BufferedOutputStream os = new BufferedOutputStream(fileOutputStream);
        String json = new Gson().toJson(this.candidates);
        os.write((json.getBytes()));
        os.close();
        fileOutputStream.close();
    }

    public void save(Candidate candidate) throws IOException {
        for (int i = 0; i < candidates.size(); i++) {
            if(candidate.getId().equals(candidates.get(i).getId())){
                candidates.set(i,candidate);
                serializing();
                return;
            }
        }
        candidates.add(candidate);
        serializing();
    }

    public void delete(Candidate candidate) throws IOException {
        for (int i = 0; i < candidates.size(); i++) {
            if(candidate.getId().equals(candidates.get(i).getId())){
                candidates.remove(candidates.get(i));
            }
        }
        serializing();
    }

    public ArrayList<Candidate> findAll() {
        return this.candidates;
    }
}
