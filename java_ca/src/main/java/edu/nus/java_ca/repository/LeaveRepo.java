package edu.nus.java_ca.repository;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.nus.java_ca.model.Department;
import edu.nus.java_ca.model.Leave;
import edu.nus.java_ca.model.LeaveStatus;
import edu.nus.java_ca.model.User;

public interface LeaveRepo extends JpaRepository<Leave, Integer> {

	@Query("SELECT l FROM Leave l where l.leaveId = :Id")
	Leave findLeaveById(@Param("Id") Long d);

	//this is for checking EMPL LEAVE HISTORY
	@Query("SELECT l FROM Leave l where l.user.userId = :uid")
	public ArrayList<Leave> findLeaveByUserId(@Param("uid") Long uid);
	//public ArrayList<Leave> findLeaveByUser_UserIdLike(Long id);
	
	@Query("SELECT l FROM Leave l" 
			+ " WHERE (l.status=:APPLIED " 
			+ "OR l.status= :UPDATED) " + " AND l.user in (SELECT u  FROM User u WHERE u.department =:DEPARTMENT)")
	 public ArrayList<Leave> findLeaveToApprove(@Param("APPLIED") LeaveStatus a, 
			@Param("UPDATED") LeaveStatus u, @Param ("DEPARTMENT")  Department department);

	@Query("SELECT l FROM Leave l" 
			+ " WHERE (l.status=:APPLIED " 
			+ "OR l.status= :UPDATED)")
	 public ArrayList<Leave> findLeaveToApprove(@Param("APPLIED") LeaveStatus a, 
			@Param("UPDATED") LeaveStatus u);
	
	
	//NEW QUERY
	@Query("SELECT l FROM Leave l WHERE year(l.startDate)=?1 AND month(l.startDate)=?2")
	public ArrayList<Leave> getByYearandMonth(int year, int month);
	
	
	
	@Query("SELECT c from Leave c WHERE c.status='APPLIED' OR c.status='APPROVED' OR c.status='UPDATED'")
	ArrayList<Leave> findAppliedLeaves();
	
	@Query("SELECT l  FROM  Leave l WHERE l.user = :user")
	public ArrayList<Leave>  findLeaveByUser(@Param("user")User user);

	Leave findByStartDateAndEndDate(LocalDate s, LocalDate e);
	
}
