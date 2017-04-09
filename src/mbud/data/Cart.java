package mbud.data;

import java.util.HashMap;
import java.util.Map;

import mbud.hibernate.mapping.Produkt;
import mbud.util.StringUtil;

public class Cart {
	private Map<Produkt, Integer> products = new HashMap<>();
	
	public Cart() {
		
	}
	
	public void addProduct(Produkt p) {
		for (Map.Entry<Produkt, Integer> i : products.entrySet()) {
			if (i.getKey().getId() == p.getId()) {
				i.setValue(i.getValue() + 1);
				return;
			}
		}
		products.put(p, 1);
	}
	
	public int getNumberOfItems() {
		int num = 0;
		for (Integer i : products.values()) {
			num += i;
		}
		return num;
	}
	
	public Map<Produkt, Integer> getProducts() {
		return products;
	}
	
	public String getTotalPrice() {
		double price = 0.0;
		for (Map.Entry<Produkt, Integer> i : products.entrySet()) {
			price += i.getValue() * i.getKey().getCena();
		}
		return StringUtil.formatPrice(price) + " z³";
	}
	
	public void changeProductCount(int id, int count) {
		for (Map.Entry<Produkt, Integer> i : products.entrySet()) {
			if (i.getKey().getId() == id) {
				i.setValue(count);
				return;
			}
		}
	}
	
	public void removeFromCart(int id) {
		for (Map.Entry<Produkt, Integer> i : products.entrySet()) {
			if (i.getKey().getId() == id) {
				products.remove(i.getKey());
				return;
			}
		}
	}
	
	public void clearCart() {
		products = new HashMap<>();
	}
}
