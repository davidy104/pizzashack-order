package co.nz.pizzashack.support;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.nz.pizzashack.data.dto.DepartmentDto;
import co.nz.pizzashack.data.dto.StaffDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.data.dto.WorkflowDto;
import co.nz.pizzashack.ds.DepartmentDS;
import co.nz.pizzashack.ds.StaffDS;
import co.nz.pizzashack.ds.UserDS;
import co.nz.pizzashack.ds.WorkflowDS;

public class WorkflowInitialDataSetup {

	private UserDS userDs;

	private DepartmentDS departmentDs;

	private StaffDS staffDs;

	private WorkflowDS workflowDs;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WorkflowInitialDataSetup.class);

	public WorkflowInitialDataSetup(UserDS userDs, DepartmentDS departmentDs,
			StaffDS staffDs, WorkflowDS workflowDs) {
		this.userDs = userDs;
		this.departmentDs = departmentDs;
		this.staffDs = staffDs;
		this.workflowDs = workflowDs;
	}

	public void initialize() throws Exception {
		LOGGER.info("initialize start:{}");
		// init user
		UserDto davidUser = userDs.createUser("david", "123456");
		UserDto bradUser = userDs.createUser("brad", "123456");
		UserDto johnUser = userDs.createUser("john", "123456");
		// create admin user, just for operation
		userDs.createUser("general", "123456");

		DepartmentDto deptDto = new DepartmentDto();
		deptDto.setDeptName("Order manager");
		deptDto = departmentDs.createDepartment(deptDto);
		LOGGER.info("department:{} ", deptDto);

		Set<Long> deptIds = new HashSet<Long>();
		deptIds.add(deptDto.getDeptId());

		StaffDto staff = new StaffDto();
		staff.getIndividual().setEmail("david.yuan@gmail.com");
		staff.getIndividual().setFirstName("David");
		staff.getIndividual().setLastName("Yuan");
		staff.getIndividual().setIdentity("8934892894");
		staff.setLevel("senior");
		staff.setRole("manager");
		staff.setUser(davidUser);
		staffDs.createStaff(staff, deptIds);

		staff = new StaffDto();
		staff.getIndividual().setEmail("brad.wu@gmail.com");
		staff.getIndividual().setFirstName("Brad");
		staff.getIndividual().setLastName("Wu");
		staff.getIndividual().setIdentity("2938298398");
		staff.setLevel("intermedior");
		staff.setRole("manager");
		staff.setUser(bradUser);
		staffDs.createStaff(staff, deptIds);

		staff = new StaffDto();
		staff.getIndividual().setEmail("john.ni@gmail.com");
		staff.getIndividual().setFirstName("John");
		staff.getIndividual().setLastName("Ni");
		staff.getIndividual().setIdentity("190390293293");
		staff.setLevel("junior");
		staff.setRole("manager");
		staff.setUser(johnUser);
		staffDs.createStaff(staff, deptIds);

		WorkflowDto workflow = workflowDs.deployWorkflow("orderBillingProcess",
				"order", "bpmn/OrderBillingProcess.bpmn20.xml");
		LOGGER.info("deploy billing process, workflow:{} ", workflow);

		workflow = workflowDs.deployWorkflow("orderCoreProcess", "order",
				"bpmn/OrdercoreProcess.bpmn20.xml");
		LOGGER.info("deploy core process, workflow:{} ", workflow);

		workflow = workflowDs.deployWorkflow("orderlocalProcess", "order",
				"bpmn/OrderlocalProcess.bpmn20.xml");
		LOGGER.info("deploy local main process, workflow:{} ", workflow);
	}
}
