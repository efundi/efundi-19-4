package za.ac.nwu;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class NWUGradebookRecord {

	private static final long serialVersionUID = 1L;

	public static final String STATUS_NEW = "NEW";
	public static final String STATUS_SUCCESS = "SUCCESS";
	public static final String STATUS_FAIL = "FAIL";

	private Long id;

	@EqualsAndHashCode.Include
    private String siteId;

	@EqualsAndHashCode.Include
    private String studentNumber;

	@EqualsAndHashCode.Include
    private double grade;

	@EqualsAndHashCode.Include
    private int gradableObjectId;

    private Date createdDate;
    
    private Date modifiedDate;

	@EqualsAndHashCode.Include
    private String status;
	
    private int retryCount;
    
    private String description;
}
