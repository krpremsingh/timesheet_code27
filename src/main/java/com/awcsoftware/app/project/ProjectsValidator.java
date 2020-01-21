package com.awcsoftware.app.project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.Util;
import com.awcsoftware.app.employee.EmployeeValidator;

@Component
public class ProjectsValidator {
	static Logger logger = Logger.getLogger(EmployeeValidator.class);
	static Set<String> errorMsg;
	static {
		errorMsg = new LinkedHashSet<String>();
	}

	public LocalDate dateFormatter(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;

	}

	public Set<String> validateProject(ProjectsInfo info) {
		errorMsg.clear();
		if (Util.isEmptyOrNull(info.getProjectName())) {
			errorMsg.add(ProjectsMessageConstants.BlankProjectName.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(info.getBudget())) {
			errorMsg.add(ProjectsMessageConstants.BlankProjectBudget.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(info.getStatus())) {
			errorMsg.add(ProjectsMessageConstants.BlankProjectStatus.getLabel().toString());
			return errorMsg;
		}

		if (!Util.isValidDate(info.getStartDate())) {
			errorMsg.add(ProjectsMessageConstants.InvalidDateFormat.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(info.getStartDate())) {
			errorMsg.add(ProjectsMessageConstants.BlankStartDate.getLabel().toString());
			return errorMsg;
		}

		if (Util.isEmptyOrNull(info.getEndDate())) {
			errorMsg.add(ProjectsMessageConstants.BlankEndDate.getLabel().toString());
			return errorMsg;
		}
		if (!Util.isValidDate(info.getEndDate())) {
			errorMsg.add(ProjectsMessageConstants.InvalidDateFormat.getLabel().toString());
			return errorMsg;
		}
		if (dateFormatter(info.getEndDate()).isBefore(dateFormatter(info.getStartDate()))) {
			errorMsg.add(ProjectsMessageConstants.ProjectEndDateRange.getLabel().toString());
			return errorMsg;
		}
		
         for(ProjectLocations projectLocation:info.getProjectLocations()) {
        	 if(Util.isEmptyOrNull(projectLocation.getWorkLocationId())) {
        		errorMsg.add(ProjectsMessageConstants.BlankProjectLocation.getLabel().toString());
     			return errorMsg; 
        	 }
         }
/*		if (Util.isEmptyOrNull(info.getProjectLocations().get(0).getWorkLocationId())) {
			errorMsg.add(ProjectsMessageConstants.BlankProjectLocation.getLabel().toString());
			return errorMsg;
		}*/
		return errorMsg;

	}

	public Set<String> validateStartDateEndDate(ProjectsInfo info) {

		if (Util.isEmptyOrNull(info.getStartDate())) {
			errorMsg.add(ProjectsMessageConstants.BlankStartDate.getLabel().toString());
			return errorMsg;
		}

		if (Util.isEmptyOrNull(info.getEndDate())) {
			errorMsg.add(ProjectsMessageConstants.BlankEndDate.getLabel().toString());
			return errorMsg;
		}
		if (!Util.isValidDate(info.getEndDate())) {
			errorMsg.add(ProjectsMessageConstants.InvalidDateFormat.getLabel().toString());
			return errorMsg;
		}
		if (dateFormatter(info.getEndDate()).isBefore(dateFormatter(info.getStartDate()))) {
			errorMsg.add(ProjectsMessageConstants.ProjectEndDateRange.getLabel().toString());
			return errorMsg;
		}
		return errorMsg;

	}

}
