package com.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserDocumentRepository userDocumentRepository;

    public UserController(UserDocumentRepository userDocumentRepository) {
        this.userDocumentRepository = userDocumentRepository;
    }

    @GetMapping
    public Page<UserDocument> findUsers(){
        return userDocumentRepository.findAll(PageRequest.of(0, 10));
    }

    @GetMapping("/{id}")
    public UserDocument findUserById(@PathVariable String id){
        return userDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다. "));
    }

    @PostMapping
    public UserDocument createUser(@RequestBody UserCreateRequestDto requestDto){
        UserDocument user = new UserDocument(
                requestDto.getId(), requestDto.getName(), requestDto.getAge(), requestDto.getIsActive()
        );
        return userDocumentRepository.save(user);
    }

    @PutMapping("/{id}")
    public UserDocument updateUser(@PathVariable String id, @RequestBody UserUpdateRequestDto updateDto){
        UserDocument existingUser = userDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        existingUser.setAge(updateDto.getAge());
        existingUser.setName(updateDto.getName());
        existingUser.setIsActive(updateDto.getIsActive());

        return userDocumentRepository.save(existingUser);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        UserDocument user = userDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));
        userDocumentRepository.delete(user);
    }

}
