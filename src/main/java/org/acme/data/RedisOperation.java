package org.acme.data;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.hash.HashCommands;
import io.quarkus.redis.datasource.search.Document;
import io.quarkus.redis.datasource.search.SearchCommands;
import io.quarkus.redis.datasource.search.SearchQueryResponse;
import jakarta.inject.Singleton;

import static io.smallrye.config.ConfigLogging.log;


@Singleton
public class RedisOperation {

    private final HashCommands<String, String, String> hashc;
    private final SearchCommands<String> searchc;

    public RedisOperation(RedisDataSource ds) {
        hashc = ds.hash(String.class);
        searchc = ds.search(String.class);
    }

    public String get(String key, String field) {
        String retour;
        retour = hashc.hget(key, field);
        log.info("La commande a renvoyé : "+retour);
        return retour;
    }

    public String getIndex(String idVoie,Integer pkDebut,Integer pkFin) {
        String index = "idx:voiture";
        String query = "\"@voie:{"+idVoie+"} @pk:["+pkDebut+" "+pkFin+"]\"";
    
        StringBuilder retour = new StringBuilder("");

        log.info("On envoie la demande de recherche sur "+index+" lavec la query : "+query);
        SearchQueryResponse result = searchc.ftSearch(index, query);
        
        log.info("la requête est passée");
        log.info("La réponse est : "+result.toString());
        for (Document doc : result.documents()) {
            log.info("Trouver : "+doc.key());
            retour.append(" ").append(doc.key());
        }
        return retour.toString();
    }
}