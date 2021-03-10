package com.tbp.av.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tbp.av.model.Nfe;
import com.tbp.av.model.User;
import java.util.Date;



public interface  NfeRepository extends CrudRepository<Nfe, Integer> {
	

		List<Nfe> findByUser(User user);
		
		@Query(value = "select   "
				+ "nfe.chnfe,"
				+ "nfe.id, "
				+ " nfe.cnpjremetente,"
				+ " nfe.dataemissao,"
				+ " nfe.ie,"
				+ " nfe.nome,"
				+ " nfe.tiponf,"
				+  " nfe.valor,"
				+ " nfe.cnpj"
				+ " from nfe  "
				+ " where "
				+ "nfe.dataemissao >= :dataini"
				+ " and nfe.dataemissao <= :datafim"
				+ " and nfe.cnpj = :cnpj"
				, nativeQuery = true)
	    List<Nfe> findAllWithCreationDateTimeBefore(
	      @Param("dataini") String dataini,
	      @Param("datafim") String datafim,
	      @Param("cnpj") Integer cnpj
	      
	      );

}


/*
 * @Query(value = "select distinct  " + "nfe.chnfe," + " nfe.cnpjremetente," +
 * " nfe.dataemissao," + " nfe.ie," + " nfe.nome," + " nfe.tiponf," +
 * " nfe.valor," + " nfe.cnpj," + " user.id" +
 * " from nfe join user on  nfe.cnpj = user.id " + " where " +
 * "nfe.dataemissao >= :dataini" + " and nfe.dataemissao <= :datafim" +
 * " and user.cnpj = :cnpj ", nativeQuery = true)
 */