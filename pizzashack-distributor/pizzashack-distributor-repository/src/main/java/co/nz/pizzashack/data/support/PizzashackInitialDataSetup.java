package co.nz.pizzashack.data.support;

import java.math.BigDecimal;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import co.nz.pizzashack.data.support.EntityBuilder.EntityBuilderManager;

public class PizzashackInitialDataSetup {
	private TransactionTemplate transactionTemplate;

	@PersistenceContext
	private EntityManager entityManager;

	Random rand = new Random();

	public PizzashackInitialDataSetup(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void initialize() {
		EntityBuilderManager.setEntityManager(this.entityManager);

		this.transactionTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus status) {
				// initiate menu
				{
					new PizzashackItemBuilder() {
						{
							create("Chicken Parmesan",
									"Grilled chicken, fresh tomatoes, feta and mozzarella cheese",
									new BigDecimal(25.00), "/images/1.png");
						}
					}.build();

					new PizzashackItemBuilder() {
						{
							create("Spicy Italian",
									"Pepperoni and a double portion of spicy Italian sausage",
									new BigDecimal(28.00), "/images/2.png");
						}
					}.build();

					new PizzashackItemBuilder() {
						{
							create("Garden Fresh",
									"Slices onions and green peppers, gourmet mushrooms, black olives and ripe Roma tomatoes",
									new BigDecimal(35.00), "/images/3.png");
						}
					}.build();

					new PizzashackItemBuilder() {
						{
							create("Tuscan Six Cheese",
									"Six cheese blend of mozzarella, Parmesan, Romano, Asiago and Fontina",
									new BigDecimal(26.50), "/images/4.png");
						}
					}.build();

					new PizzashackItemBuilder() {
						{
							create("Spinach Alfredo",
									"Rich and creamy blend of spinach and garlic Parmesan with Alfredo saucePepperoni and a double portion of spicy Italian sausage",
									new BigDecimal(15.10), "/images/5.png");
						}
					}.build();
					new PizzashackItemBuilder() {
						{
							create("BBQ Chicken Bacon",
									"Grilled white chicken, hickory-smoked bacon and fresh sliced onions in barbeque sauce",
									new BigDecimal(45.00), "/images/6.png");
						}
					}.build();
					new PizzashackItemBuilder() {
						{
							create("Hawaiian BBQ Chicken",
									"Grilled white chicken, hickory-smoked bacon, barbeque sauce topped with sweet pine-apple",
									new BigDecimal(37.00), "/images/7.png");
						}
					}.build();
					new PizzashackItemBuilder() {
						{
							create("Grilled Chicken Club",
									"Grilled white chicken, hickory-smoked bacon and fresh sliced onions topped with Roma tomatoes",
									new BigDecimal(29.00), "/images/8.png");
						}
					}.build();
					new PizzashackItemBuilder() {
						{
							create("Double Bacon 6Cheese",
									"Hickory-smoked bacon, Julienne cut Canadian bacon, Parmesan, mozzarella, Romano, Asiago and and Fontina cheese",
									new BigDecimal(32.50), "/images/9.png");
						}
					}.build();
					new PizzashackItemBuilder() {
						{
							create("Chilly Chicken Cordon Bleu",
									"Spinash Alfredo sauce topped with grilled chicken, ham, onions and mozzarella",
									new BigDecimal(28.00), "/images/10.png");
						}
					}.build();
				}

				return null;
			}

		});

		EntityBuilderManager.clearEntityManager();

	}
}
