package edu.avans.hartigehap.service;

import org.springframework.ui.Model;

public interface CommandDiningTable {
	
	public void execute(String diningTableId, String menuItemName, Model uiModel);
	
}
