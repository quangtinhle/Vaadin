package org.example.content;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.example.entities.Candidate;
import org.example.entities.CandidateStatus;
import org.example.service.CandidateService;

import java.io.IOException;


public class CandidateForm extends FormLayout {

    private TextField firstName = new TextField("Vorname");
    private TextField lastName = new TextField("Nachname");
    private TextField email = new TextField("Email");
    private NativeSelect<CandidateStatus> status = new NativeSelect<>("Zustand");
    private DateField birthdate = new DateField("Geburtsdatum");
    private Button save = new Button("Speichern");
    private Button delete = new Button("Abbrechen");

    private Candidate candidate;
    private Binder<Candidate> binder = new Binder<>(Candidate.class);
    private Datalist listView;
    private CandidateService service = CandidateService.getInstance();

    public CandidateForm (Datalist datalist) throws IOException {
        //this.setRequiredField(newCandidate);
        this.listView = datalist;
        HorizontalLayout buttons = new HorizontalLayout(save,delete);
        addComponents(firstName,lastName,email,status,birthdate,buttons);
        status.setItems(CandidateStatus.values());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        delete.setStyleName(ValoTheme.BUTTON_DANGER);

        binder.bindInstanceFields(this);
        save.addClickListener(e -> {
            try {
                this.save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        delete.addClickListener(e-> {
            try {
                this.delete();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
        binder.setBean(candidate);

        //show delete button for only candidate already in the database
        delete.setVisible(candidate.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    private void delete() throws IOException {
        service.delete(candidate);
        setVisible(false);
    }

    private void save() throws IOException {
        service.save(candidate);
        setVisible(false);
    }

    /*private void setRequiredField(boolean newCandidate) {
        if(newCandidate == false)

        "Dieses Feld ist obligatorisch"
    }*/
}
