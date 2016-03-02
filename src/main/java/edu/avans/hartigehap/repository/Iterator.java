package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.Order;

public interface Iterator {
	public boolean hasNext();
	public Order next();
}	