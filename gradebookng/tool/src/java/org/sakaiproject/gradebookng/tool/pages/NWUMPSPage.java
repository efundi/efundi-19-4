package org.sakaiproject.gradebookng.tool.pages;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.utils.Lists;
import org.apache.wicket.AttributeModifier;
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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.sakaiproject.gradebookng.business.util.AssignmentDataProvider;
import org.sakaiproject.gradebookng.tool.model.GbAssignmentData;
import org.sakaiproject.gradebookng.tool.model.GbSettings;
import org.sakaiproject.gradebookng.tool.panels.NWUMPSStudentInfoPanel;
import org.sakaiproject.service.gradebook.shared.Assignment;
import org.sakaiproject.service.gradebook.shared.GradebookInformation;

/**
 * NWU MPS Page
 *
 * @author Joseph Gillman
 */
public class NWUMPSPage extends BasePage {

	private static final long serialVersionUID = 1L;

	private NWUMPSStudentInfoPanel studentInfoPanel;
	
	private Set<GbAssignmentData> selectedAssignments = new HashSet<GbAssignmentData>();

	public NWUMPSPage() {
		defaultRoleChecksForInstructorOnlyPage();
		disableLink(this.nwumpsPageLink);
		selectedAssignments = new HashSet<GbAssignmentData>();
	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		// get settings data
		final GradebookInformation settings = this.businessService.getGradebookSettings();

		// setup page model
		final GbSettings gbSettings = new GbSettings(settings);
		final CompoundPropertyModel<GbSettings> formModel = new CompoundPropertyModel<>(gbSettings);

		this.studentInfoPanel = new NWUMPSStudentInfoPanel("mpsStudentInfoPanel", formModel);

		// Hide the panel if not showing to non admins and user is not admin
		// if (!this.showGradeEntryToNonAdmins && !this.businessService.isSuperUser()) {
		// this.gradeEntryPanel.setVisible(false);
		// }

//		final CheckGroup<GbAssignmentData> group = new CheckGroup<GbAssignmentData>("selected-assignments", new ArrayList<GbAssignmentData>());
		Form form = new Form("mps-form") {
			@Override
			protected void onSubmit() {
//				info("selected assignments " + group.getDefaultModelObjectAsString());
				
//				System.out.println("selected assignments " + group.getDefaultModelObjectAsString());
				//Publish marks for selected sites
				System.out.println("Send Marks to MPS");
				
				for (GbAssignmentData gbAssignmentData : selectedAssignments) {
					System.out.println("selected assignments " + gbAssignmentData);
				}
				
			}
		};
		// get the list of Assignments
		final List<Assignment> assignments = this.businessService.getGradebookAssignments();

		AssignmentDataProvider assignmentDataProvider = new AssignmentDataProvider(assignments);
//		AjaxFallbackDefaultDataTable assignmentsTable = new AjaxFallbackDefaultDataTable<>("assignments-table", getColumns(), assignmentDataProvider, 25);
		AjaxFallbackDefaultDataTable assignmentsTable = new AjaxFallbackDefaultDataTable<>("assignments-table", getColumns(), assignmentDataProvider, 25);
		assignmentsTable.addBottomToolbar(new NoRecordsToolbar(assignmentsTable));
				
		form.add(new Button("send-marks"));
		
//		DefaultDataTable assignmentsTable = new DefaultDataTable("assignments-table", columns, dataProvider, 10);
		// DataTable assignmentsTable = new DataTable("assignments-table", getColumns(), getDataProvider());
		
//		CheckGroup group = new CheckGroup("selected-assignments");
//		group.add(new CheckGroupSelector("groupselector"));
//		DataTable assignmentsTable = new DataTable("assignments-table", getColumns(), getDataProvider(), 10);
		
//		assignmentsTable.setOutputMarkupPlaceholderTag(true);
//		group.add(assignmentsTable);
//		form.add(group);
		form.add(assignmentsTable);
		add(form);
		
				
//		final AssignmentDataProvider dataProvider = new AssignmentDataProvider();
////		SortableDataProvider<Assignment> dataProvider = new SortableEntityProvider<Assignment>(assignments, "name");
//		
//		List<IColumn<GbAssignmentData, String>> columns = new ArrayList<IColumn<GbAssignmentData, String>>();
//		columns.add(new PropertyColumn<GbAssignmentData, String>(Model.of("name"), "name"));
//		columns.add(new PropertyColumn<GbAssignmentData, String>(Model.of("name"), "name"));
//		columns.add(new PropertyColumn<GbAssignmentData, String>(Model.of("name"), "name"));
//		
//		List<IColumn<UserProvider.Contact, String>> columns = new ArrayList<IColumn<UserProvider.Contact, String>>(2);
//        columns.add(new PropertyColumn<UserProvider.Contact, String>(new Model<String>("First Name"), "name.first", "name.first"));
//        columns.add(new PropertyColumn<UserProvider.Contact, String>(new Model<String>("Last Name"), "name.last", "name.last"));
//        DefaultDataTable<UserProvider.Contact, String> dataTable = new DefaultDataTable<UserProvider.Contact, String>("table", columns, userProvider, 10);

	}

	@SuppressWarnings("unchecked")
	private List<IColumn<GbAssignmentData, String>> getColumns() {

		List<IColumn<GbAssignmentData, String>> columns = Lists.newArrayList();
		columns.add(new AbstractColumn<GbAssignmentData, String>(new Model<String>("Select")) {
            @Override
            public void populateItem(Item<ICellPopulator<GbAssignmentData>> cellItem, String componentId, IModel<GbAssignmentData> rowModel) {
                cellItem.add(new CheckBoxPanel(componentId, rowModel));
                cellItem.add(new AttributeModifier("style", "display: table-cell; text-align: center;"));
            }
        });
		
//		columns.add(new CheckBoxColumn(Model.of("Select")) {
//			
//			@SuppressWarnings("serial")
//			@Override
//			protected IModel newCheckBoxModel(final IModel rowModel) {
//				return new AbstractCheckBoxModel() {
//					@Override
//					public boolean isSelected() {
//						return selectedAssignments.contains(rowModel.getObject());
//					}
//					@Override
//					public void unselect() {
//						selectedAssignments.remove(rowModel.getObject());
//					}
//					@Override
//					public void select() {
//						selectedAssignments.add((GbAssignmentData) rowModel.getObject());
//					}
//				};
//			}
//		});

//        columns.add(new PropertyColumn<GbAssignmentData, String>(new Model<String>("Test Name"), "assignmentName", "assignmentName"));

        columns.add(new PropertyColumn<>(Model.of("Test Name"), "assignmentName"));
//        columns.add(new PropertyColumn<>(Model.of("Detail from MPS"), "assignmentId"));
        
        columns.add(new AbstractColumn<GbAssignmentData, String>(Model.of("Detail from MPS"))
        {
            @Override
            public void populateItem(Item<ICellPopulator<GbAssignmentData>> cellItem, String componentId,
                IModel<GbAssignmentData> model) {
                cellItem.add(new ActionPanel(componentId, model));
                cellItem.add(new AttributeModifier("style", "display: table-cell; text-align: center;"));
            }
        });
		return columns;
	}
	
	class ActionPanel extends Panel
	{
	    /**
	     * @param id
	     *            component id
	     * @param model
	     *            model for contact
	     */
	    public ActionPanel(String id, IModel<GbAssignmentData> model)
	    {
	        super(id, model);
	        add(new Link<Void>("assignment-info")
	        {
	            @Override
	            public void onClick()
	            {
	            	System.out.println((GbAssignmentData) getParent().getDefaultModelObject());

//		              setResponsePage(NWUMPSStudentInfoPanel.class);
	            }
	        });
	    }
	}
	
	class CheckBoxPanel extends Panel {

	    private CheckBox field;

	    public CheckBoxPanel(String id, IModel<GbAssignmentData> model) {
	        super(id, model);
	        field = new CheckBox("checkBox"){
	            public void onClick()
	            {
	            	System.out.println((GbAssignmentData) getParent().getDefaultModelObject());
	            	//load new page with this assigbnment info
	            }
	        };
	        add(field);
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

