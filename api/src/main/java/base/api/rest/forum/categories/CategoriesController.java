package base.api.rest.forum.categories;

import base.api.domain.forum.categories.CategoryEntity;
import base.api.logging.LogExecutionTime;
import base.api.rest.forum.categories.dto.CategoryAdditionalDto;
import base.api.rest.forum.categories.dto.CategoryDataDto;
import base.api.rest.forum.categories.dto.CategoryRequestDto;
import base.api.rest.forum.categories.dto.CategoryRespondDto;
import base.api.services.forum.CategoryService;
import base.api.utils.UtilsUid;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forum/categories")
public class CategoriesController {

  private final CategoryService categoryService;
  private CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

  public CategoriesController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  /**
   * GET / : find all categories
   *
   * @return CategoryRespondDto list of all categories
   */
  @LogExecutionTime
  @GetMapping()
  public ResponseEntity<List<CategoryRespondDto>> findAllCategories() {

    List<CategoryEntity> categoryEntities = categoryService.findAll();
    List<CategoryRespondDto> categories =
        categoryEntities.stream()
            .map(
                categoryEntity -> {
                  CategoryDataDto categoryDataDto = categoryMapper.entityToDataDto(categoryEntity);
                  CategoryAdditionalDto categoryAdditionalDto = categoryService.setAdditionalData(categoryEntity);
                  return new CategoryRespondDto(categoryDataDto, categoryAdditionalDto);
                })
            .collect(Collectors.toList());

    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  /**
   * GET / : find category by uid
   *
   * @return CategoryRespondDto object
   */
  @LogExecutionTime
  @GetMapping("/{categoryUid}")
  public ResponseEntity<CategoryDataDto> findCategoryByUid(
      @PathVariable("categoryUid") String categoryUid) {

    Optional<CategoryEntity> categoryEntity = categoryService.findByUid(categoryUid);

    if (categoryEntity.isPresent()) {
      CategoryDataDto categoryDataDto =
          categoryMapper.entityToDataDto(categoryEntity.get());
      return ResponseEntity.ok(categoryDataDto);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /**
   * POST / : add category
   *
   * @param categoryRequestDto object with category data
   * @return uid String
   */
  @LogExecutionTime
  @PostMapping
  public ResponseEntity<CategoryDataDto> addCategory(
      @RequestBody CategoryRequestDto categoryRequestDto) {

    CategoryEntity categoryEntity = categoryMapper.dtoToEntity(categoryRequestDto);
    Optional<CategoryEntity> category = categoryService.add(categoryEntity);

    if (category.isPresent()) {
      CategoryDataDto categoryDataDto = categoryMapper.entityToDataDto(category.get());
      return new ResponseEntity<>(categoryDataDto, HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /**
   * PUT / : edit category
   *
   * @param categoryUid category Uid
   * @param categoryRequestDto object with category data
   * @return void
   */
  @LogExecutionTime
  @PutMapping("/{categoryUid}")
  public ResponseEntity<CategoryRespondDto> editCategory(
      @PathVariable("categoryUid") String categoryUid,
      @RequestBody CategoryRequestDto categoryRequestDto) {
    CategoryEntity categoryEntity = categoryMapper.dtoToEntity(categoryRequestDto);
    categoryEntity.setUid(UtilsUid.uidDecode(categoryUid));
    Optional<CategoryEntity> editCategory = categoryService.edit(categoryEntity);
    if (editCategory.isPresent()) {
      CategoryDataDto categoryDataDto = categoryMapper.entityToDataDto(editCategory.get());
      CategoryAdditionalDto categoryAdditionalDto = categoryService.setAdditionalData(editCategory.get());
      CategoryRespondDto categoryRespondDto =  new CategoryRespondDto(categoryDataDto, categoryAdditionalDto);
      return new ResponseEntity<>(categoryRespondDto, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /**
   * DELETE / : delete category
   *
   * @param categoryUid category Uid
   * @return void
   */
  @LogExecutionTime
  @DeleteMapping("/{categoryUid}")
  public ResponseEntity deleteCategory(@PathVariable("categoryUid") String categoryUid) {
    categoryService.delete(categoryUid);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  //    /**
  //     * PATCH / : activate/deactivate category
  //     *
  //     * @param categoryUid category Uid
  //     * @return void
  //     */
  //    @LogExecutionTime
  //    @PatchMapping("/{categoryUid}")
  //    public ResponseEntity changeStatusCategory(@PathVariable("categoryUid") String categoryUid)
  // {
  //      categoryService.changeStatus(categoryUid);
  //      return new ResponseEntity<>(HttpStatus.OK);
  //    }
}
