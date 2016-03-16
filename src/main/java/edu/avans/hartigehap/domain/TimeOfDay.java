package edu.avans.hartigehap.domain;

import java.util.Date;

import lombok.*;

@Getter
@Setter
public class TimeOfDay {
    public enum TimeOfDayEnum{
        MORNING, NOON, EVENING
    }
    
    private Date time;
    protected TimeOfDayEnum timeOfDayEnum;
    
    public TimeOfDay(Date time, TimeOfDayEnum timeOfDayEnum){
        this.time = time;
        this.timeOfDayEnum = timeOfDayEnum;
    }
    
}
