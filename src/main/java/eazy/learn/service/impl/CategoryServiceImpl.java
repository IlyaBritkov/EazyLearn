package eazy.learn.service.impl;

import eazy.learn.repository.CategoryRepository;
import eazy.learn.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return false;
    }
}
