package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.Owner;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class OwnerServiceTest extends AbstractTransactionRollbackTest{
	
	private static final String OWNER_NAME = "Xander";
	private static final String OWNER_NAME2 = "Richard";

	@Autowired
	private OwnerService ownerService;
	
	@Test
	public void create() {
		Owner owner = createOwner(OWNER_NAME);
		
		List<Owner> owners = ownerService.findAll();
		assertNotNull(owners);
		assertTrue("created owner in list", owners.contains(owner));
	}
	
	@Test
	public void findById(){
		//prepare
		Owner owner = createOwner(OWNER_NAME);
		
		//execute
		Owner owner2 = ownerService.findById(owner.getId());
		
		//verify
		assertTrue("created owner in findByFirstNameAndLastName", owner.equals(owner2));
	}
	
	@Test
	public void findByName(){
		//prepare
		Owner owner = createOwner(OWNER_NAME);
				
		//execute
		List<Owner> owners = ownerService.findByName(OWNER_NAME);
		
		//verify
		assertTrue("created owner in findByName", owners.contains(owner));
	}
	
	@Test
	public void delete(){
		Owner owner = createOwner(OWNER_NAME);
		List<Owner> owners = ownerService.findAll();
		assertNotNull(owners);
		assertTrue("created owner in list", owners.contains(owner));
		
		ownerService.delete(owner);
		
		List<Owner> owner2 = ownerService.findAll();
        assertNotNull(owner2);
        assertFalse("deleted customer not in the list", owner2.contains(owner2));
	}
	
	@Test
	public void update(){
		Owner owner = createOwner(OWNER_NAME);
        owner.setName(OWNER_NAME2);

        Owner owner2 = ownerService.findById(owner.getId());
        assertEquals("firstName", OWNER_NAME2, owner2.getName());
	}

	private Owner createOwner(String name) {
        Owner owner = new Owner();
        owner.setName(name);
        Owner retval = ownerService.save(owner);
        assertNotNull(retval);
        assertNotNull(retval.getId());
        assertEquals("name", name, retval.getName());
        return retval;
    }
	
	

}
