package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class CommentDtoMapper {
    public static CommentDto doMap(Comment comment) {
        return new CommentDto(
            comment.getId(),
            comment.getText(),
            comment
                .getUser()
                .getName(),
            comment.getCreated());
    }

    public static Comment toEntity(CommentDto commentDto, User user, Item item) {

        Comment comment = new Comment();
        comment.setText(commentDto.text());
        comment.setUser(user);
        comment.setItem(item);
        comment.setCreated(commentDto.created());
        comment.setId(commentDto.id());
        return comment;
    }
}
