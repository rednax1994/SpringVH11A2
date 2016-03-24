package edu.avans.hartigehap.domain;

public class DisplayInvoice extends DisplayTemplate{

    @Override
    String displayHeader(Document document) {
        return "halllo";
    }

    @Override
    String displayLines(Document document) {
     return "test";
    }

    @Override
    String displayFooter(Document document) {
     return "boeh";
    }

    

}
