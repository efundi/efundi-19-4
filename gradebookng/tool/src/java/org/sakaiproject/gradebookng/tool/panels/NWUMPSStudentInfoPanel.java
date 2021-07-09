package org.sakaiproject.gradebookng.tool.panels;

import org.apache.wicket.model.IModel;
import org.sakaiproject.gradebookng.tool.model.GbSettings;

/**
 * NWUMPSStudentInfoPanel
 *
 * @author Joseph Gillman
 *
 */
public class NWUMPSStudentInfoPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	
	IModel<GbSettings> model;

	public NWUMPSStudentInfoPanel(final String id, final IModel<GbSettings> model) {
		super(id, model);
		this.model = model;
	}
}
