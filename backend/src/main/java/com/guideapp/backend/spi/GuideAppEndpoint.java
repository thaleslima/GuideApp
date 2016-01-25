/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.guideapp.backend.spi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.Category;
import com.guideapp.backend.entity.City;
import com.guideapp.backend.entity.Local;
import com.guideapp.backend.entity.SubCategory;
import com.guideapp.backend.service.category.CategoryService;
import com.guideapp.backend.service.category.CategoryServiceImpl;
import com.guideapp.backend.service.city.CityService;
import com.guideapp.backend.service.city.CityServiceImpl;
import com.guideapp.backend.service.local.LocalService;
import com.guideapp.backend.service.local.LocalServiceImpl;
import com.guideapp.backend.service.subCategory.SubCategoryService;
import com.guideapp.backend.service.subCategory.SubCategoryServiceImpl;

import java.util.List;

/** An endpoint class we are exposing */
@Api(
  name = "guideAppApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.guideapp.com",
    ownerName = "backend.guideapp.com",
    packagePath=""
  )
)
public class GuideAppEndpoint {
    LocalService localService;
    CityService cityService;
    CategoryService categoryService;
    SubCategoryService subCategoryService;

    public GuideAppEndpoint()
    {
        localService = new LocalServiceImpl();
        cityService = new CityServiceImpl();
        categoryService = new CategoryServiceImpl();
        subCategoryService = new SubCategoryServiceImpl();
    }



    @ApiMethod(name = "getLocals", path = "local", httpMethod = "GET")
    public List<Local> getLocals(@Nullable @Named("search") String search) throws NotFoundException {
        if(search == null || search.isEmpty())
            return localService.list();
        else
            return localService.list(search);
    }

    @ApiMethod(name = "getLocal", path = "local/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Local getLocal(@Named("id") Long id) throws NotFoundException {
        return localService.getById(id);
    }

    @ApiMethod(name = "insertLocal", path = "local", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertLocal(Local local) throws ConflictException, NotFoundException {
        localService.insert(local);
    }

    @ApiMethod(name = "updateLocal", path = "local", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateLocal(Local local) throws NotFoundException, ConflictException {
        localService.update(local);
    }

    @ApiMethod(name = "removeLocal", path = "local/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeLocal(@Named("id") Long id) throws NotFoundException, ConflictException {
        localService.remove(id);
    }





    @ApiMethod(name = "getCities", path = "city", httpMethod = "GET")
    public List<City> getCities(@Nullable @Named("search") String search) throws NotFoundException {
        if(search == null || search.isEmpty())
            return cityService.list();
        else
            return cityService.list(search);
    }

    @ApiMethod(name = "getCity", path = "city/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public City getCity(@Named("id") Long id) throws NotFoundException {
        return cityService.getById(id);
    }

    @ApiMethod(name = "insertCity", path = "city", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertCity(City city) throws ConflictException, NotFoundException {
        cityService.insert(city);
    }

    @ApiMethod(name = "updateCity", path = "city", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateCity(City city) throws NotFoundException, ConflictException {
        cityService.update(city);
    }

    @ApiMethod(name = "removeCity", path = "city/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeCity(@Named("id") Long id) throws NotFoundException, ConflictException {
        cityService.remove(id);
    }






    @ApiMethod(name = "getCategories", path = "category", httpMethod = "GET")
    public List<Category> getCategories(@Nullable @Named("search") String search) throws NotFoundException {
        if(search == null || search.isEmpty())
            return categoryService.list();
        else
            return categoryService.list(search);
    }

    @ApiMethod(name = "getCategory", path = "category/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Category getCategory(@Named("id") Long id) throws NotFoundException {
        return categoryService.getById(id);
    }

    @ApiMethod(name = "insertCategory", path = "category", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertCategory(Category category) throws ConflictException, NotFoundException {
        categoryService.insert(category);
    }

    @ApiMethod(name = "updateCategory", path = "category", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateCategory(Category category) throws NotFoundException, ConflictException {
        categoryService.update(category);
    }

    @ApiMethod(name = "removeCategory", path = "category/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeCategory(@Named("id") Long id) throws NotFoundException, ConflictException {
        categoryService.remove(id);
    }





    @ApiMethod(name = "getSubCategories", path = "subcategory", httpMethod = "GET")
    public List<SubCategory> getSubCategories(@Nullable @Named("search") String search) throws NotFoundException {
        if(search == null || search.isEmpty())
            return subCategoryService.list();
        else
            return subCategoryService.list(search);
    }

    @ApiMethod(name = "getSubCategory", path = "subcategory/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public SubCategory getSubCategory(@Named("id") Long id) throws NotFoundException {
        return subCategoryService.getById(id);
    }

    @ApiMethod(name = "insertSubCategory", path = "subcategory", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertSubCategory(SubCategory subCategory) throws ConflictException, NotFoundException {
        subCategoryService.insert(subCategory);
    }

    @ApiMethod(name = "updateSubCategory", path = "subcategory", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateSubCategory(SubCategory subCategory) throws NotFoundException, ConflictException {
        subCategoryService.update(subCategory);
    }

    @ApiMethod(name = "removeSubCategory", path = "subcategory/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeSubCategory(@Named("id") Long id) throws NotFoundException, ConflictException {
        subCategoryService.remove(id);
    }
}
