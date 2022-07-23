package com.example.softarex_demo_project.dto.mappers.answer;

import com.example.softarex_demo_project.dto.question.answer.SingleLineAnswerEntityDto;
import com.example.softarex_demo_project.model.question.SingleLineAnswerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface is a mapper for SingleLineAnswerEntity and SingleLineAnswerEntityDto.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Mapper
public interface SingleLineAnswerEntityMapper {
    SingleLineAnswerEntityMapper INSTANCE = Mappers.getMapper(SingleLineAnswerEntityMapper.class);

    @Mapping(source = "answerEntity", target = ".")
    SingleLineAnswerEntityDto entityToDto(SingleLineAnswerEntity answerEntity);

    @Mapping(source = "dto", target = ".")
    SingleLineAnswerEntity dtoToEntity(SingleLineAnswerEntityDto dto);
}
