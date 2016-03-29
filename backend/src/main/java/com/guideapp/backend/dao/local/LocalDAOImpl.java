package com.guideapp.backend.dao.local;

import com.google.appengine.api.datastore.Query;
import com.guideapp.backend.dao.generic.ObjectifyGenericDAO;
import com.guideapp.backend.entity.Local;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.guideapp.backend.util.OfyService.ofy;

/**
 * Created by thales on 1/24/16.
 */
public class LocalDAOImpl extends ObjectifyGenericDAO<Local> implements LocalDAO {


    @Override
    public List<Local> listByFilters(Long idCity, Long idCategory, Long[] subCategories){
        if(idCity == null) {
            return null;
        }

        if(idCategory != null) {
            com.googlecode.objectify.cmd.Query<Local> query = ofy().load().type(mClazz);
            Collection<Query.Filter> filtersAnd = new ArrayList<>();
            Collection<Query.Filter> filtersOr = new ArrayList<>();

            filtersAnd.add(new Query.FilterPredicate(
                    "idCity",
                    Query.FilterOperator.EQUAL,
                    idCity));

            filtersAnd.add(new Query.FilterPredicate(
                    "idCategories",
                    Query.FilterOperator.EQUAL,
                    idCategory));

            if(subCategories != null && subCategories.length > 0) {
                int size = subCategories.length;

                if (size == 1) {
                    filtersAnd.add(new Query.FilterPredicate(
                            "idSubCategories",
                            Query.FilterOperator.EQUAL,
                            subCategories[0]));
                } else {
                    for (Long subCategory : subCategories) {
                        filtersOr.add(new Query.FilterPredicate(
                                "idSubCategories",
                                Query.FilterOperator.EQUAL,
                                subCategory));
                    }
                }
            }
            Query.Filter filterAnd = Query.CompositeFilterOperator.and(filtersAnd);


            if(filtersOr.size() > 0) {
                Query.Filter filterOr = Query.CompositeFilterOperator.or(filtersOr);

                filterAnd = Query.CompositeFilterOperator.and(filterAnd, filterOr);
            }


            return query.filter(filterAnd).list();
        }

        return listByProperty("idCity", idCity);
    }
}
