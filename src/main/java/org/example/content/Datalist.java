package org.example.content;

import com.google.gson.Gson;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.example.entities.Candidate;
import org.example.service.CandidateService;

import java.io.IOException;
import java.util.ArrayList;



public class Datalist extends VerticalLayout {
    private CandidateService service = CandidateService.getInstance();
    private Grid<Candidate> grid = new Grid<>(Candidate.class);
    private CandidateForm candidateForm;

    public CandidateForm getCandidateForm() {
        return candidateForm;
    }

    public Grid<Candidate> getGrid() {
        return grid;
    }

    public Datalist() throws IOException {
        candidateForm = new CandidateForm(this);
        addComponents(grid, candidateForm);

        grid.setColumns("firstName","lastName","birthDate","email","status" );
        grid.setSizeFull();
       /* HorizontalLayout content = new HorizontalLayout(grid,candidateForm);
        candidateForm.setCandidate(null);
        content.setSizeFull();
        content.setExpandRatio(grid,1);*/
        grid.asSingleSelect().addValueChangeListener(e -> candidateForm.setCandidate(grid.asSingleSelect().getValue()));
        updateList(service.findAll());
    }

    public void updateList(ArrayList<Candidate> candidates) {
        //System.out.println(new Gson().toJson(candidates));
        grid.setItems(candidates);
    }
}
