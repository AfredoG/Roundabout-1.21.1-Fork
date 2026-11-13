package net.hydra.jojomod.datagen;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;




public class GennedBlockModel extends BlockModelBuilder {
    private final JsonObject jsonO;
    public GennedBlockModel(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper, JsonObject json) {
        super(outputLocation, existingFileHelper);
        jsonO = json;
    }
    @Override
    public com.google.gson.JsonObject toJson()
    {
        return jsonO;
    }

}
