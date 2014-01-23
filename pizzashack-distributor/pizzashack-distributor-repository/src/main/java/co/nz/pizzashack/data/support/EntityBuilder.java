package co.nz.pizzashack.data.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.ArrayUtils;

public abstract class EntityBuilder<T extends Serializable> {

	protected T product;

	{
		initProduct();
	}

	public T build(Boolean... doNotPersist) {
		T product = assembleProduct();
		if (ArrayUtils.isEmpty(doNotPersist)
				|| (ArrayUtils.isNotEmpty(doNotPersist) && doNotPersist[0] == Boolean.FALSE)) {
			EntityBuilderManager.getEntityManager().persist(product);
		}
		T temp = product;
		initProduct();
		return temp;
	}

	abstract void initProduct();

	abstract T assembleProduct();

	public static class EntityBuilderManager {
		private static ThreadLocal<EntityManager> entityManagerHolder = new ThreadLocal<EntityManager>();

		public static void setEntityManager(EntityManager entityManager) {
			entityManagerHolder.set(entityManager);
		}

		public static void clearEntityManager() {
			entityManagerHolder.remove();
		}

		public static EntityManager getEntityManager() {
			return entityManagerHolder.get();
		}
	}
}