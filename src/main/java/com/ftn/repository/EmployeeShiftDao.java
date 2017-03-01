package com.ftn.repository;

import com.ftn.model.EmployeeShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Alek on 2/28/2017.
 */
public interface EmployeeShiftDao extends JpaRepository<EmployeeShift, Long> {

    List<EmployeeShift> findByDate(Date date);

    List<EmployeeShift> findByDateBetween(Date from, Date until);
}
