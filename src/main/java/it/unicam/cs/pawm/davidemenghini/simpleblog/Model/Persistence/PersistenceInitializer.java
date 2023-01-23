package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;

import java.util.Map;


/**
 *
 * Questa interfaccia funzionale definisce l'azione di inizzializzare
 */
@FunctionalInterface
public interface PersistenceInitializer {

    void initializeData(Map<String,String> data);


    static void initializeMysql(Map<String,String> data){
        
    }

}
