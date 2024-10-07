package com.erkindilekci.booksapi.mapper;

import com.erkindilekci.booksapi.domain.Author;
import com.erkindilekci.booksapi.dto.AuthorDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<Author, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public Author mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}
