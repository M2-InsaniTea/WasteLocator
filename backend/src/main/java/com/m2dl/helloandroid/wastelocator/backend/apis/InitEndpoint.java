package com.m2dl.helloandroid.wastelocator.backend.apis;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Key;
import com.m2dl.helloandroid.wastelocator.backend.Constants;
import com.m2dl.helloandroid.wastelocator.backend.beans.InitBean;
import com.m2dl.helloandroid.wastelocator.backend.models.Interest;
import com.m2dl.helloandroid.wastelocator.backend.models.Tag;
import com.m2dl.helloandroid.wastelocator.backend.models.UserAccount;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        resource = "init"
)
public class InitEndpoint {
    @ApiMethod(httpMethod = "PUT")
    public final InitBean populate() {
        List<String> result = new LinkedList<>();

        if (ofy().load().type(Tag.class).count() == 0) {
            List<Tag> tags = new LinkedList<>();

            Tag tagRecyclage = new Tag("Recyclage", "#04B431");
            Tag tagDegradation = new Tag("Dégradation", "#FE642E");
            Tag tagFuite = new Tag("Fuite d'eau", "#819FF7");

            Tag tagCarton = new Tag("Carton", "#FFAA1E");
            Tag tagPapier = new Tag("Papier", "#00CFFE");
            Tag tagPile = new Tag("Pile", "#9B2AFB");
            Tag tagTextile = new Tag("Textile", "#FF2E92");
            Tag tagVerre = new Tag("Verre", "#6DEA2C");

            tags.add(tagRecyclage);
            tags.add(tagDegradation);
            tags.add(tagFuite);
            tags.add(tagCarton);
            tags.add(tagPapier);
            tags.add(tagPile);
            tags.add(tagTextile);
            tags.add(tagVerre);

            Map<Key<Tag>, Tag> resTags = ofy().save().entities(tags).now();

            result.add(String.format("Data inserted for model 'Tag' (%d entries)", resTags.size()));


            List<Interest> interests = new LinkedList<>();
            List<Interest> interestsVerre = new LinkedList<>();
            interestsVerre.add(new Interest().addLocation(43.55891, 1.47303));
            interestsVerre.add(new Interest().addLocation(43.55999, 1.47194));
            interestsVerre.add(new Interest().addLocation(43.55995, 1.47195));
            interestsVerre.add(new Interest().addLocation(43.56439, 1.47053));
            interestsVerre.add(new Interest().addLocation(43.56355, 1.47579));
            interestsVerre.add(new Interest().addLocation(43.55509, 1.46816));
            interestsVerre.add(new Interest().addLocation(43.56295, 1.46311));
            interestsVerre.add(new Interest().addLocation(43.56305, 1.45939));
            interestsVerre.add(new Interest().addLocation(43.56068, 1.45738));
            interestsVerre.add(new Interest().addLocation(43.56376, 1.45531));
            interestsVerre.add(new Interest().addLocation(43.56850, 1.46620));
            interestsVerre.add(new Interest().addLocation(43.56735, 1.46726));
            interestsVerre.add(new Interest().addLocation(43.57124, 1.46269));
            interestsVerre.add(new Interest().addLocation(43.56731, 1.46477));
            for (Interest interestVerre : interestsVerre) {
                interestVerre.addTag(tagVerre);
            }
            interests.addAll(interestsVerre);

            List<Interest> interestsTextile = new LinkedList<>();
            interestsTextile.add(new Interest().addLocation(43.55505, 1.46811));
            interestsTextile.add(new Interest().addLocation(43.56305, 1.45935));
            for (Interest interestTextile : interestsTextile) {
                interestTextile.addTag(tagTextile);
            }
            interests.addAll(interestsTextile);

            List<Interest> interestsCarton = new LinkedList<>();
            interestsCarton.add(new Interest().addLocation(43.55773, 1.46920).addLocation(43.55780, 1.46934).addLocation(43.55745, 1.46969).addLocation(43.55738, 1.46954));
            interestsCarton.add(new Interest().addLocation(43.55992, 1.47172).addLocation(43.56000, 1.47186).addLocation(43.55948, 1.47236).addLocation(43.55942, 1.47220));
            interestsCarton.add(new Interest().addLocation(43.5622, 1.46751).addLocation(43.56231, 1.4677).addLocation(43.56206, 1.46795).addLocation(43.56197, 1.46772));
            interestsCarton.add(new Interest().addLocation(43.56449, 1.4656).addLocation(43.56455, 1.46576).addLocation(43.56406, 1.46625).addLocation(43.56398, 1.46609));
            interestsCarton.add(new Interest().addLocation(43.56577, 1.46736).addLocation(43.56593, 1.46769).addLocation(43.56577, 1.46785).addLocation(43.5656, 1.46753));
            interestsCarton.add(new Interest().addLocation(43.56665, 1.4695).addLocation(43.56674, 1.46968).addLocation(43.56657, 1.46985).addLocation(43.5665, 1.46965));
            for (Interest interestCarton : interestsCarton) {
                interestCarton.addTag(tagCarton);
            }
            interests.addAll(interestsCarton);

            List<Interest> interestsPile = new LinkedList<>();
            interestsPile.add(new Interest().addLocation(43.56362, 1.46487).addLocation(43.56381, 1.46537).addLocation(43.56354, 1.4657).addLocation(43.5633, 1.46522));
            interestsPile.add(new Interest().addLocation(43.56456, 1.46589).addLocation(43.56472, 1.46617).addLocation(43.56458, 1.46629).addLocation(43.56446, 1.46602));
            for (Interest interestPile : interestsPile) {
                interestPile.addTag(tagPile);
            }
            interests.addAll(interestsPile);

            List<Interest> interestsPapier = new LinkedList<>();
            interestsPapier.add(new Interest().addLocation(43.56758, 1.46950).addLocation(43.56773, 1.46989).addLocation(43.56733, 1.47026).addLocation(43.56716, 1.46991));
            interestsPapier.add(new Interest().addLocation(43.56666, 1.46950).addLocation(43.56674, 1.46967).addLocation(43.56667, 1.46975).addLocation(43.56657, 1.46958));
            interestsPapier.add(new Interest().addLocation(43.56497, 1.46513).addLocation(43.56504, 1.46529).addLocation(43.56461, 1.4657).addLocation(43.56454, 1.46554));
            interestsPapier.add(new Interest().addLocation(43.56542, 1.46363).addLocation(43.56554, 1.4639).addLocation(43.5652, 1.46422).addLocation(43.56508, 1.46391));
            interestsPapier.add(new Interest().addLocation(43.56372, 1.46478).addLocation(43.56393, 1.46529).addLocation(43.56426, 1.46496).addLocation(43.56403, 1.46448));
            interestsPapier.add(new Interest().addLocation(43.56349, 1.46428).addLocation(43.56362, 1.4646).addLocation(43.56323, 1.46499).addLocation(43.56311, 1.46473));
            interestsPapier.add(new Interest().addLocation(43.5632, 1.465620).addLocation(43.56335, 1.4659).addLocation(43.56263, 1.46661).addLocation(43.56249, 1.46632));
            interestsPapier.add(new Interest().addLocation(43.56377, 1.46168).addLocation(43.56384, 1.46181).addLocation(43.56375, 1.46195).addLocation(43.56367, 1.4618));
            interestsPapier.add(new Interest().addLocation(43.56123, 1.46364).addLocation(43.56137, 1.46392).addLocation(43.56086, 1.46442).addLocation(43.56072, 1.46414));
            interestsPapier.add(new Interest().addLocation(43.56151, 1.46595).addLocation(43.56161, 1.46623).addLocation(43.56186, 1.46599).addLocation(43.56175, 1.46571));
            interestsPapier.add(new Interest().addLocation(43.56146, 1.46600).addLocation(43.56154, 1.46615).addLocation(43.56112, 1.46656).addLocation(43.56104, 1.46641));
            interestsPapier.add(new Interest().addLocation(43.56202, 1.46603).addLocation(43.5621, 1.46618).addLocation(43.5615, 1.46678).addLocation(43.56142, 1.46663));
            interestsPapier.add(new Interest().addLocation(43.56146, 1.46709).addLocation(43.56156, 1.4673).addLocation(43.5614, 1.46745).addLocation(43.5613, 1.46725));
            interestsPapier.add(new Interest().addLocation(43.56245, 1.4669).addLocation(43.56253, 1.46706).addLocation(43.5623, 1.46728).addLocation(43.56222, 1.46713));
            interestsPapier.add(new Interest().addLocation(43.5618, 1.4677).addLocation(43.56197, 1.46805).addLocation(43.56174, 1.46827).addLocation(43.56157, 1.46793));
            interestsPapier.add(new Interest().addLocation(43.56263, 1.46908).addLocation(43.56277, 1.46939).addLocation(43.5625, 1.46965).addLocation(43.56236, 1.46932));
            interestsPapier.add(new Interest().addLocation(43.56189, 1.46903).addLocation(43.56201, 1.4693).addLocation(43.56185, 1.46944).addLocation(43.56173, 1.46916));
            interestsPapier.add(new Interest().addLocation(43.56134, 1.46839).addLocation(43.56141, 1.46855).addLocation(43.56101, 1.46896).addLocation(43.56093, 1.46881));
            interestsPapier.add(new Interest().addLocation(43.56141, 1.46894).addLocation(43.56169, 1.46951).addLocation(43.56105, 1.47007).addLocation(43.56079, 1.46955));
            interestsPapier.add(new Interest().addLocation(43.56153, 1.47001).addLocation(43.56088, 1.47064).addLocation(43.56107, 1.47108).addLocation(43.56173, 1.47041));
            interestsPapier.add(new Interest().addLocation(43.55933, 1.47231).addLocation(43.55939, 1.47246).addLocation(43.55886, 1.47299).addLocation(43.55879, 1.47284));
            interestsPapier.add(new Interest().addLocation(43.56076, 1.46724).addLocation(43.56084, 1.46739).addLocation(43.55942, 1.46883).addLocation(43.55934, 1.46868));
            interestsPapier.add(new Interest().addLocation(43.56025, 1.46732).addLocation(43.56032, 1.46748).addLocation(43.55976, 1.46805).addLocation(43.55968, 1.46787));
            interestsPapier.add(new Interest().addLocation(43.55844, 1.46891).addLocation(43.55852, 1.46906).addLocation(43.55798, 1.4696).addLocation(43.5579, 1.46945));
            for (Interest interestPapier : interestsPapier) {
                interestPapier.addTag(tagPapier);
            }
            interests.addAll(interestsPapier);

            for (Interest interest : interests) {
                interest.addTag(tagRecyclage);
            }

            Map<Key<Interest>, Interest> resInterest = ofy().save().entities(interests).now();

            result.add(String.format("Data inserted for model 'Interest' (%d entries)", resInterest.size()));
        } else {
            result.add("Nothing to insert in database");
        }

        InitBean bean = new InitBean(result);
        return bean;
    }

    @ApiMethod(httpMethod = "DELETE")
    public final InitBean clean() {
        List<String> result = new LinkedList<>();

        List<Key<Tag>> tagKeys = ofy().load().type(Tag.class).keys().list();
        List<Key<UserAccount>> userKeys = ofy().load().type(UserAccount.class).keys().list();
        List<Key<Interest>> interestKeys = ofy().load().type(Interest.class).keys().list();

        ofy().delete().keys(tagKeys).now();
        ofy().delete().keys(userKeys).now();
        ofy().delete().keys(interestKeys).now();

        result.add(String.format("%d entries deleted from 'Tag'", tagKeys.size()));
        result.add(String.format("%d entries deleted from 'UserAccount'", userKeys.size()));
        result.add(String.format("%d entries deleted from 'Interest'", interestKeys.size()));

        InitBean bean = new InitBean(result);
        return bean;
    }
}
