package com.gerquestions.gerquestions.services;

import com.gerquestions.gerquestions.dao.AppUserRepository;
import com.gerquestions.gerquestions.dto.AppUserDTO;
import com.gerquestions.gerquestions.entities.AppUser;
import com.gerquestions.gerquestions.entities.GeneratedQuestions;
import com.gerquestions.gerquestions.exceptions.UserAlreadyExistsException;
import com.gerquestions.gerquestions.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private AppUserRepository<AppUser, String> appUserRepository;

    public AppUserService(AppUserRepository<AppUser, String> userRepository) {
        super();
        this.appUserRepository = userRepository;
    }

    public void addAppUser(AppUser appUser) throws UserAlreadyExistsException {
        Optional<AppUser> userOp = appUserRepository.findById(appUser.getEmail());
        if(!userOp.isPresent())
            appUserRepository.save(appUser);
        else
            throw new UserAlreadyExistsException("Email already present in database: " + userOp.get().getEmail());
    }

    public List<AppUserDTO> getUsers() {
        List<AppUser> users = appUserRepository.findAll();
        List<AppUserDTO> usersDTO = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            AppUser user = users.get(i);
            List<Long> questionIds = new ArrayList<>();
            for (GeneratedQuestions q : user.getGeneratedQuestions()) {
                questionIds.add(q.getId());
            }
            AppUserDTO appUserDTO = new AppUserDTO(user.getEmail(), user.getName(), user.getPassword(), questionIds);
            usersDTO.add(appUserDTO);
        }


        return usersDTO;
    }

    public AppUser searchByEmail(String email) throws UserNotFoundException {
        Optional<AppUser> user = appUserRepository.findById(email);

        if(!user.isPresent()) {
            throw new UserNotFoundException("Email not found in database.");
        }

        return user.get();
    }
}
