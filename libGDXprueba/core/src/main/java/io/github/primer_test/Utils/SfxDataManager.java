package io.github.primer_test.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import io.github.primer_test.Sonidos.SonidoData; 
import io.github.primer_test.Utils.SfxUnitData.SfxAccionData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class SfxDataManager {

    private static SfxDataManager instance;
    
    private final Map<String, Map<String, SonidoData>> dataCache; 

    private SfxDataManager() {
        dataCache = new HashMap<>();
        loadSfxData();
    }

    public static SfxDataManager getInstancia() {
        if (instance == null) {
            instance = new SfxDataManager();
        }
        return instance;
    }

    
    private static class SfxDataRaiz {
        public List<SfxUnitData> unidadesSfx;
    }

    private void loadSfxData() {
        Json json = new Json();
        
        try {
            
        	SfxDataRaiz raiz = json.fromJson(SfxDataRaiz.class, Gdx.files.internal("sfx.json"));
            
            if (raiz != null && raiz.unidadesSfx != null) {
                
                
                for (SfxUnitData unitData : raiz.unidadesSfx) {
                    
                    Map<String, SonidoData> actionsMap = new HashMap<>();
                    
                    
                    for (SfxAccionData sfxAction : unitData.sfx) {
                        
                        
                        SonidoData finalData = new SonidoData(
                            sfxAction.file,
                            sfxAction.volume,
                            sfxAction.loop
                        );
                        actionsMap.put(sfxAction.action, finalData);
                    }
                    
                    
                    dataCache.put(unitData.name, actionsMap);
                }
            }
        } catch (Exception e) {
            
            Gdx.app.error("SFX_LOAD", "Error al cargar sfx.json o estructura JSON incorrecta", e);
        }
    }

    public boolean isDataLoaded() {
        
        return !dataCache.isEmpty(); 
    }
    
    public SonidoData getSfxData(String unitName, String actionName) {
        Map<String, SonidoData> actions = dataCache.get(unitName);
        if (actions != null) {
            return actions.get(actionName);
        }
        
        return null;
    }
    
    
    public List<SonidoData> getAllSfxDataList() {
        List<SonidoData> allSfx = new ArrayList<>();
        for (Map<String, SonidoData> actionsMap : dataCache.values()) {
            allSfx.addAll(actionsMap.values());
        }
        return allSfx;
    }
}