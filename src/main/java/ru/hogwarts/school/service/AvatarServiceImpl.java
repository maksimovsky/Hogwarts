package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentServiceImpl service;
    private final AvatarRepository repository;

    public AvatarServiceImpl(StudentServiceImpl service, AvatarRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Override
    public void uploadAvatar(Integer studentId, MultipartFile file) throws IOException {
        Student student = service.getStudentById(studentId);

        Path path = Path.of(avatarsDir, student.getName() + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(path);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        repository.save(avatar);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public Avatar findAvatar(Integer studentId) {
        return repository.findByStudentId(studentId).orElse(new Avatar());
    }
}
