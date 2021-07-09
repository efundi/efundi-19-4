package org.sakaiproject.gradebookng.tool.model;

import org.apache.wicket.util.io.IClusterable;

import lombok.Data;

/**
 * GbAssignmentData
 *
 * @author Joseph Gillman
 */
@Data
public class GbAssignmentData implements IClusterable {
	
	private boolean selected;

	private String assignmentId;

	private String assignmentName;

	// private List<Assignment> assignments;
	//
	// private GradebookUiSettings uiSettings;
	//
	// public GbAssignmentData(final GradebookNgBusinessService businessService,
	// final GradebookUiSettings settings) {
	//
	// uiSettings = settings;
	// SortType sortBy = SortType.SORT_BY_SORTING;
	// if (settings.isCategoriesEnabled() && settings.isGroupedByCategory()) {
	// // Pre-sort assignments by the categorized sort order
	// sortBy = SortType.SORT_BY_CATEGORY;
	// }
	// assignments = businessService.getGradebookAssignments(sortBy);
	// }
}
