package org.sakaiproject.gradebookng.tool.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.util.io.IClusterable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * GbAssignmentData
 *
 * @author Joseph Gillman
 */
@ToString
@EqualsAndHashCode
public class GbAssignmentData implements IClusterable {

	private static final long serialVersionUID = 1L;

	private boolean selected;

	private String assignmentId;

	private String assignmentName;

	private List<GbStudentInfoData> studentInfoDataList = new ArrayList<GbStudentInfoData>();

	public GbAssignmentData() {
	}

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public List<GbStudentInfoData> getStudentInfoDataList() {
		return studentInfoDataList;
	}

	public void setStudentInfoDataList(List<GbStudentInfoData> studentInfoDataList) {
		this.studentInfoDataList = studentInfoDataList;
	}
}
