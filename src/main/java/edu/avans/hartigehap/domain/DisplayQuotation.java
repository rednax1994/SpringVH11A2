package edu.avans.hartigehap.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DisplayQuotation extends DisplayTemplate{@Override
    String displayHeader(Document document) {
        return "test1";
    }

    @Override
    String displayLines(Document document) {
        return "test2";
    }

    @Override
    String displayFooter(Document document) {
        return "test3";
    }

   

}
