package net.hotakus.fdcookbook.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class CookBookBER implements BlockEntityRenderer<CookBookBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public CookBookBER(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(CookBookBlockEntity cookBookBlockEntity, float partialTicks, PoseStack stack,
                       MultiBufferSource multiBufferSource, int combinedOverlay, int packedLight) {
        final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        final LocalPlayer player = Minecraft.getInstance().player;
        final ItemStack item = player.getMainHandItem();

        stack.pushPose();
        stack.translate(0f, 0.25f, 0.25f);
        stack.scale(0.5f, 0.5f, 0.5f);

        Direction dir = cookBookBlockEntity.getBlockState().getValue(CookBookBlock.FACING);

        switch (dir) {
            case NORTH -> stack.mulPose(Vector3f.YP.rotationDegrees(0));
            case SOUTH -> stack.mulPose(Vector3f.YP.rotationDegrees(180));
            case EAST -> stack.mulPose(Vector3f.YP.rotationDegrees(90));
            case WEST -> stack.mulPose(Vector3f.YP.rotationDegrees(270));
        }

        itemRenderer.renderStatic(player, item, ItemTransforms.TransformType.FIXED, false, stack, multiBufferSource,
                Minecraft.getInstance().level, combinedOverlay, combinedOverlay, 0);

        stack.popPose();
    }


}
