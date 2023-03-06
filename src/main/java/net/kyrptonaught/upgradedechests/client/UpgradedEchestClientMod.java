package net.kyrptonaught.upgradedechests.client;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.kyrptonaught.upgradedechests.UpgradedEchestMod;
import net.kyrptonaught.upgradedechests.block.RiftEChest;
import net.kyrptonaught.upgradedechests.block.SpatialEChest;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


@Environment(EnvType.CLIENT)
public class UpgradedEchestClientMod implements ClientModInitializer {
    public static final Identifier SPATIAL_ECHEST_TEXTURE = new Identifier("upgradedechests", "entity/chest/dimensional_chest");
    public static final Identifier RIFT_ECHEST_TEXTURE = new Identifier("upgradedechests", "entity/chest/rift_chest");
    public static final DefaultParticleType BLUEPARTICLE = Registry.register(Registries.PARTICLE_TYPE, "upgradedechests" + ":blueparticle", FabricParticleTypes.simple(true));
    public static final DefaultParticleType GREENPARTICLE = Registry.register(Registries.PARTICLE_TYPE, "upgradedechests" + ":greenparticle", FabricParticleTypes.simple(true));

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(SpatialEChest.blockEntity, ChestBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(RiftEChest.blockEntity, ChestBlockEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(BLUEPARTICLE, ColoredPortalParticle.BlueFactory::new);
        ParticleFactoryRegistry.getInstance().register(GREENPARTICLE, ColoredPortalParticle.GreenFactory::new);
        BuiltinItemRendererRegistry.INSTANCE.register(UpgradedEchestMod.spatialEChest, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(SpatialEChest.blockEntity.instantiate(BlockPos.ORIGIN, UpgradedEchestMod.spatialEChest.getDefaultState()), matrices, vertexConsumers, light, overlay);
        });
        BuiltinItemRendererRegistry.INSTANCE.register(UpgradedEchestMod.riftEChest, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(RiftEChest.blockEntity.instantiate(BlockPos.ORIGIN, UpgradedEchestMod.riftEChest.getDefaultState()), matrices, vertexConsumers, light, overlay);
        });
        FabricLoader.getInstance().getModContainer(UpgradedEchestMod.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(UpgradedEchestMod.MOD_ID, "upgradedechests32x"), modContainer, ResourcePackActivationType.NORMAL);
        });
    }
}

