package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {
    void uploadAvatar(Integer studentId, MultipartFile file) throws IOException;

    Avatar findAvatar(Integer studentId);

    Collection<Avatar> getAll(int pageNumber, int pageSize);
}
