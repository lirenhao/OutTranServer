package com.yada.demo.OutTranServer.dao;

import com.yada.demo.OutTranServer.model.Trans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TransDao extends JpaRepository<Trans, Long>, JpaSpecificationExecutor<Trans> {

    List<Trans> findByTranDateBetween(String startDate, String endDate);
}
