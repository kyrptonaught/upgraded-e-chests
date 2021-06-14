package net.kyrptonaught.upgradedechests.client;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.kyrptonaught.upgradedechests.UpgradedEchestMod;
import net.kyrptonaught.upgradedechests.block.RiftEChest;
import net.kyrptonaught.upgradedechests.block.SpatialEChest;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;


@Environment(EnvType.CLIENT)
public class UpgradedEchestClientMod implements ClientModInitializer {
    public static final Identifier SPATIAL_ECHEST_TEXTURE = new Identifier("upgradedechests", "blocks/dimensional_chest");
    public static final Identifier RIFT_ECHEST_TEXTURE = new Identifier("upgradedechests", "blocks/rift_chest");
    // public static final Identifier RIFT_ECHEST_HOPPER_TEXTURE = new Identifier("upgradedechests", "blocks/rift_chest_hopper_32");
    public static final DefaultParticleType BLUEPARTICLE = Registry.register(Registry.PARTICLE_TYPE, "upgradedechests" + ":blueparticle", FabricParticleTypes.simple(true));
    public static final DefaultParticleType GREENPARTICLE = Registry.register(Registry.PARTICLE_TYPE, "upgradedechests" + ":greenparticle", FabricParticleTypes.simple(true));

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(SpatialEChest.blockEntity, ChestBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(RiftEChest.blockEntity, ChestBlockEntityRenderer::new);
        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(SPATIAL_ECHEST_TEXTURE));
        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(RIFT_ECHEST_TEXTURE));
        //ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(RIFT_ECHEST_HOPPER_TEXTURE));
        ParticleFactoryRegistry.getInstance().register(BLUEPARTICLE, ColoredPortalParticle.BlueFactory::new);
        ParticleFactoryRegistry.getInstance().register(GREENPARTICLE, ColoredPortalParticle.GreenFactory::new);
        BuiltinItemRendererRegistry.INSTANCE.register(UpgradedEchestMod.spatialEChest, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(SpatialEChest.blockEntity.instantiate(BlockPos.ORIGIN,UpgradedEchestMod.spatialEChest.getDefaultState()), matrices, vertexConsumers, light, overlay);
        });
        BuiltinItemRendererRegistry.INSTANCE.register(UpgradedEchestMod.riftEChest, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(RiftEChest.blockEntity.instantiate(BlockPos.ORIGIN,UpgradedEchestMod.riftEChest.getDefaultState()), matrices, vertexConsumers, light, overlay);
        });
        FabricLoader.getInstance().getModContainer(UpgradedEchestMod.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(UpgradedEchestMod.MOD_ID, "upgradedechests32x"), modContainer, ResourcePackActivationType.NORMAL);
        });
    }
}

