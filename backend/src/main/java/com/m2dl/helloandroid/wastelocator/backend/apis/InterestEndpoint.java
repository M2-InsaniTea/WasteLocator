package com.m2dl.helloandroid.wastelocator.backend.apis;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.m2dl.helloandroid.wastelocator.backend.Constants;
import com.m2dl.helloandroid.wastelocator.backend.beans.ListInterestBean;
import com.m2dl.helloandroid.wastelocator.backend.models.Interest;
import com.m2dl.helloandroid.wastelocator.backend.models.Tag;

import java.util.List;

import javax.inject.Named;

import static com.m2dl.helloandroid.wastelocator.backend.OfyService.ofy;

/**
 * Created by flemoal on 25/01/16.
 */
@Api(
        name = "wasteApi",
        version = "v2",
        namespace = @ApiNamespace(
                ownerDomain = Constants.API_OWNER,
                ownerName = Constants.API_OWNER,
                packagePath = Constants.API_PACKAGE_PATH
        )
)
@ApiClass(
        resource = "interests"
)
public class InterestEndpoint {
    @ApiMethod(httpMethod = "GET")
    public final ListInterestBean list() {
        ListInterestBean bean = new ListInterestBean();
        bean.setInterests(ofy().load().type(Interest.class).list());
        bean.setTags(ofy().load().type(Tag.class).list());
        return bean;
    }

    @ApiMethod(httpMethod = "GET")
    public final Interest detail(@Named("id") Long id) {
        Interest interest = ofy().load().type(Interest.class).id(id).now();
        return interest;
    }
}
