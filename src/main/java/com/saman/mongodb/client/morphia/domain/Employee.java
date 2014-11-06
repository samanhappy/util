package com.saman.mongodb.client.morphia.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.PostLoad;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import com.mongodb.DBObject;

@Entity("employees")
public class Employee {
	
	public Employee() {
	}

	public Employee(String firstName, String lastName, Key<Employee> manager,
			Long salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.manager = manager;
		this.salary = salary;
	}

	// auto-generated, if not set (see ObjectId)
	@Id
	ObjectId id;

	// value types are automatically persisted
	String firstName, lastName;

	// only non-null values are stored
	Long salary = null;

	// by default fields are @Embedded
	Address address;

	// references can be saved without automatic loading
	Key<Employee> manager;

	// refs are stored**, and loaded automatically
	// @Reference will not save objects, just a reference to them; You must save them yourself.
	@Reference
	List<Employee> underlings = new ArrayList<Employee>();

	// stored in one binary field
	// @Serialized EncryptedReviews;

	// fields can be renamed
	@Property("started")
	Date startDate;
	@Property("left")
	Date endDate;

	// fields can be indexed for better performance
	@Indexed
	boolean active = false;

	// fields can loaded, but not saved
	@NotSaved
	String readButNotStored;

	// fields can be ignored (no load/save)
	@Transient
	int notStored;

	// not @Transient, will be ignored by Serialization/GWT for example.
	transient boolean stored = true;
	
	// Lifecycle methods -- Pre/PostLoad, Pre/PostPersist...
	@PostLoad
	void postLoad(DBObject dbObj) {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Employee> getUnderlings() {
		return underlings;
	}

	public void setUnderlings(List<Employee> underlings) {
		this.underlings = underlings;
	}
}
