package com.example.softarex_demo_project.dto.mappers.answer;

import com.example.softarex_demo_project.dto.question.answer.DateAnswerEntityDto;
import com.example.softarex_demo_project.model.question.DateAnswerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface is a mapper for DateAnswerEntity and DateAnswerEntityDto.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Mapper
public interface DateAnswerEntityMapper {
    DateAnswerEntityMapper INSTANCE = Mappers.getMapper(DateAnswerEntityMapper.class);

    @Mapping(source = "answerEntity", target = ".")
    DateAnswerEntityDto entityToDto(DateAnswerEntity answerEntity);

    @Mapping(source = "dto", target = ".")
    DateAnswerEntity dtoToEntity(DateAnswerEntityDto dto);
}
