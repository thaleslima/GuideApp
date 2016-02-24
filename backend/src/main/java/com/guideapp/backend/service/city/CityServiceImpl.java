package com.guideapp.backend.service.city;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.dao.city.CityDAO;
import com.guideapp.backend.dao.city.CityDAOImpl;
import com.guideapp.backend.entity.City;
import com.guideapp.backend.util.ValidationUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public class CityServiceImpl implements CityService {

    private final CityDAO mCityDAO;

    /**
     * Configure the local service.
     */
    public CityServiceImpl() {
        this.mCityDAO = new CityDAOImpl();
    }

    @Override
    public List<City> list() {
        return mCityDAO.listAll();
    }

    @Override
    public List<City> list(String search) {
        return mCityDAO.listByProperty("name", search);
    }

    @Override
    public City getById(Long id) throws NotFoundException {
        City city = mCityDAO.getByKey(id);

        if (city == null) {
            throw new NotFoundException("City not found");
        }

        return city;
    }

    @Override
    public void insert(City city) throws ConflictException {
        if (city == null) {
            throw new ConflictException("Cidade não informada.");
        }

        if (ValidationUtil.nullOrEmpty(city.getName())) {
            throw new ConflictException("Nome da cidade não informado.");
        }

        if (ValidationUtil.nullOrEmpty(city.getUf())) {
            throw new ConflictException("Estado não informado.");
        }

        city.setTimestamp(new Date().getTime());
        mCityDAO.insert(city);
    }

    @Override
    public void update(City city) throws ConflictException, NotFoundException {

        if (city == null) {
            throw new ConflictException("Cidade não informada.");
        }

        if (ValidationUtil.nullOrEmpty(city.getName())) {
            throw new ConflictException("Nome da cidade não informado.");
        }

        if (ValidationUtil.nullOrEmpty(city.getUf())) {
            throw new ConflictException("Estado não informado.");
        }

        if (ValidationUtil.nullOrEmpty(city.getId())) {
            throw new ConflictException("Id não informado.");
        }

        City c = mCityDAO.getByKey(city.getId());

        if (c == null) {
            throw new NotFoundException("Cidade não encontrada.");
        }

        city.setTimestamp(new Date().getTime());
        mCityDAO.insert(city);
    }

    @Override
    public void remove(Long id) throws NotFoundException {
        City city = mCityDAO.getByKey(id);

        if (city == null) {
            throw new NotFoundException("Cidade não encontrada.");
        }

        mCityDAO.delete(city);
    }
}
