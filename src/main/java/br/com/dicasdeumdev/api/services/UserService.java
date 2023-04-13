package br.com.dicasdeumdev.api.services;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User create(UserDTO obj);

    User update(UserDTO obj);
}
