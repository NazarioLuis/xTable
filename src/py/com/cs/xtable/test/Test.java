package py.com.cs.xtable.test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.cs.xtable.xutil.Comparison;

public class Test {

	public static void main(String[] args) {
		String columns[] = {
					"id",
					"firstName+lastName=>Nombre y Apellido",
					"birthdate=>Fecha de Nac.",
					"credit=>Crédito",
					"address=>Dirección",
					"active=>Activo"
				};
		
		
		MyTestView testView = new MyTestView();
		testView.setVisible(true);
		
		
		testView.getTable().config(columns);
		

		testView.getTable().addConditions(3, Color.red, Comparison.LT, 10000);
		testView.getTable().addConditions(3, Color.green, Comparison.GTE, 10000);

		List<Customer> customers = new ArrayList<>();
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setFirstName("Name1");
		customer.setLastName("LastName1");
		customer.setBirthdate(new Date());
		customer.setActive(true);
		customer.setCredit(8000d);
		customers.add(customer);
		
		Customer customer1 = new Customer();
		customer1.setId(2);
		customer1.setAddress("Address2");
		customer1.setFirstName("Name2");
		customer1.setBirthdate(new Date());
		customer1.setLastName("LastName2");
		customer1.setActive(true);
		customer1.setCredit(100000d);
		customers.add(customer1);
		
		Customer customer2 = new Customer();
		customer2.setId(3);
		customer2.setFirstName("Name3");
		customer2.setLastName("LastName3");
		customer2.setBirthdate(new Date());
		customer2.setAddress("Address3");
		customer2.setActive(false);
		customers.add(customer2);
		
		
		testView.getTable().setData(customers);
	}

}
