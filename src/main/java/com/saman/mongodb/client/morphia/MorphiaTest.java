package com.saman.mongodb.client.morphia;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.UpdateResults;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import com.saman.mongodb.client.morphia.domain.Address;
import com.saman.mongodb.client.morphia.domain.Employee;

public class MorphiaTest {

	Morphia morphia;
	Datastore ds;

	@Before
	public void init() throws UnknownHostException {
		morphia = new Morphia();
		ds = morphia.createDatastore(
				new MongoClient(new MongoClientURI(System.getProperty(
						"MONGO_URI", "mongodb://112.124.118.97:27017"))),
				"test");
		morphia.map(Employee.class);
	}

	@Test
	public void testSaveSimple() {
		Key<Employee> misterKey = ds.save(new Employee("Mister", "GOD", null,
				0L));
		System.out.println(misterKey.getId());
	}

	@Test
	public void testSaveWithObject() {
		Employee ee = new Employee("Mister", "GOD", null, 0L);
		ee.setAddress(new Address("Nanjing Jiangsu", "220000"));
		Key<Employee> misterKey = ds.save(ee);
		System.out.println(misterKey.getId());
	}

	@Test
	public void testSaveWithKey() {
		Employee boss = ds.find(Employee.class).field("manager").equal(null)
				.get();
		System.out.println(boss.getFirstName());
		Key<Employee> scottsKey = ds.save(new Employee("Scott", "Hernandez", ds
				.getKey(boss), 150 * 1000L));
		System.out.println(scottsKey.getKind());
	}

	@Test
	public void testSaveWithReference() {
		Employee boss = ds.find(Employee.class).field("manager").equal(null)
				.get();
		List<Employee> underlings = new ArrayList<Employee>();
		underlings.add(boss);
		Employee ee = new Employee("I hava reference", "Hernandez", ds.getKey(boss),
				150 * 1000L);
		ee.setUnderlings(underlings);
		Key<Employee> scottsKey = ds.save(ee);
		System.out.println(scottsKey.getKind());
	}
	
	@Test
	public void findReference() {
		Employee boss = ds.find(Employee.class).field("firstName").equal("I hava reference")
				.get();
		System.out.println(boss.getUnderlings().get(0).getFirstName());
	}

	@Test
	public void testUpdate() {
		Employee boss = ds.find(Employee.class).field("manager").equal(null)
				.get();
		Key<Employee> scottsKey = ds.find(Employee.class).field("firstName")
				.equal("Scott").getKey();
		UpdateResults res = ds.update(
				boss,
				ds.createUpdateOperations(Employee.class).add("underlings",
						scottsKey));
		System.out.println(res.getUpdatedCount());
	}

	@Test
	public void testDeleteAll() {
		WriteResult res = ds.delete(ds.createQuery(Employee.class));
		System.out.println(res.getN());
	}

	@Test
	public void testFindByFilter() {
		Key<Employee> scottsKey = ds.find(Employee.class).field("firstName")
				.equal("Scott").getKey();
		Employee scottsBoss = ds.find(Employee.class)
				.filter("underlings", scottsKey).get();
		System.out.println(scottsBoss.getFirstName());
	}

	@Test
	public void testFindByObject() {
		Employee boss = ds.find(Employee.class).field("manager").equal(null)
				.get();
		for (Employee e : ds.find(Employee.class, "manager", boss))
			System.out.println(e.getFirstName());
	}

}
