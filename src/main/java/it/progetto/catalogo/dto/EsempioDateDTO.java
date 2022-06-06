package it.progetto.catalogo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EsempioDateDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	
	@Schema(example = "20/01/2000", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate data1;
	@Schema(example = "gg/mm/aaaa 23:50:55", type = "string")
	@JsonFormat(pattern = DATE_TIME_PATTERN)
	private LocalDateTime dataTempo;
	
}
