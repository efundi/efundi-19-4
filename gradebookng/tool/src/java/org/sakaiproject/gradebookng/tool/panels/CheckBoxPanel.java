package org.sakaiproject.gradebookng.tool.panels;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.sakaiproject.gradebookng.tool.model.GbAssignmentData;

public class CheckBoxPanel extends Panel {

    private CheckBox field;
	private GbAssignmentData selectedAssignment;

    public CheckBoxPanel(String id, IModel<Boolean> model) {
        super(id);
        field = new CheckBox("checkBox", model){
            public void onClick()
            {
            	selectedAssignment = (GbAssignmentData)getParent().getDefaultModelObject();
            	System.out.println(selectedAssignment);
            	//load new page with this assigbnment info
            }
        };
        add(field);
    }

    public CheckBoxPanel(String id) {
        this(id, new Model<Boolean>());
    }

    public CheckBox getField() {
        return field;
    }
}