package net.hydra.jojomod.access;

import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.BlockPos;

public interface IViewArea {
    SectionRenderDispatcher.RenderSection roundabout$getRenderChunkAt(BlockPos $$0);
}
