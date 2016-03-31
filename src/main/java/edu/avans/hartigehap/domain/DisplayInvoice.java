package edu.avans.hartigehap.domain;

public class DisplayInvoice extends DisplayTemplate {
    private static final double HUNDERD = 100;
    private static final double HUNDERDTWENTYONE = 121;
    
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
        String message = "<br>Factuurregels<br>" + "<table border='1'><tr>" + "<td></td>" + "<th>Description</th>"
                + "<th>Quantity</th>" + "<th>Price</th>" + "<th>Total</th>" + "<th>Discount</th>" + "<th>BTW</th>"
                + "<th>Subtotal</th>" + "</tr>";
        
        for (Line line : document.getInvoice().getInvoiceLines()) {
            message += "<tr>" + "<td></td>" + "<td>" + line.getDescription() + "</td>" + "<td>" + line.getQuantity()
                    + "</td>" + "<td>" + line.getPrice() + "</td>" + "<td>" + line.getTotal() + "</td>" + "<td>"
                    + line.getDiscount() + "</td>" + "<td>21%</td>" + "<td>"
                    + (line.getTotal()
                            - ((line.getTotal() / HUNDERD) * line.getDiscount()) * HUNDERDTWENTYONE / HUNDERD)
                    + "</td></tr>";
        }
        message += "</table>";
        return message;
    }
    
    @Override
    String displayFooter(Document document) {
        return "Wij verwachten de betaling binnen 2 weken op bankrekeningnummer NL01ABNA01234567889<br>"
                + "Tot snel bij" + document.getRestaurant().getId();
    }
    
}
