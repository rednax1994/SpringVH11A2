package edu.avans.hartigehap.service.impl;

import org.springframework.ui.Model;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.service.CommandDiningTable;
import edu.avans.hartigehap.service.DiningTableService;

public class CommandRemoveMenuItem implements CommandDiningTable{

    private DiningTableService diningTableService;

    public CommandRemoveMenuItem(DiningTableService diningTableService){
        this.diningTableService = diningTableService;
    }

    @Override
    public void execute(String diningTableId, String menuItemName, Model uiModel) {
        DiningTable diningTable = diningTableService.fetchWarmedUp(Long.valueOf(diningTableId));
        uiModel.addAttribute("diningTable", diningTable);

        diningTableService.deleteOrderItem(diningTable, menuItemName);

    }


}
