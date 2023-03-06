package net.kyrptonaught.upgradedechests.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kyrptonaught.upgradedechests.UpgradedEchestMod;
import net.kyrptonaught.upgradedechests.block.blockentity.OpenableBlockEntity;
import net.kyrptonaught.upgradedechests.block.blockentity.RiftChestBlockEntity;
import net.kyrptonaught.upgradedechests.client.UpgradedEchestClientMod;
import net.kyrptonaught.upgradedechests.inv.RiftEChestInventory;
import net.kyrptonaught.upgradedechests.util.ContainerNames;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class RiftEChest extends EnderChestBlock implements InventoryProvider {
    public static BlockEntityType<RiftChestBlockEntity> blockEntity;

    public RiftEChest(Settings settings) {
        super(settings);
        Registry.register(Registries.BLOCK, new Identifier(UpgradedEchestMod.MOD_ID, "riftchest"), this);
        blockEntity = Registry.register(Registries.BLOCK_ENTITY_TYPE, UpgradedEchestMod.MOD_ID + ":riftchest", FabricBlockEntityTypeBuilder.create(RiftChestBlockEntity::new, this).build(null));
        Registry.register(Registries.ITEM, new Identifier(UpgradedEchestMod.MOD_ID, "riftchest"), new BlockItem(this, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(UpgradedEchestMod.GROUP).register(entries -> entries.add(new ItemStack(this)));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RiftChestBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient & type == blockEntity ? (world1, pos, state1, blockEntity) -> ((OpenableBlockEntity) blockEntity).clientTick() : null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient)
            if (placer instanceof PlayerEntity) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof RiftChestBlockEntity) {
                    ((RiftChestBlockEntity) blockEntity).setStoredPlayer((PlayerEntity) placer);
                    // ((RiftChestBlockEntity) blockEntity).appendUpgrades(itemStack.getSubTag(ShulkerUpgrades.KEY));
                }
            }
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 3; ++i) {
            int j = random.nextInt(2) * 2 - 1;
            int k = random.nextInt(2) * 2 - 1;
            double d = (double) pos.getX() + 0.5D + 0.25D * (double) j;
            double e = (float) pos.getY() + random.nextFloat();
            double f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
            double g = random.nextFloat() * (float) j;
            double h = ((double) random.nextFloat() - 0.5D) * 0.125D;
            double l = random.nextFloat() * (float) k;
            world.addParticle(UpgradedEchestClientMod.BLUEPARTICLE, d, e, f, g, h, l);
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof RiftChestBlockEntity) {
                if (!((RiftChestBlockEntity) entity).hasStoredPlayer())
                    player.sendMessage(Text.literal("Rift chest is not bound to a player"), true);
                RiftEChestInventory inv = ((RiftChestBlockEntity) entity).getEChestInv((ServerWorld) world);
                if (inv == null) player.sendMessage(Text.literal("Bound player must be online to use"), true);
                else {
                    // player.openHandledScreen(RiftScreenHandler.createScreenHandlerFactory(inv, new LiteralText("Rift Ender Chest: ").append(((RiftChestBlockEntity) entity).getPlayerName((ServerWorld) world))));
                    player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
                        return GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, inv);
                    }, ContainerNames.getRiftChestName(((RiftChestBlockEntity) entity).getPlayerName((ServerWorld) world))));
                    return ActionResult.CONSUME;
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        if (!world.isClient()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof RiftChestBlockEntity) {
                return ((RiftChestBlockEntity) entity).getEChestInv((ServerWorld) world);
            }
        }
        return null;
    }
}