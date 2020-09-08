package org.sakaiproject.assignment.api.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AssignmentSubmissionMarker represents a Marker for an Assignment submission.
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ASN_SUBMISSION_MARKER")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude= {"assignmentMarker","assignmentSubmission"})
public class AssignmentSubmissionMarker {

	@Id
	@Column(name = "SUBMISSION_MARKER_ID", length = 36, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "MARKER_ID")
    private AssignmentMarker assignmentMarker;

	@Column(name = "CONTEXT", length = 99, nullable = false)
	private String context;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="SUBMISSION_ID")
    private AssignmentSubmission assignmentSubmission;

    @Column(name = "DOWNLOADED", nullable = false)
    private Boolean downloaded = Boolean.FALSE;

    @Column(name = "UPLOADED", nullable = false)
    private Boolean uploaded = Boolean.FALSE;

    @Type(type = "org.sakaiproject.springframework.orm.hibernate.type.InstantType")
    @Column(name = "CREATED_DATE", nullable = false)
    private Instant dateCreated;

    @Type(type = "org.sakaiproject.springframework.orm.hibernate.type.InstantType")
    @Column(name = "MODIFIED_DATE")
    private Instant dateModified;

    @Column(name = "MODIFIER", length = 99)
    private String modifier;
}
