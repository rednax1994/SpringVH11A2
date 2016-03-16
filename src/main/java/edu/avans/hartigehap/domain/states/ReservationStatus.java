package edu.avans.hartigehap.domain.states;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.DomainObject;
import edu.avans.hartigehap.domain.Reservation;
import edu.avans.hartigehap.domain.StateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
public abstract class ReservationStatus extends DomainObject {
    private static final long serialVersionUID = 1L;
    
    public enum ReservationStatusId {
        CONCEPT, APPROVED, FINISHED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    protected ReservationStatusId reservationStatusId;
    
    @OneToOne(mappedBy = "currentState")
    private Reservation reservation;
    
    public ReservationStatus(Reservation reservation) {
        this.reservation = reservation;
    }
    
    public abstract void acceptReservation() throws StateException;
    
    public abstract void endReservation() throws StateException;
    
}
