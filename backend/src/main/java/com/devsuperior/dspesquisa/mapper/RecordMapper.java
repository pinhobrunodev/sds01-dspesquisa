package com.devsuperior.dspesquisa.mapper;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devsuperior.dspesquisa.dto.RecordDTO;
import com.devsuperior.dspesquisa.dto.RecordInsertDTO;
import com.devsuperior.dspesquisa.entities.Record;
import com.devsuperior.dspesquisa.repositories.GameRepository;

@Component
public class RecordMapper {

	
	@Autowired
	private GameRepository repository;
	
	
	// nao relaciona o id, o db ja faz isso automaticamente
	
	public Record toEntity(RecordInsertDTO dto) {
		Record entity = new Record();
		entity.setAge(dto.getAge());
		entity.setName(dto.getName());
		entity.setMoment(Instant.now());
		/**
		 * Trago junto com o GAME os atributos dele(title),Plataforma(Platform) e Genero(nome)
		 * 
		 * 
		 * EX:
		 * 
		 * NOME:BRUNO
		 * AGE:21
		 * PLATFORM: PC (0)
		 * GAME: CSGO (1)
		 * => PC(0) CSGO (1) , O GENRE ASSOCIADO A PLATAFORMA(0) , NOME DO JOGO(1) =>  GENRE =  SHOOTER(1)
		 */
		// Instancia o obj monitorado
		entity.setGame(repository.getOne(dto.getGameId()));
		return entity;
	}
	public RecordDTO toRecordDTO(Record entity) {
		RecordDTO dto = new RecordDTO();
		dto.setAge(entity.getAge());
		dto.setGameTitle(entity.getGame().getTitle());
		dto.setGenreName(entity.getGame().getGenre().getName());
		dto.setId(entity.getId());
		dto.setMoment(entity.getMoment());
		dto.setName(entity.getName());
		dto.setPlatform(entity.getGame().getPlatform());
		return dto;
	}
	
}
