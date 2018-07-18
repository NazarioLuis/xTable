# xTable
Una alternativa súper fácil para trabajar con jTable: con una implementación de modelo de tabla genérica usando reflection

```java

//.........
table = new AwesomeTable();
//.........

String columns[] = {
      "id",
      "firstName+lastName=>Nombre y Apellido",
      "credit=>Crédito",
      "address=>Dirección",
      "active=>Activo"
    };

table.config(columns);

table.addConditions(3, Color.green, Comparison.BETWEEN, 5000, 10000);
table.addConditions(5, Color.red, Comparison.EQ, false);

List<Customer> customers = new ArrayList<>();

Customer customer1 = new Customer();
customer1.setId(1);
customer1.setFirstName("Name1");
customer1.setLastName("LastName1");
customer1.setActive(true);
customer1.setCredit(8000d);
customers.add(customer1);

Customer customer2 = new Customer();
customer2.setId(2);
customer2.setAddress("Address2");
customer2.setFirstName("Name2");
customer2.setLastName("LastName2");
customer2.setActive(true);
customer2.setCredit(100000d);
customers.add(customer2);

table.setData(customers);
```
