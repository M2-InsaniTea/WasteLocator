package com.m2dl.helloandroid.wastelocator.backend.apis;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.m2dl.helloandroid.wastelocator.backend.Constants;
import com.m2dl.helloandroid.wastelocator.backend.models.Tag;
import com.m2dl.helloandroid.wastelocator.backend.models.UserAccount;

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
        resource = "tags"
)
public class TagEndpoint {
    @ApiMethod(httpMethod = "GET")
    public final List<Tag> list() {
        return ofy().load().type(Tag.class).list();
    }

    @ApiMethod(httpMethod = "GET")
    public final Tag detail(@Named("id") Long id) {
        Tag tag = ofy().load().type(Tag.class).id(id).now();
        return tag;
    }
}
