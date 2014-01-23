package co.nz.pizzashack.client.data

import groovy.transform.ToString
import co.nz.pizzashack.client.data.dto.PizzashackDto
@ToString(includeNames = true, includeFields=true)
class Cart implements Serializable{
	Map<PizzashackDto, Integer> pizzashackItems = new HashMap<PizzashackDto, Integer>()


	Map<PizzashackDto, Integer> getPizzashackItems() {
		return Collections.unmodifiableMap(this.pizzashackItems);
	}

	void addPizzashack(PizzashackDto pizzashack) {
		if (pizzashackItems.containsKey(pizzashack)) {
			int quantity = pizzashackItems.get(pizzashack)
			quantity++
			pizzashackItems.put(pizzashack, quantity)
		} else {
			pizzashackItems.put(pizzashack, 1)
		}
	}

	void removePizzashack(PizzashackDto pizzashack) {
		pizzashackItems.remove(pizzashack)
	}

	void clear() {
		pizzashackItems.clear()
	}
}
