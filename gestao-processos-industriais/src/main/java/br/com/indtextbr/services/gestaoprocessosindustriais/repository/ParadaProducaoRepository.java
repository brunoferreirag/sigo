package br.com.indtextbr.services.gestaoprocessosindustriais.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import br.com.indtextbr.services.gestaoprocessosindustriais.entity.ParadaProducao;




@Repository
public class ParadaProducaoRepository {
	private  List<ParadaProducao> paradas = new ArrayList<>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@PostConstruct
	public void initData() {
		ParadaProducao parada1 = new ParadaProducao();
		parada1.setCausa("Parada Eletrica");
		parada1.setDataInicio("2020-11-03 00:00:00");
		parada1.setDataFim("2020-11-03 03:00:00");
		parada1.setCodigoLinha("01");
		parada1.setPercentualParado(new BigDecimal(30));
		parada1.setTurno("Manhã");
		
		ParadaProducao parada2 = new ParadaProducao();
		parada2.setCausa("Acidente");
		parada2.setDataInicio("2020-11-02 00:00:00");
		parada2.setDataFim("2020-11-03 02:00:00");
		parada2.setCodigoLinha("02");
		parada2.setPercentualParado(new BigDecimal(100));
		parada2.setTurno("Tarde");
		
		ParadaProducao parada3 = new ParadaProducao();
		parada3.setCausa("Manutenção Programada");
		parada3.setDataInicio("2020-11-01 00:00:00");
		parada3.setDataFim("2020-11-01 04:00:00");
		parada3.setCodigoLinha("03");
		parada3.setPercentualParado(new BigDecimal(100));
		parada3.setTurno("Noite");
		
		ParadaProducao parada4 = new ParadaProducao();
		parada4.setCausa("Manutenção Programada");
		parada4.setDataInicio("2020-11-01 00:00:00");
		parada4.setDataFim("2020-11-01 04:00:00");
		parada4.setCodigoLinha("01");
		parada4.setPercentualParado(new BigDecimal(100));
		parada4.setTurno("Noite");
		
		this.paradas.add(parada1);
		this.paradas.add(parada2);
		this.paradas.add(parada3);
		this.paradas.add(parada4);
	}

	
	public List<ParadaProducao> getByTurnoAndDataInicioAndDataFim(String turno, String dataInicio, String dataFim) {
		LocalDateTime dataDeInicio = LocalDateTime.parse(dataInicio, formatter);
		LocalDateTime dataDeFim = LocalDateTime.parse(dataFim, formatter);
		var results = this.paradas.stream()
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
