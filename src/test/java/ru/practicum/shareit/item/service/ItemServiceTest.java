package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.StatusOfAvailable;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User user;
    private Item item;
    private ItemDto itemDto;
    private Booking booking;
    private Comment comment;
    private CommentDto commentDto;
    private ItemDtoMapper itemDtoMapper;

    @Test
    void addNewComment() {

    }

    @Test
    void getItemById() {
    }

    @Test
    void getItemsBySearchRequest() {
    }

    @Test
    void getItemsOfOwner() {
    }

    @BeforeEach
    void setUp() {

        booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.WAITING);

        user = new User();
        user.setId(1L);
        user.setName("Andrey");
        user.setEmail("burdak24@mail.ru");

        item = new Item();
        item.setId(1L);
        item.setUser(user);
        item.setName("Saw");
        item.setDescription("Good Saw Mockita");
        item.setAvailable(true);
        item.setStatus(StatusOfAvailable.AVAILABLE);

        itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setUserId(user.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setStatus(item.getStatus());
        itemDto.setAvailable(item.getAvailable());

        booking.setItem(item);
        booking.setUser(user);
        booking.setStatus(BookingStatus.WAITING);
        booking.setId(1L);

        comment = new Comment();
        comment.setText("Good seller");
        comment.setCreated(LocalDateTime.now());
        comment.setId(1L);
        comment.setItem(item);
        comment.setUser(user);

        commentDto = new CommentDto(
            comment.getId(),
            comment.getText(),
            comment
                .getUser()
                .getName(),
            comment.getCreated()
        );

        item.setComments(new ArrayList<>());
        item
            .getComments()
            .add(comment);
        item.setBookings(new ArrayList<>());
        item
            .getBookings()
            .add(booking);

        itemDto.setComments(new ArrayList<>());
        itemDto
            .getComments()
            .add(commentDto);

    }

    @Test
    void shouldAddNewItem_thenAnswerUses() {
        when(userRepository.findById(user.getId()))
            .thenReturn(Optional.of(user));

        when(itemRepository.save(any(Item.class)))
            .thenAnswer(invocation -> {
                Item savedItem = invocation.getArgument(0);

                //имитируем работу БД
                savedItem.setId(100L);

                return savedItem;
            });

        ItemDto result = itemService.addNewItem(user.getId(), item);

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(100L, result.getId()),
            () -> assertEquals("Saw", result.getName()),
            () -> assertEquals("Good Saw Mockita", result.getDescription()),
            () -> assertEquals(user.getId(), result.getUserId())

        );

        verify(userRepository).findById(user.getId());
        verify(itemRepository).save(any(Item.class));

    }

    @Test
    void shouldReturnItemById() {
        when(itemRepository.findById(item.getId()))
            .thenReturn(Optional.of(item));

        ItemDto result = itemService.getItemById(user.getId(), item.getId());

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(1L, result.getId()),
            () -> assertEquals("Saw", result.getName()),
            () -> verify(itemRepository).findById(1L),
            () -> assertEquals(itemDto.getId(), result.getId()),
            () -> assertEquals(itemDto.getName(), result.getName())

        );

    }

    @Test
    void shouldThrowExceptionWhenItemNotFound() {
        when(itemRepository.findById(1L))
            .thenThrow(new ItemNotFoundException(String.format("Вещь с id %d не найдена", 1L)));
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> itemService.getItemById(user.getId(), 1L));
        assertEquals("Вещь с id 1 не найдена", exception.getMessage());

        verify(itemRepository).findById(1L);
    }

    @Test
    void updateItem() {
    }

}