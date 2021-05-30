package com.devsuperior.dspesquisa.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dspesquisa.dto.RecordDTO;
import com.devsuperior.dspesquisa.dto.RecordInsertDTO;
import com.devsuperior.dspesquisa.entities.Record;
import com.devsuperior.dspesquisa.mapper.RecordMapper;
import com.devsuperior.dspesquisa.repositories.RecordRepository;

@Service
public class RecordService {

	@Autowired
	private RecordRepository repository;
	@Autowired
	private RecordMapper mapper;
	
	// RecordDTO => Retorna uma record completo com instant,nome,idade,plataforma,genero,titulo do game
	// Recebe um insertRecordDTO e retorna um recordDTO completo
	
	@Transactional
	public RecordDTO insert(RecordInsertDTO dto) {
		Record entity = mapper.toEntity(dto);
		repository.save(entity);
		return mapper.toRecordDTO(entity);
	}

	
	@Transactional(readOnly = true)
	public Page<RecordDTO> findByMoments(Instant minDate, Instant maxDate, PageRequest pageRequest) {
		return repository.findByMoments(minDate,maxDate,pageRequest).map(x->  new RecordDTO(x));
	}
	
	
}
