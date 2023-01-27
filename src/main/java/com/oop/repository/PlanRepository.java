package com.oop.repository;

 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oop.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer>{
	
	@Query("select distinct(planName) from Plan")
	public List<String> findPlanNames();
	
	@Query("select distinct(planStatus) from Plan")
	public List<String> findPlanStatus();

}
