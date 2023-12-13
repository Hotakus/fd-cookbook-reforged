package net.hotakus.fdcookbook.blocks;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FDCBShapes {
    public static VoxelShape makeShapeDefault() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.546875, 0.0875, 0.171875, 0.671875, 0.0984375, 0.203125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.546875, 0.1523325, 0.17314375, 0.671875, 0.16327, 0.19658125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.2703125, 0.0234375, 0.203125, 0.75, 0.1140625, 0.859375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.1, 0.203125, 0.765625, 0.115625, 0.859375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.021875, 0.203125, 0.765625, 0.0375, 0.859375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.7578125, 0.0375, 0.203125, 0.765625, 0.1, 0.859375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.0375, 0.203125, 0.7578125, 0.1, 0.859375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1140625, 0.1875, 0.75, 0.1390625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, -0.0015625, 0.1875, 0.75, 0.0234375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.765625, 0.021875, 0.1875, 0.78125, 0.115625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.115625, 0.1875, 0.765625, 0.13125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, -0.0015625, 0.1875, 0.7578125, 0.00625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.7578125, 0.0296875, 0.1875, 0.765625, 0.0375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.021875, 0.1875, 0.7578125, 0.0296875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.7578125, 0.021875, 0.1875, 0.765625, 0.0296875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.765625, 0.0140625, 0.1875, 0.7734375, 0.021875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.00625, 0.1875, 0.765625, 0.021875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.765625, 0.115625, 0.1875, 0.7734375, 0.1234375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.7578125, 0.1078125, 0.1875, 0.765625, 0.115625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.1078125, 0.1875, 0.7578125, 0.115625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.7578125, 0.1, 0.1875, 0.765625, 0.1078125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.13125, 0.1875, 0.7578125, 0.1390625, 0.875), BooleanOp.OR);

        return shape;
    }
}
