package edu.avans.hartigehap.service;

import org.springframework.ui.Model;

public interface DiningTableCommand {
	
	public void execute(String diningTableId, String menuItemName, Model uiModel);
	
}
