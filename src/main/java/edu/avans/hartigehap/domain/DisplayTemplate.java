package edu.avans.hartigehap.domain;

public abstract class DisplayTemplate{
    
    public String displayDocument(Document document){
        return displayHeader(document) + displayLines(document) + displayFooter(document);
    }
    
    abstract String displayHeader(Document document);
    abstract String displayLines(Document document);
    abstract String displayFooter(Document document);
}
