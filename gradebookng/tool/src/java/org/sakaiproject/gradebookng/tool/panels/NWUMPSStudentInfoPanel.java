package org.sakaiproject.gradebookng.tool.panels;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.sakaiproject.gradebookng.business.util.StudentInfoDataProvider;
import org.sakaiproject.gradebookng.tool.model.GbAssignmentData;
import org.sakaiproject.gradebookng.tool.model.GbStudentInfoData;
import org.sakaiproject.gradebookng.tool.pages.NWUMPSPage;

/**
 * NWUMPSStudentInfoPanel
 *
 * @author Joseph Gillman
 *
 */
public class NWUMPSStudentInfoPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	
	public NWUMPSStudentInfoPanel(String id) {
		super(id);
		addPanelComponents(null);
	}
	
	public NWUMPSStudentInfoPanel(String id, final GbAssignmentData assignmentData) {
		super(id);
		addPanelComponents(assignmentData);
	}

	private void addPanelComponents(final GbAssignmentData assignmentData) {
		StudentInfoDataProvider studentInfoDataProvider = new StudentInfoDataProvider(assignmentData);
		AjaxFallbackDefaultDataTable studentInfoTable = new AjaxFallbackDefaultDataTable<>("student-info-table", getColumns(), studentInfoDataProvider, 100);
		studentInfoTable.addBottomToolbar(new NoRecordsToolbar(studentInfoTable));
				
		add(new Link<Void>("student-info-close") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(NWUMPSPage.class);
			}

			@Override
			public boolean isVisible() {
				return (businessService.isUserAbleToEditAssessments());
			}
		});		
		add(studentInfoTable);
	}

	@SuppressWarnings("unchecked")
	private List<IColumn<GbStudentInfoData, String>> getColumns() {

		List<IColumn<GbStudentInfoData, String>> columns = Lists.newArrayList();
        columns.add(new PropertyColumn<>(Model.of("UserId"), "userId"));
        columns.add(new PropertyColumn<>(Model.of("Status"), "status"));
        columns.add(new PropertyColumn<>(Model.of("Error Message"), "errorMessage"));
		return columns;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(
			CssHeaderItem.forReference(new CssResourceReference(NWUMPSPage.class, "repeater.css")));
	}
}
