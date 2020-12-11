package org.sakaiproject.assignment.api.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AssignmentSubmissionMarkerHisotry represents a History of Markers for an Assignment submission.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ASN_SUBMISSION_MARKER_HISTORY")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class AssignmentMarkerHistory {

	@Id
	@Column(name = "HIST_ID", length = 36, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

    @Column(name = "OLD_MARKER_ID", length = 36)
    private String oldMarkerId;

    @Column(name = "NEW_MARKER_ID", length = 36)
    private String newMarkerId;

	@Column(name = "CONTEXT", length = 99, nullable = false)
	private String context;

    @Column(name="OLD_QUOTA")
    private Double oldQuotaPercentage;

    @Column(name="NEW_QUOTA")
    private Double newQuotaPercentage;

    @Type(type = "org.sakaiproject.springframework.orm.hibernate.type.InstantType")
    @Column(name = "MODIFIED_DATE")
    private Instant dateModified;

    @Column(name = "MODIFIER", length = 99)
    private String modifier;
}
