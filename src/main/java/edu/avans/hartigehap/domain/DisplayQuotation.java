package edu.avans.hartigehap.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DisplayQuotation extends DisplayTemplate {

    @Override
    String displayHeader(Document document) {
        String message = "<h1>Test plusje</h1>";
        message += "<br>Evenement datum:" + document.getEventDate();
        message += "<br>Verval datum:" + document.getExpirationDate();
        message += "<br>Start moment:" + document.getStartTimeOfDay();
        message += "<br>Starttijd:" + document.getStartTime();
        message += "<br>Eind moment:" + document.getEndTimeOfDay();
        message += "<br>Eindttijd:" + document.getEndTime();
        message += "<br>Zaal:" + document.getRoom().getRoomNr();
        message += "<br>Contactgegevens klant:" + document.getCustomer().toString();
        message += "<br>Restaurant:" + document.getRestaurant().getId();
        message += "<br>Aantal personen:" + document.getAmountOfPeople();
        message += "<br>Status:" + document.getStatus();
        return message;
    }

    @Override
    String displayLines(Document document) {
        String message = "Offerteregels<br>";
        if (document instanceof Quotation) {
            for (Line line : ((Quotation) document).getQuotationLines()) {
                message += line.toString() + "<br>";
            }
        }
        return message;
    }

    @Override
    String displayFooter(Document document) {
        String message = "Graag reageren binnen 7 dagen";
        return message;
    }

}
