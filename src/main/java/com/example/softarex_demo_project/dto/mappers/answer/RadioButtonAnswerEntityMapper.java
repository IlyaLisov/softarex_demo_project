package com.example.softarex_demo_project.dto.mappers.answer;

import com.example.softarex_demo_project.dto.question.answer.RadioButtonAnswerEntityDto;
import com.example.softarex_demo_project.model.question.RadioButtonAnswerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface is a mapper for RadioButtonAnswerEntity and RadioButtonAnswerEntityDto.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Mapper
public interface RadioButtonAnswerEntityMapper {
    RadioButtonAnswerEntityMapper INSTANCE = Mappers.getMapper(RadioButtonAnswerEntityMapper.class);

    @Mapping(source = "answerEntity", target = ".")
    RadioButtonAnswerEntityDto entityToDto(RadioButtonAnswerEntity answerEntity);

    @Mapping(source = "dto", target = ".")
    RadioButtonAnswerEntity dtoToEntity(RadioButtonAnswerEntityDto dto);
}
