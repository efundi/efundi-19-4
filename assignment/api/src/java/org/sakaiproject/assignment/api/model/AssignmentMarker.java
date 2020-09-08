package org.sakaiproject.assignment.api.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AssignmentMarker represents an assignment Marker.
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ASN_MARKER_T")
@Data
@EqualsAndHashCode(exclude= "submissionMarkers")
public class AssignmentMarker {

	@Id
	@Column(name = "MARKER_ID", length = 36, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "MARKER_USER_ID", nullable = false)
	private String markerUserId;

	@Column(name = "CONTEXT", length = 99, nullable = false)
	private String context;

	@ManyToOne
	@JoinColumn(name = "ASSIGNMENT_ID")
	private Assignment assignment;

    @Column(name = "QUOTA_PERCENTAGE")
    private Double quotaPercentage = 0.0;

    @Column(name = "ORDER_NUM")
    private Integer orderNumber = 0;

    @Column(name = "NUM_ALLOC")
    private Integer numberAllocated = 0;

    @Column(name = "NUM_UPLOADED")
    private Integer numberUploaded = 0;

    @Column(name = "NUM_DOWNLOADED")
    private Integer numberDownloaded = 0;

    @Type(type = "org.sakaiproject.springframework.orm.hibernate.type.InstantType")
    @Column(name = "CREATED_DATE", nullable = false)
    private Instant dateCreated;

    @Type(type = "org.sakaiproject.springframework.orm.hibernate.type.InstantType")
    @Column(name = "MODIFIED_DATE")
    private Instant dateModified;

    @Column(name = "MODIFIER", length = 99)
    private String modifier;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "assignmentMarker", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssignmentSubmissionMarker> submissionMarkers = new HashSet<>();

    @Transient
    private String userDisplayName;

    @Transient
    private String userRole;

    @Transient
    private String userDisplayId;

    @Transient
    private String newAssignments;

    public String getnewAssignments(){   	        			
    	this.newAssignments = getNumberAllocated() - getNumberDownloaded()+"";  	
		return this.newAssignments;
    }	
}
