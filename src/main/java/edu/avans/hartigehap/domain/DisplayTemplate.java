package edu.avans.hartigehap.domain;

public abstract class DisplayTemplate{
    
    public void displayDocument(){
        displayHeader();
        displayLines();
        displayFooter();
    }
    
    abstract void displayHeader();
    abstract void displayLines();
    abstract void displayFooter();
}
