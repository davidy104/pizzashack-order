package co.nz.pizzashack.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CollectionTest.class);

	private Set<Long> selectedIds;
	private Set<Long> needRemoveDeptIds;
	private Set<Long> newDeptIds;
	private Set<Long> existedDeptIds;

	@Before
	public void initialize() throws Exception {
		selectedIds = new HashSet<Long>();
		selectedIds.add(1L);
		selectedIds.add(2L);
		selectedIds.add(3L);

		existedDeptIds = new HashSet<Long>();
		existedDeptIds.add(1L);
		existedDeptIds.add(2L);
		existedDeptIds.add(4L);
//		existedDeptIds.add(3L);
	}

	@Test
	public void test() {
		Set<Long> unionSet = new HashSet<Long>();
		unionSet.addAll(selectedIds);
		unionSet.addAll(existedDeptIds);

		LOGGER.info("unionSet print start :{} ");
		printList(unionSet);
		LOGGER.info("unionSet print end :{} ");

		newDeptIds = unionSet;
		newDeptIds.removeAll(existedDeptIds);

		LOGGER.info("newDeptIds print start :{} ");
		printList(newDeptIds);
		LOGGER.info("newDeptIds print end :{} ");

//		needRemoveDeptIds = unionSet;
		existedDeptIds.removeAll(selectedIds);

		LOGGER.info("needRemoveDeptIds print start :{} ");
		printList(existedDeptIds);
		LOGGER.info("needRemoveDeptIds print end :{} ");

	}

	private void printList(Set<Long> ids) {
		for (Long id : ids) {
			LOGGER.info("id:{} ", id);
		}
	}

}
