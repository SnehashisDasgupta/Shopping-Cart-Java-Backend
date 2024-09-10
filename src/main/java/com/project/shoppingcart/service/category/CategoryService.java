package com.project.shoppingcart.service.category;

import com.project.shoppingcart.exceptions.AlreadyExistsException;
import com.project.shoppingcart.exceptions.ResourceNotFoundException;
import com.project.shoppingcart.model.Category;
import com.project.shoppingcart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category) // Wrap the given category in an Optional
                .filter(c -> !categoryRepository.existsByName(c.getName())) // Check if the category name already exists
                .map(categoryRepository :: save) // If the category name doesn't exist, save the category
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists")); // If name exists, throw an exception
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)) // Find category by ID and wrap it in Optional
                .map(oldCategory -> {  // If category is found, map it for update
                    oldCategory.setName(category.getName()); // Update the name of the old category
                    return categoryRepository.save(oldCategory); // Save updated category to the repository
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!")); // Throw exception if category isn't found
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
               .ifPresentOrElse(categoryRepository::delete,
                        () -> {throw new ResourceNotFoundException("Category not found!");}
               );
    }
}
