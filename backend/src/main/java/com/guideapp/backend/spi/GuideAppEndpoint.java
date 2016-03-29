package com.guideapp.backend.spi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
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
import com.guideapp.backend.service.subcategory.SubCategoryService;
import com.guideapp.backend.service.subcategory.SubCategoryServiceImpl;
import com.guideapp.backend.util.Constants;
import com.google.appengine.api.users.User;

import java.util.List;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "guideAppApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.guideapp.com",
                ownerName = "backend.guideapp.com",
                packagePath = ""
        ),
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)

public class GuideAppEndpoint {
    private final LocalService mLocalService;
    private final CityService mCityService;
    private final CategoryService mCategoryService;
    private final SubCategoryService mSubCategoryService;


    /**
     * Configure the endpoint.
     */
    public GuideAppEndpoint() {
        mLocalService = new LocalServiceImpl();
        mCityService = new CityServiceImpl();
        mCategoryService = new CategoryServiceImpl();
        mSubCategoryService = new SubCategoryServiceImpl();
    }

    /**
     * Returns a list of locals by filters
     *
     * @param search     generic
     * @param idCity     the id of the city
     * @param idCategory the id of the category.
     * @return the list of locals by filter
     */
    @ApiMethod(name = "getLocals", path = "local", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Local> getLocals(@Nullable @Named("search") String search,
                                 @Nullable @Named("idCity") Long idCity,
                                 @Nullable @Named("idCategory") Long idCategory,
                                 @Nullable @Named("idSubCategory") Long[] subCategories)
            throws ConflictException {

        return mLocalService.list(idCity, idCategory, subCategories);
    }


    /**
     * Returns a Local object with the given id.
     *
     * @param id the Long representation of the local Key.
     * @return Local object.
     * @throws NotFoundException when there is no Local with the given id.
     */
    @ApiMethod(name = "getLocal", path = "local/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Local getLocal(@Named("id") Long id)
            throws NotFoundException {

        return mLocalService.getById(id);
    }


    /**
     * Creates a Local object
     *
     * @param user  An user who invokes this method, null when the user is not signed in.
     * @param local A Local object representing user's inputs
     * @throws ConflictException     when there is a error in Local object
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "insertLocal", path = "local", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertLocal(final User user, Local local)
            throws ConflictException, UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mLocalService.insert(local);
    }


    /**
     * Updates a Local object
     *
     * @param user  An user who invokes this method, null when the user is not signed in.
     * @param local A Local object representing user's inputs
     * @throws ConflictException     when there is a error in Local object
     * @throws NotFoundException     when there is no Local with the given id
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "updateLocal", path = "local", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateLocal(final User user, Local local)
            throws NotFoundException, ConflictException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mLocalService.update(local);
    }


    /**
     * Removes a Local object
     *
     * @param user An user who invokes this method, null when the user is not signed in.
     * @param id   the Long representation of the local Key.
     * @throws NotFoundException     when there is a error in Local object
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "removeLocal", path = "local/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeLocal(final User user, @Named("id") Long id)
            throws NotFoundException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mLocalService.remove(id);
    }


    /**
     * Returns a list of cities by filters
     *
     * @param search text generic
     * @return the list of cities by filter
     */
    @ApiMethod(name = "getCities", path = "city", httpMethod = ApiMethod.HttpMethod.GET)
    public List<City> getCities(@Nullable @Named("search") String search) {

        if (search == null || search.isEmpty()) {
            return mCityService.list();
        } else {
            return mCityService.list(search);
        }
    }


    /**
     * Returns a City object with the given id..
     *
     * @param id the Long representation of the City Key.
     * @return City object.
     * @throws NotFoundException when there is no City with the given id.
     */
    @ApiMethod(name = "getCity", path = "city/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public City getCity(@Named("id") Long id)
            throws NotFoundException {

        return mCityService.getById(id);
    }


    /**
     * Creates a City object
     *
     * @param user An user who invokes this method, null when the user is not signed in.
     * @param city A City object representing user's inputs
     * @throws ConflictException     when there is a error in City object
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "insertCity", path = "city", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertCity(final User user, City city)
            throws ConflictException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mCityService.insert(city);
    }


    /**
     * Updates a City object
     *
     * @param user An user who invokes this method, null when the user is not signed in.
     * @param city A City object representing user's inputs
     * @throws ConflictException     when there is a error in City object
     * @throws NotFoundException     when there is no City with the given id
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "updateCity", path = "city", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateCity(final User user, City city)
            throws NotFoundException, ConflictException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mCityService.update(city);
    }


    /**
     * Removes a City object
     *
     * @param user An user who invokes this method, null when the user is not signed in.
     * @param id   the Long representation of the City Key.
     * @throws NotFoundException     when there is a error in City object
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "removeCity", path = "city/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeCity(final User user, @Named("id") Long id)
            throws NotFoundException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mCityService.remove(id);
    }


    /**
     * Returns a list of categories by filters
     *
     * @param search text generic
     * @return the list of categories by filter
     */
    @ApiMethod(name = "getCategories", path = "category", httpMethod = "GET")
    public List<Category> getCategories(@Nullable @Named("search") String search) {

        if (search == null || search.isEmpty()) {
            return mCategoryService.list();
        } else {
            return mCategoryService.list(search);
        }
    }


    /**
     * Returns a Category object with the given id..
     *
     * @param id the Long representation of the Category Key.
     * @return Category object.
     * @throws NotFoundException when there is no Category with the given id.
     */
    @ApiMethod(name = "getCategory", path = "category/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Category getCategory(@Named("id") Long id)
            throws NotFoundException {

        return mCategoryService.getById(id);
    }


    /**
     * Creates a Category object
     *
     * @param user     An User who invokes this method, null when the user is not signed in.
     * @param category A Category object representing user's inputs
     * @throws ConflictException     when there is a error in Category object
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "insertCategory", path = "category", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertCategory(final User user, Category category)
            throws ConflictException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mCategoryService.insert(category);
    }


    /**
     * Updates a City object
     *
     * @param user     An user who invokes this method, null when the user is not signed in.
     * @param category A Category object representing user's inputs
     * @throws ConflictException     when there is a error in Category object
     * @throws NotFoundException     when there is no Category with the given id
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "updateCategory", path = "category", httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateCategory(final User user, Category category)
            throws NotFoundException, ConflictException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }
        mCategoryService.update(category);
    }


    /**
     * Removes a Category object
     *
     * @param user An user who invokes this method, null when the user is not signed in.
     * @param id   the Long representation of the Category Key.
     * @throws NotFoundException     when there is a error in Category object
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "removeCategory",
            path = "category/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeCategory(final User user, @Named("id") Long id)
            throws NotFoundException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mCategoryService.remove(id);
    }


    /**
     * Returns a list of sub-categories by Category key
     *
     * @param idCategory the Long representation of the Category Key.
     * @return the list of sub-categories
     */
    @ApiMethod(name = "getSubCategories", path = "subcategory",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<SubCategory> getSubCategories(@Nullable @Named("idCategory") Long idCategory) {

        if (idCategory == null || idCategory == 0) {
            return mSubCategoryService.list();
        } else {
            return mSubCategoryService.list(idCategory);
        }
    }


    /**
     * Returns a SubCategory object with the given id..
     *
     * @param id the Long representation of the SubCategory Key.
     * @return SubCategory object.
     * @throws NotFoundException when there is no SubCategory with the given id.
     */
    @ApiMethod(name = "getSubCategory",
            path = "subcategory/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public SubCategory getSubCategory(@Named("id") Long id)
            throws NotFoundException {

        return mSubCategoryService.getById(id);
    }


    /**
     * Creates a SubCategory object
     *
     * @param user        An user who invokes this method, null when the user is not signed in.
     * @param subCategory A SubCategory object representing user's inputs
     * @throws ConflictException     when there is a error in SubCategory object
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "insertSubCategory",
            path = "subcategory",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insertSubCategory(final User user, SubCategory subCategory)
            throws ConflictException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mSubCategoryService.insert(subCategory);
    }


    /**
     * Updates a SubCategory object
     *
     * @param user        An user who invokes this method, null when the user is not signed in.
     * @param subCategory A SubCategory object representing user's inputs
     * @throws ConflictException     when there is a error in SubCategory object
     * @throws NotFoundException     when there is no SubCategory with the given id
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "updateSubCategory",
            path = "subcategory",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public void updateSubCategory(final User user, SubCategory subCategory)
            throws NotFoundException, ConflictException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mSubCategoryService.update(subCategory);
    }


    /**
     * Removes a SubCategory object
     *
     * @param user An user who invokes this method, null when the user is not signed in.
     * @param id   the Long representation of the SubCategory Key.
     * @throws NotFoundException     when there is a error in SubCategory object
     * @throws UnauthorizedException when the user is not signed in
     */
    @ApiMethod(name = "removeSubCategory",
            path = "subcategory/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeSubCategory(final User user, @Named("id") Long id)
            throws NotFoundException, UnauthorizedException {

        if (user == null) {
            throw new UnauthorizedException(Constants.AUTHORIZATION_REQUIRED);
        }

        mSubCategoryService.remove(id);
    }
}
