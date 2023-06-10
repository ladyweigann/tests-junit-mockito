package br.com.dicasdeumdev.api.services.impl;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.repositories.UserRepository;
import br.com.dicasdeumdev.api.services.exceptions.DataIntegratyViolationException;
import br.com.dicasdeumdev.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "Lari";
    public static final String EMAIL = "lari@gmail";
    public static final String PASSWORD = "123";

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void shouldReturnAnUserInstanceWhenFindById() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try{
            service.findById(ID);
        }catch(Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Objeto não encontrado", e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(0).getClass());

        assertEquals(user.getName(), response.get(0).getName());
        assertEquals(user.getId(), response.get(0).getId());
        assertEquals(user.getEmail(), response.get(0).getEmail());
        assertEquals(user.getPassword(), response.get(0).getPassword());

    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

       try{
           optionalUser.get().setId(2);
           service.create(userDTO);
       }catch(Exception e) {
           assertEquals(DataIntegratyViolationException.class, e.getClass());
       }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenThrow(new DataIntegratyViolationException("Email já existente"));

        try{
            service.update(userDTO);
        }catch(Exception e) {
            assertEquals(DataIntegratyViolationException.class, e.getClass());
        }
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());

        service.delete(ID);

        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenDeleteThenReturnObjectNotFoundException() {
        when(repository.findById(any())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try{
            service.delete(ID);
        }catch (Exception e) {
            assertEquals(ObjectNotFoundException.class, e.getClass());
        }
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}