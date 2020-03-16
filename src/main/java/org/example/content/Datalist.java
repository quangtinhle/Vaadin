package org.example.content;

import com.google.gson.Gson;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.example.MyUI;
import org.example.entities.Candidate;
import org.example.service.CandidateService;

import java.io.IOException;
import java.util.ArrayList;



public class Datalist extends VerticalLayout {
    private CandidateService service = CandidateService.getInstance();
    private Grid<Candidate> grid = new Grid<>(Candidate.class);
    private CandidateForm candidateForm;
    Button newbutton = new Button("Neue Bewerber anlegen");
    int flag = 0;

    public CandidateForm getCandidateForm() {
        return candidateForm;
    }

    public Grid<Candidate> getGrid() {
        return grid;
    }

    public Datalist() throws IOException {

        candidateForm = new CandidateForm(this);
        addComponents(newbutton,grid, candidateForm);

        grid.setColumns("firstName","lastName","birthDate","email","status" );
        grid.setSizeFull();
       /* HorizontalLayout content = new HorizontalLayout(grid,candidateForm);
        candidateForm.setCandidate(null);
        content.setSizeFull();
        content.setExpandRatio(grid,1);*/
        grid.asSingleSelect().addValueChangeListener(e -> {

            candidateForm.setCandidate(grid.asSingleSelect().getValue());
            if(flag == 1){
                newbutton.setCaption("Neue Bewerber anlegen");
                newbutton.setStyleName(ValoTheme.BUTTON_PRIMARY);
                flag = 0;
            }
        });
        updateList(service.findAll());

        newbutton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        newbutton.addClickListener(clickEvent -> {
            changeButton();
        });

    }

    public void updateList(ArrayList<Candidate> candidates) {
        //System.out.println(new Gson().toJson(candidates));
        grid.setItems(candidates);
        if (flag == 1) changeButton();
    }


    public void changeButton() {
        if (flag == 0) {
            flag = 1;
            candidateForm.setCandidate(null);
            candidateForm.setVisible(true);
            candidateForm.setIdEnable();
            newbutton.setCaption("Abbrechen");
            newbutton.setStyleName(ValoTheme.BUTTON_DANGER);
            grid.asSingleSelect().clear();
        } else if (flag == 1) {
            candidateForm.setCandidate(null);
            candidateForm.setVisible(false);
            newbutton.setCaption("Neue Bewerber anlegen");
            newbutton.setStyleName(ValoTheme.BUTTON_PRIMARY);
            flag = 0;
            grid.asSingleSelect().clear();
        }
    }
}
