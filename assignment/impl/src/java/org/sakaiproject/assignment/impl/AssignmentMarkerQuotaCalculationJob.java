package org.sakaiproject.assignment.impl;

import java.util.Collection;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.sakaiproject.api.app.scheduler.SchedulerManager;
import org.sakaiproject.assignment.api.AssignmentService;
import org.sakaiproject.assignment.api.model.Assignment;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssignmentMarkerQuotaCalculationJob implements Job {

	private AssignmentService assignmentService;
	private ServerConfigurationService serverConfigurationService;
	private SchedulerManager schedulerManager;

	public void execute(JobExecutionContext jobInfo) throws JobExecutionException {
		if (serverConfigurationService.getBoolean("assignment.useMarker", false)) {
			assignmentService.quotaCalculationJob();
			assignmentService.reassignMarkerQuotaForDeletedMarkers();
		}
	}

	public void init() {
		log.debug("AssignmentMarkerQuotaCalculationJob - init()");
		Scheduler scheduler = schedulerManager.getScheduler(); 
		if (scheduler == null) {
			log.error("Scheduler is down!");
			return;
		}				
	}

	public AssignmentService getAssignmentService() {
		return assignmentService;
	}

	@Autowired
	public void setAssignmentService(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}

	/**
	 * @return the serverConfigurationService
	 */
	public ServerConfigurationService getServerConfigurationService() {
		return serverConfigurationService;
	}

	/**
	 * @param serverConfigurationService
	 *            the serverConfigurationService to set
	 */
	@Autowired
	public void setServerConfigurationService(ServerConfigurationService serverConfigurationService) {
		this.serverConfigurationService = serverConfigurationService;
	}

	public SchedulerManager getSchedulerManager() {
		return schedulerManager;
	}

	@Autowired
	public void setSchedulerManager(SchedulerManager schedulerManager) {
		this.schedulerManager = schedulerManager;
	}
}
