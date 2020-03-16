package org.example.content;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.example.entities.Candidate;
import org.example.entities.CandidateStatus;
import org.example.service.CandidateService;

import java.io.IOException;
import java.util.spi.CalendarDataProvider;


public class CandidateForm extends FormLayout {
    private TextField id = new TextField("id");
    private TextField firstName = new TextField("Vorname");
    private TextField lastName = new TextField("Nachname");
    private TextField email = new TextField("Email");
    private NativeSelect<CandidateStatus> status = new NativeSelect<>("Zustand");
    private DateField birthDate = new DateField("Geburtsdatum");

    private Button save = new Button("Speichern");
    private Button delete = new Button("Löschen");

    private Candidate candidate;
    private Binder<Candidate> binder = new Binder<>(Candidate.class);
    private Datalist listView;
    private CandidateService service = CandidateService.getInstance();

    public CandidateForm (Datalist datalist) throws IOException {
        //this.setRequiredField(newCandidate);
        this.listView = datalist;
        HorizontalLayout buttons = new HorizontalLayout(save,delete);
        addComponents(id,firstName,lastName,email,status,birthDate,buttons);
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
        //show delete button for only candidate already in the database
        if(candidate == null){
            delete.setVisible(false);
            this.candidate = new Candidate();
        }

        else {
            delete.setVisible(this.candidate.isPersisted());
            this.candidate = candidate;
        }
        binder.setBean(this.candidate);
        setVisible(true);
        firstName.selectAll();
    }

    private void delete() throws IOException {
        candidate = binder.getBean();
        service.delete(candidate);
        this.listView.updateList(service.findAll());
        setCandidate(null);

    }

    private void save() throws IOException {
        candidate = binder.getBean();
        System.out.println(candidate.getId());
        service.save(candidate);
        this.listView.updateList(service.findAll());
        setCandidate(null);

}

    /*private void setRequiredField(boolean newCandidate) {
        if(newCandidate == false)

        "Dieses Feld ist obligatorisch"
    }*/
}
