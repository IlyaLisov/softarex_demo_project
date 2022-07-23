package com.example.softarex_demo_project.dto.mappers.answer;

import com.example.softarex_demo_project.dto.question.answer.CheckboxAnswerEntityDto;
import com.example.softarex_demo_project.model.question.CheckboxAnswerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface is a mapper for CheckboxAnswerEntity and CheckboxAnswerEntityDto.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Mapper
public interface CheckboxAnswerEntityMapper {
    CheckboxAnswerEntityMapper INSTANCE = Mappers.getMapper(CheckboxAnswerEntityMapper.class);

    @Mapping(source = "answerEntity", target = ".")
    CheckboxAnswerEntityDto entityToDto(CheckboxAnswerEntity answerEntity);

    @Mapping(source = "dto", target = ".")
    CheckboxAnswerEntity dtoToEntity(CheckboxAnswerEntityDto dto);
}
