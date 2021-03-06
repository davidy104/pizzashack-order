package co.nz.pizzashack.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.nz.pizzashack.ds.DepartmentDS;
import co.nz.pizzashack.ds.StaffDS;
import co.nz.pizzashack.ds.UserDS;
import co.nz.pizzashack.ds.WorkflowDS;
import co.nz.pizzashack.support.WorkflowInitialDataSetup;

@Configuration
public class InitialDataConfig {

	@Resource
	private UserDS userDs;

	@Resource
	private DepartmentDS departmentDs;

	@Resource
	private StaffDS staffDs;

	@Resource
	private WorkflowDS workflowDs;

	@Bean(initMethod = "initialize")
	public WorkflowInitialDataSetup setupWorkflowData() {
		return new WorkflowInitialDataSetup(userDs, departmentDs, staffDs,
				workflowDs);
	}
}
