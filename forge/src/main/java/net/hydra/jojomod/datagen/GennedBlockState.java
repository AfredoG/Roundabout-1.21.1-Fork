package net.hydra.jojomod.datagen;

import com.google.gson.JsonObject;
import net.neoforged.neoforge.client.model.generators.IGeneratedBlockState;

public class GennedBlockState implements IGeneratedBlockState {
    private JsonObject json;
    public GennedBlockState(JsonObject jsob){
        json = jsob;
    }
    @Override
    public JsonObject toJson() {
        return json;
    }
}
