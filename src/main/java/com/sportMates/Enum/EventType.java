package com.sportMates.Enum;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum EventType {
    INSCRIPTED("inscripted"),
    OWNED("owned"),
    UNSUBSCRIPTED("unsubscripted");

    private final String value;

    private EventType(String value) {
        this.value = value;
    }

    public boolean isValidValue(String value) {
        boolean isValid = false;
        for(EventType eventType: values()){
            if(eventType.equals(value)){
                isValid = true;
            }
        }
        return isValid;
    }
}
