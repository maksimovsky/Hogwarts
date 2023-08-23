package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentServiceImpl service;
    private final AvatarRepository repository;

    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(StudentServiceImpl service, AvatarRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Override
    public void uploadAvatar(Integer studentId, MultipartFile file) throws IOException {
        logger.debug("Uploading avatar for student with id {}", studentId);
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
        logger.debug("Getting extension of file {}", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public Avatar findAvatar(Integer studentId) {
        logger.debug("Getting avatar of student with id {}", studentId);
        return repository.findByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public Collection<Avatar> getAll(int pageNumber, int pageSize) {
        logger.debug("Getting all avatars on page {} with page size {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return repository.findAll(pageRequest).getContent();
    }
}
