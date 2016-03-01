package edu.avans.hartigehap.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.avans.hartigehap.domain.Owner;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.OwnerRepository;
import edu.avans.hartigehap.service.OwnerService;
import edu.avans.hartigehap.service.RestaurantService;

@Service("ownerService")
@Repository
@Transactional
public class OwnerServiceImpl implements OwnerService {

	@Autowired private OwnerRepository ownerRepository;
	@Autowired private RestaurantService restaurantService;

	@Override
	@Transactional(readOnly = true)
	public List<Owner> findAll() {
		return Lists.newArrayList(ownerRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findById(Long id) {
		return ownerRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Owner> findByName(String name) {
		return ownerRepository.findByName(name);
	}

	@Override
	public Owner save(Owner owner) {
		return ownerRepository.save(owner);
	}

	@Override
	public void delete(Owner owner) {
		ownerRepository.delete(owner);
	}

	@Override
	public List<Owner> findByRestaurants(String restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);
		return ownerRepository.findByRestaurants(Arrays.asList(new Restaurant[]{restaurant}), new Sort(Sort.Direction.ASC, "name"));
	}

}
