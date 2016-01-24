/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.m2dl.helloandroid.wastelocator.backend.apis;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.users.User;
import com.m2dl.helloandroid.wastelocator.backend.Constants;
import com.m2dl.helloandroid.wastelocator.backend.MyBean;
import com.m2dl.helloandroid.wastelocator.backend.models.UserAccount;

import javax.inject.Named;

import static com.m2dl.helloandroid.wastelocator.backend.OfyService.ofy;

/**
 * Endpoint class for Users
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
        resource = "users"
)
public class UserEndpoint {
    @ApiMethod(httpMethod = "GET")
    public UserAccount connect(@Named("username") String username) {
        UserAccount account = ofy().load().type(UserAccount.class).filter("username", username).first().now();

        if (account == null) {
            account = new UserAccount();
            account.setUsername(username);
            ofy().save().entity(account).now();
        }

        return account;
    }
}
