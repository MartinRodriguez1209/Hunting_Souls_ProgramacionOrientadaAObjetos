package io.github.primer_test.Utils;

import java.util.List;

public class SfxUnitData {

    public String name; 
    
    
    public List<SfxAccionData> sfx;


    public static class SfxAccionData {
        public String action;
        public String file;
        public float volume;
        public boolean loop;
    }
}