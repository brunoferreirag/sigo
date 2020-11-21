package br.com.indtextbr.services.gestaoprocessosindustriais.repository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import br.com.indtextbr.services.gestaoprocessosindustriais.entity.ProblemaProducao;

@Repository
public class ProblemaProducaoRepository {
	private List<ProblemaProducao> problemas = new ArrayList<>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@PostConstruct
	public void initData() {
		ProblemaProducao problema1 = new ProblemaProducao();
		problema1.setCausa("Parada Eletrica");
		problema1.setDataInicio("2020-11-03 00:00:00");
		problema1.setDataFim("2020-11-03 03:00:00");
		problema1.setCodigoLinha("01");
		problema1.setTurno("Manhã");

		ProblemaProducao problema2 = new ProblemaProducao();
		problema2.setCausa("Acidente");
		problema2.setDataInicio("2020-11-02 00:00:00");
		problema2.setDataFim("2020-11-03 02:00:00");
		problema2.setCodigoLinha("02");
		problema2.setTurno("Tarde");

		ProblemaProducao problema3 = new ProblemaProducao();
		problema3.setCausa("Manutenção Programada");
		problema3.setDataInicio("2020-11-01 00:00:00");
		problema3.setDataFim("2020-11-01 04:00:00");
		problema3.setCodigoLinha("03");
		problema3.setTurno("Noite");

		ProblemaProducao problema4 = new ProblemaProducao();
		problema4.setCausa("Manutenção Programada");
		problema4.setDataInicio("2020-11-01 00:00:00");
		problema4.setDataFim("2020-11-01 04:00:00");
		problema4.setCodigoLinha("01");
		problema4.setTurno("Noite");

		ProblemaProducao problema5 = new ProblemaProducao();
		problema5.setCausa("Manutenção Programada");
		problema5.setDataInicio("2020-11-01 00:00:00");
		problema5.setDataFim("2020-11-01 04:00:00");
		problema5.setCodigoLinha("02");
		problema5.setTurno("Noite");

		this.problemas.add(problema1);
		this.problemas.add(problema2);
		this.problemas.add(problema3);
		this.problemas.add(problema4);
		this.problemas.add(problema5);
	}

	public List<ProblemaProducao> getByTurnoDataInicioAndDataFim(String turno, String dataInicio, String dataFim) {
		LocalDateTime dataDeInicio = LocalDateTime.parse(dataInicio, formatter);
		LocalDateTime dataDeFim = LocalDateTime.parse(dataFim, formatter);
		var results = this.problemas.stream()
				.filter(p ->LocalDateTime.parse(p.getDataInicio(), formatter).isAfter(dataDeInicio)
						|| LocalDateTime.parse(p.getDataInicio(), formatter).isEqual(dataDeInicio) )
				.collect(Collectors.toList());
		
		if(dataFim != null) {
			results = results.stream()
					.filter(p ->LocalDateTime.parse(p.getDataFim(), formatter).isBefore(dataDeFim)
							|| LocalDateTime.parse(p.getDataFim(), formatter).isEqual(dataDeFim) )
					.collect(Collectors.toList());
		}
		if(turno != null) {
			results = results.stream().filter(p -> p.getTurno().toLowerCase().equals(turno.toLowerCase())).collect(Collectors.toList());
		}
		return results;
	}
}
