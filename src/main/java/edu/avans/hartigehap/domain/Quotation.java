package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.service.impl.BanquetingFacadeImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
@NoArgsConstructor
public class Quotation extends DomainObject {

	private Quotation(QuotationBuilder builder) {
		this.number = builder.number;
		this.eventDate = builder.eventDate;
		this.expirationDate = builder.expirationDate;
		this.status = builder.status;
	}

	private static final long serialVersionUID = 1L;

	private int number;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(iso = ISO.DATE)
	private DateTime eventDate;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(iso = ISO.DATE)
	private DateTime expirationDate;

	// @ManyToOne
	// private Room room;

	@ManyToOne
	private Customer customer;

	@ManyToOne
	private Restaurant restaurant;

    @Transient
    private BanquetingFacadeImpl banquetingfacade = new BanquetingFacadeImpl();

	// private TemplateMailer templatemailer;

	@Enumerated(EnumType.ORDINAL)
	// represented in database as integer
	private Status status;

	@OneToMany(mappedBy = "quotation")
	private Collection<Line> quotationLines = new ArrayList<Line>();

	@OneToMany(mappedBy = "invoice")
	private Collection<Line> invoiceLines = new ArrayList<Line>();

	public static class QuotationBuilder {

		private Status status;
		private DateTime expirationDate;
		private DateTime eventDate;
		private int number;

		public QuotationBuilder(int number) {
			this.number = number;
		}

		public QuotationBuilder status(Status status) {
			this.status = status;
			return this;
		}

		public QuotationBuilder expirationDate(DateTime expirationDate) {
			this.expirationDate = expirationDate;
			return this;
		}

		public QuotationBuilder eventDate(DateTime eventDate) {
			this.eventDate = eventDate;
			return this;
		}

		public Quotation build() {
			return new Quotation(this);
		}

	}

}
