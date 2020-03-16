package org.example.content;

import com.google.gson.Gson;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.example.entities.Candidate;
import org.example.service.CandidateService;

import java.io.IOException;
import java.util.ArrayList;



public class Datalist extends VerticalLayout {
    private CandidateService service = CandidateService.getInstance();
    private Grid<Candidate> grid = new Grid<>(Candidate.class);
    private CandidateForm candidateForm;

    public Datalist() throws IOException {
        candidateForm = new CandidateForm(this);
        addComponents(grid, candidateForm);

        grid.setColumns("firstName","lastName","birthDate","email","status" );
        candidateForm.setCandidate(null);
        grid.asSingleSelect().addValueChangeListener(e -> candidateForm.setCandidate(grid.asSingleSelect().getValue()));
        updateList(service.findAll());
    }

    public void updateList(ArrayList<Candidate> candidates) {
        //System.out.println(new Gson().toJson(candidates));
        grid.setItems(candidates);
    }
}
