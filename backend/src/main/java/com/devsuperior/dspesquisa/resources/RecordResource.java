package com.devsuperior.dspesquisa.resources;

import java.time.Instant;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dspesquisa.dto.RecordDTO;
import com.devsuperior.dspesquisa.dto.RecordInsertDTO;
import com.devsuperior.dspesquisa.services.RecordService;

@RestController
@RequestMapping(value = "/records")
public class RecordResource {

	@Autowired
	private RecordService service;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RecordDTO> insert(@RequestBody RecordInsertDTO dto) {
		return ResponseEntity.ok().body(service.insert(dto));
	}

	
	@GetMapping
	// Colocandos os parametros da requisicao
		/*
		 * nome do parametro , defaultValue caso nao seja informado(opcional), variavel q vai ser o parametro do metodo
		 */
	public ResponseEntity<Page<RecordDTO>> findAll(
			// data inicial
			@RequestParam(value = "min", defaultValue = "") String min,
			// dataFinal
			@RequestParam(value = "max", defaultValue = "") String max,
			//ex: page=3, se nao informar por padrao vai ser a 0
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			//ex: Quantas linhas por pagina? se veio Zerado , vai mostrar todos
			@RequestParam(value = "linesPerPage", defaultValue = "0") Integer linesPerPage,
			// ex: Como vai ser ordenado ? pelo moment 
			@RequestParam(value = "orderBy", defaultValue = "moment") String orderBy,
			// ex: a direcao q vai ser ordenando ? descendente
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		
		/*
		 * Se as datas vierem nula eu seto o valor NULL para elas
		 * Se as datas nao vierem nulas eu converto a String em um Instant
		 */
		
		Instant minDate = ("".equals(min)) ? null : Instant.parse(min);
		Instant maxDate = ("".equals(max)) ? null : Instant.parse(max);
		
		if (linesPerPage == 0) {
			// Recebe o maximo valor possivel(busca todos os registros)
			linesPerPage = Integer.MAX_VALUE;
		}
		
		// Objeto de paginacao para considerar os parametros de paginacao
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<RecordDTO> list = service.findByMoments(minDate, maxDate, pageRequest);
		return ResponseEntity.ok().body(list);
	}

}
