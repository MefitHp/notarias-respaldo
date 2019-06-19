package com.palestra.notarias.action.service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.palestra.notarias.action.util.GeneradorId;

public class DataService {

	private static final Logger logger = Logger.getLogger(DataService.class);
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void saveDocumentVersion(String idEscritura, String documentoPath,
			String nodeName, String version, String idNodoAlfresco) {

		String idBitacora = GeneradorId.generaId(new Object());
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tbbsnm07 (idbitversesc, dsruta, dsnombre, idescritura, idsesion, tmstmp, dsversion, idnodoalfresco) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

		// TODO: hay que ver como se obtiene la session
		Object[] params = new Object[] { idBitacora, documentoPath, nodeName,
				idEscritura, "", new Timestamp((new Date())
						.getTime()), version, idNodoAlfresco };

		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR };

		int row = this.jdbcTemplate.update(sql.toString(), params, types);

		logger.info(row + " row inserted.");

	}

	public String getIdByEscrituraNumero(String numEscritura) {
		String sqlNE = "select idescritura from tbbsnm24 where dsnumescritura = '"
				+ numEscritura + "'";

		List<String> resultados = this.jdbcTemplate.queryForList(sqlNE,
				String.class);
		if (resultados.isEmpty()) {
			return null;
		} else if (resultados.size() == 1) {
			return resultados.get(0);
		} else {
			return null;
		}

	}

}