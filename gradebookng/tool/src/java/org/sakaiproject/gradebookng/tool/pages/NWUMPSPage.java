package org.sakaiproject.gradebookng.tool.pages;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.gradebookng.business.util.AssignmentDataProvider;
import org.sakaiproject.gradebookng.tool.model.GbAssignmentData;
import org.sakaiproject.gradebookng.tool.panels.NWUMPSStudentInfoPanel;
import org.sakaiproject.service.gradebook.shared.Assignment;

import za.ac.nwu.NWUGradebookPublishUtil;
import za.ac.nwu.NWUGradebookRecord;

/**
 * NWU MPS Page
 *
 * @author Joseph Gillman
 */
public class NWUMPSPage extends BasePage {

	private static final long serialVersionUID = 1L;

    private static final String SAK_PROP_DB_URL = "url@javax.sql.BaseDataSource";
    private static final String SAK_PROP_DB_USERNAME = "username@javax.sql.BaseDataSource";
    private static final String SAK_PROP_DB_PASSWORD = "password@javax.sql.BaseDataSource";
	
	private Set<GbAssignmentData> selectedAssignments = new HashSet<GbAssignmentData>();
	private Panel assignmentPanel = null;
	private Panel current = assignmentPanel;
	private static NWUGradebookPublishUtil gbUtil = null;

	/**
	 * 
	 */
	public NWUMPSPage() {
		defaultRoleChecksForInstructorOnlyPage();
		disableLink(this.nwumpsPageLink);		
		ServerConfigurationService serverConfigService = businessService.getServerConfigService();
		gbUtil = NWUGradebookPublishUtil.getInstance(serverConfigService.getString(SAK_PROP_DB_URL), serverConfigService.getString(SAK_PROP_DB_USERNAME), 
				serverConfigService.getString(SAK_PROP_DB_PASSWORD));
		assignmentPanel = new AssignmentPanel("main-panel");
	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		Form form = new Form("mps-form") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				//Publish marks for selected sites
				System.out.println("Send Marks to MPS");
				
				List<String> selectedAssignmentIds = selectedAssignments.stream().map(GbAssignmentData::getAssignmentId).collect(Collectors.toList());
				Map<String, List<String>> sectionUsersMap = businessService.getSectionUsersForCurrentSite();	
				gbUtil.publishGradebookDataToMPS(businessService.getCurrentSiteId(), sectionUsersMap, selectedAssignmentIds);
			}
		};
		form.add(current);
		add(form);
	}

	
	class AjaxCheckBoxPanel extends Panel {
		private static final long serialVersionUID = 1L;

		private AjaxCheckBox field;

		public AjaxCheckBoxPanel(String id, IModel<GbAssignmentData> model) {
			super(id, model);
			field = new AjaxCheckBox("checkBox", newCheckBoxModel(model)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					
					GbAssignmentData selectedAssignment = (GbAssignmentData) getParent().getDefaultModelObject();
					if(getModelObject().booleanValue()) {
						if(!selectedAssignments.contains(selectedAssignment)) {
							selectedAssignments.add(selectedAssignment);
						}
					} else {
						if(selectedAssignments.contains(selectedAssignment)) {
							selectedAssignments.remove(selectedAssignment);
						}
					}
				}
			};
			add(field);
		}

		protected IModel<Boolean> newCheckBoxModel(IModel<GbAssignmentData> model) {
			return new PropertyModel<Boolean>(model, "selected");
		}

		public AjaxCheckBoxPanel(String id) {
			this(id, new Model<GbAssignmentData>());
		}

		public CheckBox getField() {
			return field;
		}
	}

	class AssignmentPanel extends Panel {
		private static final long serialVersionUID = 1L;
	    /**
	     * @param id
	     *            component id
	     */
	    public AssignmentPanel(String id) {
	        super(id);

			// get the list of Assignments
			final List<Assignment> assignments = businessService.getGradebookAssignments();
			List<Long> assignmentIds = assignments.stream().map(Assignment::getId).collect(Collectors.toList());
			Map<Long, List<NWUGradebookRecord>> studentInfoMap = gbUtil.getStudentInfoMap(businessService.getCurrentSiteId(), assignmentIds);
			
			AssignmentDataProvider assignmentDataProvider = new AssignmentDataProvider(assignments, studentInfoMap);
			AjaxFallbackDefaultDataTable assignmentsTable = new AjaxFallbackDefaultDataTable<>("assignments-table", getColumns(), assignmentDataProvider, 25);
			assignmentsTable.addBottomToolbar(new NoRecordsToolbar(assignmentsTable));
					
			add(new Button("send-marks"));
			add(assignmentsTable);
	    }		
	}
	
	@SuppressWarnings("unchecked")
	private List<IColumn<GbAssignmentData, String>> getColumns() {

		List<IColumn<GbAssignmentData, String>> columns = Lists.newArrayList();
		columns.add(new AbstractColumn<GbAssignmentData, String>(new Model<String>("Select")) {
            @Override
            public void populateItem(Item<ICellPopulator<GbAssignmentData>> cellItem, String componentId, IModel<GbAssignmentData> rowModel) {
                cellItem.add(new AjaxCheckBoxPanel(componentId, rowModel));
                cellItem.add(new AttributeModifier("style", "display: table-cell; text-align: center;"));
            }
        });
        columns.add(new PropertyColumn<>(Model.of("Test Name"), "assignmentName"));        
        columns.add(new AbstractColumn<GbAssignmentData, String>(Model.of("Detail from MPS"))
        {
            @Override
            public void populateItem(Item<ICellPopulator<GbAssignmentData>> cellItem, String componentId,
                IModel<GbAssignmentData> rowModel) {
                cellItem.add(new LinkPanel(componentId, rowModel));
                cellItem.add(new AttributeModifier("style", "display: table-cell; text-align: center;"));
            }
        });
		return columns;
	}
	
	class LinkPanel extends Panel {
		private static final long serialVersionUID = 1L;
	    /**
	     * @param id
	     *            component id
	     * @param model
	     *            model for contact
	     */
	    public LinkPanel(String id, IModel<GbAssignmentData> model) {
	        super(id, model);
	        add(new AjaxLink<Void>("assignment-info") {
				private static final long serialVersionUID = 1L;
				@Override
				public void onClick(AjaxRequestTarget target) {
	            	System.out.println((GbAssignmentData) getParent().getDefaultModelObject());
	            	NWUMPSStudentInfoPanel studentInfoPanel = new NWUMPSStudentInfoPanel("main-panel", (GbAssignmentData) getParent().getDefaultModelObject());
	            	studentInfoPanel.setOutputMarkupId(true);
	            	current.replaceWith(studentInfoPanel);
	            	target.add(studentInfoPanel);
	            	current = studentInfoPanel;
//		            setResponsePage(new NWUMPSStudentInfoPanel();
				}
	        });
	    }
	}
	
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(
			CssHeaderItem.forReference(new CssResourceReference(NWUMPSPage.class, "repeater.css")));
	}
}

