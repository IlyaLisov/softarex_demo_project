package com.example.softarex_demo_project.dto.mappers;

import com.example.softarex_demo_project.dto.question.AnswerEntityDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.model.question.AnswerEntity;
import com.example.softarex_demo_project.model.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface is a mapper for Question and QuestionDto.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(source = "question", target = ".")
    QuestionDto questionToQuestionDto(Question question);

    @Mapping(source = "dto", target = ".")
    Question questionDtoToQuestion(QuestionDto dto);

    @Mapping(source = "answerEntity", target = ".")
    AnswerEntityDto answerEntityToAnswerEntityDto(AnswerEntity answerEntity);

    @Mapping(source = "dto", target = ".")
    AnswerEntity answerEntityDtoToAnswerEntity(AnswerEntityDto dto);
}
