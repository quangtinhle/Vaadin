package org.example;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.example.content.CandidateForm;
import org.example.content.Datalist;
import org.example.entities.Candidate;
import org.example.service.CandidateService;

import java.io.IOException;
import java.util.ArrayList;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private CandidateService service = CandidateService.getInstance();
    private Datalist datalist;
    private CandidateForm candidateForm;

    public MyUI() throws IOException {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();
        try {
            datalist = new Datalist();
        } catch (IOException e) {
            e.printStackTrace();
        }

        candidateForm = datalist.getCandidateForm();
        HorizontalLayout hContent = new HorizontalLayout(datalist,candidateForm);
        candidateForm.setCandidate(null);
        candidateForm.setVisible(false);



        hContent.setSizeFull();

        layout.addComponent(hContent);
        datalist.updateList(service.findAll());
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }




}
