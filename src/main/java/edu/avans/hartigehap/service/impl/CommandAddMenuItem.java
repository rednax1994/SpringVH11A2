package edu.avans.hartigehap.service.impl;

import org.springframework.ui.Model;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.service.DiningTableCommand;
import edu.avans.hartigehap.service.DiningTableService;

public class CommandAddMenuItem implements DiningTableCommand {

	private DiningTableService diningTableService;
	
	public CommandAddMenuItem(DiningTableService diningTableService){
		this.diningTableService = diningTableService;
	}

	@Override
	public void execute(String diningTableId, String menuItemName, Model uiModel) {
		DiningTable diningTable = diningTableService.fetchWarmedUp(Long.valueOf(diningTableId));
        uiModel.addAttribute("diningTable", diningTable);

        diningTableService.addOrderItem(diningTable, menuItemName);
		
	}

}
