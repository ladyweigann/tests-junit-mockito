package br.com.dicasdeumdev.api.resources;

import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.services.UserService;
import br.com.dicasdeumdev.api.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    public static final String ID = "/{id}";
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(ID)
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {

        List<UserDTO> usersDTO = userService.findAll().stream()
                .map(user -> mapper.map(user, UserDTO.class)).toList();

        return ResponseEntity.ok(usersDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(userService.create(obj).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO obj){
        obj.setId(id);
        User user = userService.update(obj);
        return ResponseEntity.ok().body(mapper.map(user, UserDTO.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
